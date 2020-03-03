package xyz.luomu32.rdep.project.service.impl;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import xyz.luomu32.rdep.project.entity.Module;
import xyz.luomu32.rdep.project.service.Analyzer;
import xyz.luomu32.rdep.project.service.AnalyzerResult;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
public class JavaProjectAnalyzer implements Analyzer {
    @Override
    public AnalyzerResult analyze(File workDir, Module module) {
        //build system maven or gradle or null

        List<File> files = new ArrayList<>();
        digger(workDir, module.getName(), files);

        BuildInfo buildInfo = parse(files);
        if (null == buildInfo)
            throw new UnsupportedOperationException();

        Map<String, List<String>> dependencies = buildInfo.getDependencies();
        boolean dubbo = false;
        boolean springBootActuator = false;
        boolean springCloudConfig = false;
        if (dependencies.containsKey("org.springframework.boot")) {
            if (dependencies.get("org.springframework.boot").stream().filter(dependency -> dependency.equals("spring-boot-starter-actuator")).findAny().isPresent())
                springBootActuator = true;
        } else if (dependencies.containsKey("org.springframework.cloud")) {
            if (dependencies.get("org.springframework.cloud").stream().filter(dependency -> dependency.equals("spring-cloud-config-client")).findAny().isPresent())
                springCloudConfig = true;
        } else if (dependencies.containsKey("com.alibaba.boot")) {
            if (dependencies.get("com.alibaba.boot").stream().filter(dependency -> dependency.equals("dubbo-spring-boot-starter")).findAny().isPresent())
                dubbo = true;
        } else if (dependencies.containsKey("com.alibaba")) {
            if (dependencies.get("com.alibaba").stream().filter(dependency -> dependency.equals("dubbo")).findAny().isPresent())
                dubbo = true;
        } else if (dependencies.containsKey("org.apache.dubbo")) {
            dubbo = true;
        }
        ExtInfo extInfo = new ExtInfo();
        extInfo.setDubbo(dubbo);
        extInfo.setSpringBootActuator(springBootActuator);
        extInfo.setSpringCloudConfig(springCloudConfig);

        AnalyzerResult result = new AnalyzerResult();
        try {
            result.setExt(new ObjectMapper().writeValueAsString(extInfo));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public void digger(File workDir, String moduleName, List<File> files) {
        for (File file : workDir.listFiles(pathname -> pathname.isFile() || pathname.getName().equals(moduleName))) {
            if (file.getName().equals("pom.xml")) {
                files.add(file);
            } else if (file.getName().endsWith(".gradle")) {

            } else if (file.getName().equals(moduleName)) {
                for (File listFile : file.listFiles(pathname -> pathname.isFile())) {
                    if (listFile.getName().equals("pom.xml")) {
                        files.add(listFile);
                    }
                }
            }
        }
    }

    private BuildInfo parse(List<File> files) {
        if (files.isEmpty())
            return null;

        BuildInfo buildInfo = new BuildInfo();

        if (files.get(0).getName().equals("pom.xml")) {
            SAXReader reader = new SAXReader();
            files.forEach(file -> {
                try {
                    Document document = reader.read(file);
                    Element root = document.getRootElement();
                    Iterator<Element> dependencies = root.elementIterator("dependencies");
                    while (dependencies.hasNext()) {
                        dependencies.next().elementIterator("dependency").forEachRemaining(d -> {
                            String group = d.element("groupId").getText();
                            String artifact = d.element("artifactId").getText();
                            buildInfo.addDependency(group, artifact);
                        });
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            });
            return buildInfo;
        }
        return null;
    }

    @Slf4j
    static class BuildInfo {

        //        private List<Dependency> dependencies = new ArrayList<>();
        private Map<String, List<String>> dependencies = new HashMap<>();

        public void addDependency(String group, String artifact) {
            log.info("add dependency,group:{},artifact:{}", group, artifact);
            if (dependencies.containsKey(group)) {
                dependencies.get(group).add(artifact);
            } else {
                List<String> artifacts = new ArrayList<>();
                artifacts.add(artifact);
                dependencies.put(group, artifacts);
            }
        }

        public Map<String, List<String>> getDependencies() {
            return dependencies;
        }
    }

    @Data
    class Dependency {
        private String group;

        private String artifact;
    }

    @Getter
    @Setter
    class ExtInfo {
        boolean dubbo;
        boolean springBootActuator;
        boolean springCloudConfig;
    }
}
