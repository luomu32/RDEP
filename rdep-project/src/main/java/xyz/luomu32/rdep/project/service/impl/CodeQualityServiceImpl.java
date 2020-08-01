package xyz.luomu32.rdep.project.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.lib.Ref;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.project.model.Module;
import xyz.luomu32.rdep.project.model.Project;
import xyz.luomu32.rdep.project.pojo.CheckstyleResult;
import xyz.luomu32.rdep.project.repo.ModuleRepo;
import xyz.luomu32.rdep.project.repo.ProjectRepo;
import xyz.luomu32.rdep.project.service.CodeQualityService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CodeQualityServiceImpl implements CodeQualityService {
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private ModuleRepo moduleRepo;

    @Override
    public void findBugsCheck(Long projectId, Long moduleId, String branch) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new ServiceException("project.not.found"));
        Module module = moduleRepo.findById(moduleId).orElseThrow(() -> new ServiceException("module.not.found"));

        //get work dir
        Path modulePath = Paths.get(System.getProperty("user.home"), "rdep workdir", project.getName(), module.getServiceId());
        try (Git git = Git.open(modulePath.getParent().toFile())) {
            //switch branch if need
            if (!git.getRepository().getBranch().equals(branch)) {
                //check branch if not exist throw exception
                Ref ref = git.checkout().setName(branch).call();
                if (ref.getName().length() == 0)
                    throw new ServiceException("branch.not.exist");
            }
        } catch (IOException e) {
            log.error("", e);
            throw new ServiceException("open git directory fail");
        } catch (GitAPIException e) {
            throw new ServiceException("check out branch fail");
        }

        //create result output directory if not exist
        File resultDirectory = new File(modulePath.toFile(), "pmd");
        if (!resultDirectory.exists())
            resultDirectory.mkdir();

        String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xml";

        Process process;
        try {
            process = new ProcessBuilder()
                    .directory(modulePath.toFile())
                    .command("findbugs")
                    .inheritIO()
                    .start();

//            process.onExit().whenCompleteAsync((t, e) -> {
//                if (e != null)
//                    throw new ServiceException("findbugs.check.fail", e.getMessage());
//            });
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//            String line;
//            if ((line = reader.readLine()) != null)
//                throw new ServiceException("findbugs.check.failed", line);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public CheckstyleResult checkstyleCheck(Long projectId, Long moduleId, String branch, String style) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new ServiceException("project.not.found"));
        Module module = moduleRepo.findById(moduleId).orElseThrow(() -> new ServiceException("module.not.found"));

        //get work dir
        String dir = System.getProperty("user.home") + "/rdep workdir/";
        File projectDir = new File(dir + project.getName());
        File moduleDir = new File(projectDir, module.getServiceId());

        try {
            Git git = Git.open(projectDir);
            if (!git.getRepository().getBranch().equals(branch)) {
                //check branch if not exist throw exception
                Ref ref = git.checkout().setName(branch).call();
                if (ref.getName().length() == 0)
                    throw new ServiceException("branch.not.exist");
            }

            String styleArg;
            if (style.equals("google"))
                styleArg = "google_checks.xml";
            else
                styleArg = "sun_checks.xml";

            File checkstyleResultFileDir = new File(moduleDir, "checkstyle");
            if (!checkstyleResultFileDir.exists())
                checkstyleResultFileDir.mkdir();
            String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xml";
            ProcessBuilder builder = new ProcessBuilder();
            builder.directory(moduleDir);
            builder.command("checkstyle",
                    "-c", styleArg,
                    "-f", "xml",
                    "-o", "checkstyle/" + filename
                    , ".");
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            if ((line = reader.readLine()) != null)
                throw new ServiceException("checkstyle.check.failed", line);

            return CheckstyleResult.buildFromXml(new File(checkstyleResultFileDir, filename));
        } catch (IOException e) {
            throw new ServiceException("read.file.fail");
        } catch (GitAPIException e) {
            e.printStackTrace();
            throw new ServiceException("git.operate.fail");
        }
    }

    @Override
    public void pmdCheck(Long projectId, Long moduleId, String branch, String style) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new ServiceException("project.not.found"));
        Module module = moduleRepo.findById(moduleId).orElseThrow(() -> new ServiceException("module.not.found"));

        //get work dir
        String dir = System.getProperty("user.home") + "/rdep workdir/";
        File projectDir = new File(dir + project.getName());
        File moduleDir = new File(projectDir, module.getServiceId());

        try (Git git = Git.open(projectDir)) {
            if (!git.getRepository().getBranch().equals(branch)) {
                //check branch if not exist throw exception
                Ref ref = git.checkout().setName(branch).call();
                if (ref.getName().length() == 0)
                    throw new ServiceException("branch.not.exist");
            }
        } catch (IOException e) {
            throw new ServiceException("read.file.fail");
        } catch (GitAPIException e) {
            e.printStackTrace();
            throw new ServiceException("git.operate.fail");
        }

        String styleArg;
        if (style.equals("google"))
            styleArg = "google_checks.xml";
        else
            styleArg = "sun_checks.xml";

        File resultFileDir = new File(moduleDir, "pmd");
        if (!resultFileDir.exists())
            resultFileDir.mkdir();
        File cacheDirectory = new File(resultFileDir, "cache");
        if (!cacheDirectory.exists())
            cacheDirectory.mkdir();

        String filename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xml";

        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(moduleDir);
        //-cache参数，加快分析，增量分析
        builder.command("pmd", "pmd",
                "-cache", "cache",
                "-d", ".",                             //被分析的代码目录
                "-R", "rulesets/java/quickstart.xml",  //规则
                "-f", "xml",                           //分析结果文件类型
                "-r", "pmd/" + filename);              //分析结果文件名
        Process process = null;
        try {
            process = builder.start();
            //TODO 如何判定是否成功。成功了也会向error stream写东西；exit value不同的操作系统返回值不同
            int value = process.waitFor();
            //正常，返回4，有错误返回0；
            File result = new File(resultFileDir, filename);
            if (!result.exists()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line;
                if ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                throw new ServiceException("pmd.check.failed", line);
            }
        } catch (IOException | InterruptedException e) {
            throw new ServiceException("pmd.check.failed", e.getMessage());
        } finally {
//            if (null != process) {
//                process.destroy();
//            }
        }
    }
}
