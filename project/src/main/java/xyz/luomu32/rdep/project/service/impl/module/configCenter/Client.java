package xyz.luomu32.rdep.project.service.impl.module.configCenter;

import java.util.Map;

public interface Client {

    Map<String, String> list();

    void setValue(String key, String value);

    void delete(String key);
}
