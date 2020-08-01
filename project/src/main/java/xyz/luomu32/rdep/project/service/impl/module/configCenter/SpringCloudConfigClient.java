package xyz.luomu32.rdep.project.service.impl.module.configCenter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//TODO 支持label
//TODO 支持加密
//TODO git 提交日志与操作日志需不需合并
public class SpringCloudConfigClient implements Client {

    private OkHttpClient client = new OkHttpClient();

    private String url;
    private PathDeterminer pathDeterminer;

    public SpringCloudConfigClient(String url, SpringCloudConfigPathDeterminer pathDeterminer) {
        this.url = url;
        this.pathDeterminer = pathDeterminer;
    }

    @Override
    public Map<String, String> list() {

        String path = pathDeterminer.getPath();

        Request request = new Request.Builder()
                .url(this.url + path + ".properties")
                .build();

        try (Response response = client.newCall(request).execute()) {
            Properties properties = new Properties();
            properties.load(new StringReader(response.body().string()));

            Map<String, String> result = new HashMap<>();
            properties.forEach((k, v) -> {
                result.put(k.toString(), v.toString());
            });

            return result;

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    @Override
    public void setValue(String key, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(String key) {
        throw new UnsupportedOperationException();
    }
}
