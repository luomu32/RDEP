package xyz.luomu32.rdep.project.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.project.model.Module;
import xyz.luomu32.rdep.project.repo.ModuleRepo;

import java.io.File;
import java.util.stream.Stream;

@Service
public class ModuleAnalyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleAnalyzer.class);

    @Autowired
    private ModuleRepo moduleRepo;

    @Async
    public void analyze(File dir, Module module) {

        Stream.of(dir.listFiles()).forEach(file -> {
            //Maven,Grade
            if (file.getName().endsWith(".pom")) {

            }
        });
    }


    private void dig(File dir) {
        Stream.of(dir.listFiles()).forEach(file -> {
            if (file.isDirectory())
                dig(file);

            if (file.getName().endsWith(".pom"))
                new MavenAnalyzer().analyze(file);

        });
    }
}
