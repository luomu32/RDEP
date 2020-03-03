//package xyz.luomu32.rdep.project.test;
//
//import org.apache.commons.exec.CommandLine;
//import org.apache.commons.exec.DefaultExecutor;
//import org.eclipse.jgit.api.Git;
//import org.eclipse.jgit.api.errors.GitAPIException;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//
//public class Maven {
//
//    @Test
//    public void build() throws IOException, InterruptedException {
//        CommandLine cmdLine = new CommandLine("mvn");
//        cmdLine.addArgument("-B"); //批量模式
//        cmdLine.addArgument("-f /Users/luomu32/rdep workdir/dubbo-demo/pom.xml");
//        cmdLine.addArgument("clean");
//        cmdLine.addArgument("package");
//
////        CommandLine cmdLine=new CommandLine("mvn clean");
//
//        DefaultExecutor executor = new DefaultExecutor();
////        executor.setWorkingDirectory(new File("/Users/luomu32/rdep workdir/dubbo-demo/"));
////        int exitValue = executor.execute(cmdLine, new ExecuteResultHandler() {
////            @Override
////            public void onProcessComplete(int exitValue) {
////
////            }
////
////            @Override
////            public void onProcessFailed(ExecuteException e) {
////
////            }
////        });
//
//
////        Process process = Runtime.getRuntime().exec("mvn -B -f /Users/luomu32/rdep\\ workdir/dubbo-demo/pom.xml clean package");
////        process.waitFor();
////        System.out.println(process.exitValue());
//    }
//
//
//    @Test
//    public void git() throws GitAPIException {
//        Git.cloneRepository()
//                .setURI("git@github.com:luomu32/dubbo-demo.git")
//                .setDirectory(new File(" /Users/luomu32/rdep workdir/dubbo-demo/订单"))
//                .call();
//    }
//}
