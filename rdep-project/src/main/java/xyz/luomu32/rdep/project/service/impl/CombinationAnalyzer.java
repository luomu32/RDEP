package xyz.luomu32.rdep.project.service.impl;

import lombok.extern.slf4j.Slf4j;
import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.project.entity.Module;
import xyz.luomu32.rdep.project.service.Analyzer;
import xyz.luomu32.rdep.project.service.AnalyzerResult;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CombinationAnalyzer implements Analyzer {

    private Map<String, Analyzer> analyzers = new HashMap<>();

    public CombinationAnalyzer registerAnalyzer(String supportedLanguage, Analyzer analyzer) {
        analyzers.put(supportedLanguage, analyzer);
        return this;
    }


    @Override
    public AnalyzerResult analyze(File workDir, Module module) {
        if (null == workDir) {
            return null;
        }
        if (!workDir.exists()) {
            return null;
        }
        String language = languageDeterminer(workDir);
        if (!analyzers.containsKey(language)) {
            throw new ServiceException("not support language " + language);
        }
        log.info("the project is use {} language", language);

        Analyzer analyzer = analyzers.get(language);
        return analyzer.analyze(workDir, module);
    }

    //TODO 更严谨。比如分析大部分文件，如果某一种文件占有比例高，认为是该语言。比如60%都是.java文件
    private String languageDeterminer(File workDir) {

        for (File file : workDir.listFiles()) {
            if (file.getName().equals("pom.xml")) {
                return "java";
            }
        }

        return null;
    }
}
