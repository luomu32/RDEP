package xyz.luomu32.rdep.project.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.project.model.*;
import xyz.luomu32.rdep.project.pojo.BuildHistoryResponse;
import xyz.luomu32.rdep.project.repo.*;
import xyz.luomu32.rdep.project.service.BuildService;
import xyz.luomu32.rdep.project.service.JenkinsService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BuildServiceImpl implements BuildService {

    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private ModuleRepo moduleRepo;
    @Autowired
    private ModuleBuildHistoryRepo moduleBuildHistoryRepo;
    @Autowired
    private ModuleBuildRepo moduleBuildRepo;
    @Autowired
    private ModuleBuildConfigRepo moduleBuildConfigRepo;
    @Autowired
    private ModuleBuildCommitHistoryRepo moduleBuildCommitHistoryRepo;
    @Autowired
    private JenkinsService jenkinsService;

    @Override
    public void build(Long projectId, Long moduleId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new ServiceException("project.not.found"));
        xyz.luomu32.rdep.project.model.Module module = moduleRepo.findById(moduleId).orElseThrow(() -> new ServiceException("module.not.found"));

        Optional<ModuleBuildConfig> buildConfig = moduleBuildConfigRepo.findByModuleId(moduleId);

//        if (buildConfig.isPresent() && buildConfig.get().getType().equals(ModuleBuildType.JENKINS)) {
        ResponseEntity<Void> response = jenkinsService.createBuild("dubbo-demo", "1234");
//        System.out.println(response.getQueueId());
//        } else {
//            ModuleBuildHistory buildHistory = new ModuleBuildHistory();
//            buildHistory.setModuleId(moduleId);
//            buildHistory.setStatus(ModuleBuildHistoryStatus.BUILDING);
//            moduleBuildHistoryRepo.save(buildHistory);
//            fetchCode(project, module);
//        }
    }

    @Override
    public List<BuildHistoryResponse> fetchBuildHistories(Long projectId, Long moduleId) {
        return moduleBuildHistoryRepo.findByModuleId(moduleId).stream()
                .map(h -> {
                    return BuildHistoryResponse.from(h, moduleBuildCommitHistoryRepo.findByModuleBuildHistoryId(h.getId()));
                }).collect(Collectors.toList());
    }

    @Override
    @Async
    public File fetchCode(Project project, xyz.luomu32.rdep.project.model.Module module) {

        String dir = System.getProperty("user.home") + "/rdep workdir/";
        File projectDir = new File(dir + project.getName());

        //download code from version control system,eg.git
        try {
            if (projectDir.exists()) {
                FileUtils.deleteDirectory(projectDir);
            }

            Git git = Git.cloneRepository().setURI(module.getScmUrl()).setDirectory(projectDir)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider("luomu32", "sun_lab32"))
                    .call();

            Optional<ModuleBuild> moduleBuild = moduleBuildRepo.findByModuleId(module.getId());
            ModuleBuildHistory history = moduleBuildHistoryRepo.findFirst1ByModuleIdAndStatusOrderByCreateDatetimeDesc(module.getId(), ModuleBuildHistoryStatus.BUILDING)
                    .orElseThrow(() -> new ServiceException("module.build.history.not.found"));

            String lastCommitId = git.getRepository().resolve("head").getName();

            List<ModuleBuildCommitHistory> commitHistories = new ArrayList<>();
            if (moduleBuild.isPresent()) {
                moduleBuild.get().setLastBuildCommitId(lastCommitId);
                moduleBuildRepo.save(moduleBuild.get());

                git.log().addRange(git.getRepository().resolve(moduleBuild.get().getLastBuildCommitId()), git.getRepository().findRef("head").getObjectId())
                        .call()
                        .forEach(commit -> {
                            ModuleBuildCommitHistory moduleBuildCommitHistory = new ModuleBuildCommitHistory();
                            moduleBuildCommitHistory.setModuleBuildHistoryId(history.getId());
                            moduleBuildCommitHistory.setAuthorName(commit.getAuthorIdent().getName());
                            moduleBuildCommitHistory.setAuthorEmail(commit.getAuthorIdent().getEmailAddress());
                            moduleBuildCommitHistory.setMessage(commit.getShortMessage());
                            moduleBuildCommitHistory.setCommitId(commit.getId().getName());
                            moduleBuildCommitHistory.setTime(commit.getCommitTime());
                            commitHistories.add(moduleBuildCommitHistory);
                        });
            } else {

                ModuleBuild moduleBuild1 = new ModuleBuild();
                moduleBuild1.setModuleId(module.getId());
                moduleBuild1.setLastBuildCommitId(lastCommitId);
                moduleBuildRepo.save(moduleBuild1);

                git.log().call().forEach(commit -> {
                    ModuleBuildCommitHistory moduleBuildCommitHistory = new ModuleBuildCommitHistory();
                    moduleBuildCommitHistory.setModuleBuildHistoryId(history.getId());
                    moduleBuildCommitHistory.setAuthorName(commit.getAuthorIdent().getName());
                    moduleBuildCommitHistory.setAuthorEmail(commit.getAuthorIdent().getEmailAddress());
                    moduleBuildCommitHistory.setMessage(commit.getShortMessage());
                    moduleBuildCommitHistory.setCommitId(commit.getId().getName());
                    moduleBuildCommitHistory.setTime(commit.getCommitTime());
                    commitHistories.add(moduleBuildCommitHistory);
                });
            }

            moduleBuildCommitHistoryRepo.saveAll(commitHistories);

        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
        }
        return projectDir;
    }

    @Override
    public void unpack(File dir, xyz.luomu32.rdep.project.model.Module module) {
        CommandLine cmdLine = new CommandLine("mvn -f " + dir.getAbsolutePath() + "/" + module.getName() + "/pom.xml clean package");

        DefaultExecutor executor = new DefaultExecutor();

        log.debug("execute command:{}", cmdLine.toString());

        try {
            int exitValue = executor.execute(cmdLine);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
