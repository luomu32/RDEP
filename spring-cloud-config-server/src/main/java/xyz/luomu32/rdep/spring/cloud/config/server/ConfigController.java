package xyz.luomu32.rdep.spring.cloud.config.server;

import org.eclipse.jgit.api.Git;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

import static xyz.luomu32.rdep.spring.cloud.config.server.ConfigServerApp.GIT_PATH;


@RestController
@RequestMapping
public class ConfigController {

    @PostMapping("{name}/{profile}/{key}")
    public void createOrUpdate(@PathVariable String name,
                               @PathVariable String profile,
                               @PathVariable String key,
                               String value,
                               String authorName,
                               String authorEmail) {

        if (StringUtils.isEmpty(value)) {
            return;
        }
        if (StringUtils.isEmpty(authorName)) {
            return;
        }

        String configFileName;
        if (profile.equals("all")) {
            configFileName = name;
        } else {
            configFileName = name + "-" + profile;
        }
        try {
            Optional<Path> configFile = Files.list(Paths.get(GIT_PATH)).filter(p ->
                    p.getFileName().startsWith(configFileName)
            ).findFirst();

            String commitMessage;
            if (!configFile.isPresent()) {
                Properties properties = new Properties();
                properties.setProperty(key, value);
                properties.store(new FileOutputStream(new File(GIT_PATH + "/" + configFileName + ".properties")), "");

                commitMessage = buildCommitMessage(true, name, key, value, null);
            } else {
                Properties properties = new Properties();
                properties.load(new FileInputStream(configFile.get().toFile()));

                if (properties.contains(key)) {
                    String oldValue = properties.get(key).toString();
                    commitMessage = buildCommitMessage(false, name, key, value, oldValue);
                } else {
                    commitMessage = buildCommitMessage(true, name, key, value, null);
                }
                properties.setProperty(key, value);

                properties.store(new FileOutputStream(new File(GIT_PATH + "/" + configFileName + ".properties")), "");
            }

            try (Git git = Git.open(new File(GIT_PATH))) {
                git.add().addFilepattern(".").call();
                git.commit().setAuthor(authorName, authorEmail).setMessage(commitMessage).call();
            } catch (Exception e) {
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("{name}/{profile}/{label}/{key}")
    public void createOrUpdate(@PathVariable String name,
                               @PathVariable String profile,
                               @PathVariable String label,
                               @PathVariable String key,
                               String value) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("{name}/{profile}/{key}")
    public void delete(@PathVariable String name,
                       @PathVariable String profile,
                       @PathVariable String key,
                       String authorName,
                       String authorEmail) {
        if (StringUtils.isEmpty(authorName)) {
            return;
        }

        String configFileName;
        if (profile.equals("all")) {
            configFileName = name;
        } else {
            configFileName = name + "-" + profile;
        }
        try {
            Optional<Path> configFile = Files.list(Paths.get(GIT_PATH)).filter(p ->
                    p.getFileName().startsWith(configFileName)
            ).findFirst();

            if (configFile.isPresent()) {
                Properties properties = new Properties();
                properties.load(new FileInputStream(configFile.get().toFile()));

                if (!properties.contains(key)) {
                    return;
                }
                properties.remove(key);
                properties.store(new FileOutputStream(new File(GIT_PATH + "/" + configFileName + ".properties")), "");

                try (Git git = Git.open(new File(GIT_PATH))) {
                    git.add().addFilepattern(".").call();
                    git.commit().setAuthor(authorName, authorEmail).setMessage("remove config from " + name + ".key:" + key).call();
                } catch (Exception e) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildCommitMessage(boolean add, String name, String key, String value, String oldValue) {
        if (add) {
            return "add new config into " + name + ".key:" + key + " and value:" + value;
        } else {
            return "change config:" + key + " from " + oldValue + " to " + value;
        }
    }
}
