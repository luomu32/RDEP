package xyz.luomu32.rdep.project.controller;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ICoverageVisitor;
import org.jacoco.core.data.ExecutionDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.project.model.Module;
import xyz.luomu32.rdep.project.model.Project;
import xyz.luomu32.rdep.project.pojo.BuildHistoryResponse;
import xyz.luomu32.rdep.project.repo.ModuleRepo;
import xyz.luomu32.rdep.project.repo.ProjectRepo;
import xyz.luomu32.rdep.project.service.Analyzer;
import xyz.luomu32.rdep.project.service.AnalyzerResult;
import xyz.luomu32.rdep.project.service.BuildService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("projects/{projectId}/modules/{moduleId}")
public class BuildController {

    @Autowired
    private BuildService buildService;

    @Autowired
    private ModuleRepo moduleRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private Analyzer analyzer;

    @PostMapping("build")
    public void build(@PathVariable Long projectId,
                      @PathVariable Long moduleId) {
        buildService.build(projectId, moduleId);
//        File dir = buildService.fetchCode(project, module);
        //build
//        buildService.unpack(dir, module);
        //build docker image
//        buildService.buildDockerImage();
        //push to docker registry
    }

    @GetMapping("build")
    public List<BuildHistoryResponse> fetch(@PathVariable Long projectId,
                                            @PathVariable Long moduleId) {
        return buildService.fetchBuildHistories(projectId, moduleId);
    }

    @PostMapping("analyze")
    public void analyze(@PathVariable Long projectId,
                        @PathVariable Long moduleId) {

        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("project.not.found"));
        Module module = moduleRepo.findById(moduleId).orElseThrow(() -> new RuntimeException("module.not.found"));

        String dir = System.getProperty("user.home") + "/rdep workdir/";
        File projectDir = new File(dir + project.getName());

        //download code from version control system,eg.git
        try {
            if (projectDir.exists()) {
                FileUtils.deleteDirectory(projectDir);
            }

            Git.cloneRepository().setURI(module.getScmUrl()).setDirectory(projectDir)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider("luomu32", "sun_lab32"))
                    .call();
        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
            return;
        }

        //determine language,eg.java,go,python,node.js...
        AnalyzerResult result = analyzer.analyze(projectDir, module);
        module.setInfo(result.getExt());
        moduleRepo.save(module);
    }

    @PostMapping("test-coverage")
    public void analyzeTestCoverage(@PathVariable Long projectId,
                                    @PathVariable Long moduleId) {

        org.jacoco.core.analysis.Analyzer analyzer = new org.jacoco.core.analysis.Analyzer(new ExecutionDataStore(), new ICoverageVisitor() {
            @Override
            public void visitCoverage(IClassCoverage coverage) {
                System.out.printf("class name:   %s%n", coverage.getName());
                System.out.printf("class id:     %016x%n", Long.valueOf(coverage.getId()));
                System.out.printf("instructions: %s%n", Integer.valueOf(coverage
                        .getInstructionCounter().getTotalCount()));
                System.out.printf("branches:     %s%n",
                        Integer.valueOf(coverage.getBranchCounter().getTotalCount()));
                System.out.printf("lines:        %s%n",
                        Integer.valueOf(coverage.getLineCounter().getTotalCount()));
                System.out.printf("methods:      %s%n",
                        Integer.valueOf(coverage.getMethodCounter().getTotalCount()));
                System.out.printf("complexity:   %s%n%n", Integer.valueOf(coverage
                        .getComplexityCounter().getTotalCount()));
            }
        });

        Project project = projectRepo.findById(projectId).orElseThrow(() -> new ServiceException("project.not.found"));
        Module module = moduleRepo.findById(moduleId).orElseThrow(() -> new ServiceException("module.not.found"));

        String dir = System.getProperty("user.home") + "/rdep workdir";

        try {
            analyzer.analyzeAll(Paths.get(dir, project.getName(), module.getName(), "target/classes").toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
