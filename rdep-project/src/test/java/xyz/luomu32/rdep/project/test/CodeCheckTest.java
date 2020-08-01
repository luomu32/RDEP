package xyz.luomu32.rdep.project.test;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class CodeCheckTest {

    //推荐使用ProcessBuilder来构建Process，而不是直接使用Process。
    //直接使用Process的问题：
    // 1.目录有空格
    // 2.死锁
    @Test
    public void testCheck() {
        Process process;
        try {
            String styleArg = "google_checks.xml";
            String resultFile = "/Users/luomu32/rdep workdir/spring-cloud-demo/warehouse/checkstyle/49929384.xml".replaceAll(" ", "\" \"");
            String moduleDir = "/Users/luomu32/rdep workdir/spring-cloud-demo/warehouse".replaceAll(" ", "\" \"");
            process = Runtime.getRuntime().exec("checkstyle -c google_checks.xml -o " + resultFile + " -f xml " + moduleDir);
//            String path = System.getProperty("user.dir");
//            process = Runtime.getRuntime().exec("checkstyle -c google_checks.xml " + path);//查看我的 .bash_history里面的grep 命令使用历史记录
            new Thread(() -> {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            new Thread(() -> {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            //阻塞当前线程，等待子进程处理完毕后返回
            int exitValue = process.waitFor();
            //exitValue，不同的操作系统，不同的执行命令，不同；
            if (exitValue == 0) {
                System.out.println("successfully executed the linux command");
            } else
                System.out.println(exitValue);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testProcessBuilder() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("checkstyle",
                "-c",
                "google_checksf.xml",
                "-f",
                "xml",
//                "-o",
//                "checkstyle/49929384.xml",
                ".");
        builder.directory(new File("/Users/luomu32/rdep workdir/spring-cloud-demo/warehouse"));
        builder.redirectErrorStream(true);
        Process process = builder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        if ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    @Test
    public void testConsole() {
        int d = 100_100;
    }

    @Test
    public void testParseResult() throws DocumentException {
        File file = new File("/Users/luomu32/rdep workdir/spring-cloud-demo/warehouse/checkstyle/49929384.xml");

        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(file);

        System.out.println(doc.getRootElement());
        List<Element> fileElement = doc.getRootElement().elements("file");
        for (Element element : fileElement) {
            List<Element> errors = element.elements("error");
            if (errors.isEmpty())
                continue;
            String name = element.attribute("name").getValue();

            int pos = name.indexOf("java") + 5;
            name = name.substring(pos);
            pos = name.lastIndexOf('/');
            String code = name.substring(pos + 1);
            String packageName = name.substring(0, pos).replaceAll("/", ".");
            System.out.println(code);
            System.out.println(packageName);
            System.out.println("====");
        }
    }

}
