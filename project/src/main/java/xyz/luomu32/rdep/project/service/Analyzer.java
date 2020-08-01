package xyz.luomu32.rdep.project.service;

import xyz.luomu32.rdep.project.model.Module;

import java.io.File;

public interface Analyzer {

    AnalyzerResult analyze(File workDir, Module module);
}
