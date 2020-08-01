package xyz.luomu32.rdep.project.test;

import org.apache.commons.exec.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Maven {

    @Test
    public void build() throws IOException, InterruptedException {
        CommandLine cmdLine = new CommandLine("mvn");
        cmdLine.addArgument("-B"); //批量模式
        cmdLine.addArgument("-f");
        cmdLine.addArgument("pom.xml");
        cmdLine.addArgument("clean");
        cmdLine.addArgument("package");

//        CommandLine cmdLine=new CommandLine("mvn clean");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(new File("/Users/luomu32/rdep workdir/dubbo-demo/"));
        executor.setWatchdog(new ExecuteWatchdog(6000));
        executor.execute(cmdLine, new ExecuteResultHandler() {
            @Override
            public void onProcessComplete(int exitValue) {
                System.out.println(exitValue);
                countDownLatch.countDown();
            }

            @Override
            public void onProcessFailed(ExecuteException e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();
//        Process process = Runtime.getRuntime().exec("mvn -B -f /Users/luomu32/rdep\\ workdir/dubbo-demo/pom.xml clean package");
//        process.waitFor();
//        System.out.println(process.exitValue());
    }


    @Test
    public void git() throws GitAPIException {
        Git.cloneRepository()
                .setURI("git@github.com:luomu32/dubbo-demo.git")
                .setDirectory(new File(" /Users/luomu32/rdep workdir/dubbo-demo/订单"))
                .call();
    }
}
