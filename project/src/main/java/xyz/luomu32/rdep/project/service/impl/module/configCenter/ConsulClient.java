package xyz.luomu32.rdep.project.service.impl.module.configCenter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsulClient implements Client {

    private final OkHttpClient client = new OkHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String url;
    private SpringCloudPathDeterminer pathDeterminer;

    public ConsulClient(String url, SpringCloudPathDeterminer pathDeterminer) {
        this.url = url;
        this.pathDeterminer = pathDeterminer;
    }

    /**
     * 如果存在一个应用，name:user，那么如果/config/user目录下有server.port 1245，/config/user,dev目录下也有server.port 3333
     * 如果应用没有设置profile，那么server.port的值是1245，如果应用启用profile：dev，那么server.port的值是3333
     * 如果应用设置的profile，比如prod，没有设置server.port，那么值也是1245。
     * <p>
     * 如果应用没有使用profile，直接在/config/{applicationName}寻找值
     * 如果应用使用了profile，/config/{applicationName}和/config/{application},{profile}取交集，有重复的，profile的覆盖没有profile的
     */
    @Override
    public Map<String, String> list() {

        String path = pathDeterminer.getPath();

        Map<String, String> result = new HashMap<>();

        Request request = new Request.Builder()
                .url(this.url + "/v1/kv" + path + "/?keys=true")
                .build();

        try (Response response = client.newCall(request).execute()) {
            List<String> keys = objectMapper.readValue(response.body().string(),
                    objectMapper.getTypeFactory().
                            constructCollectionType(List.class, String.class));

            String prefix = path.substring(1) + "/";
            keys = keys.stream()
                    .filter(key -> !key.equals(prefix) && key.startsWith(prefix))
                    .map(key -> key.substring(prefix.length()))
                    .collect(Collectors.toList());

            keys.forEach(k -> {
                result.put(k, this.getValue(k, path));
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getValue(String key, String path) {
        Request request = new Request.Builder()
                .url(this.url + "/v1/kv/" + path + "/" + key + "?raw=true")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String value = response.body().string();
            if (value.length() == 0)
                return null;
            else return value;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setValue(String key, String value) {
        String path = this.pathDeterminer.getPath();
        Request request = new Request.Builder()
                .url(this.url + "/v1/kv" + path + "/" + key)
                .put(okhttp3.RequestBody.create(value.getBytes()))
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String key) {
        String path = this.pathDeterminer.getPath();

        Request request = new Request.Builder()
                .url(this.url + "/v1/kv" + path + "/" + key)
                .delete()
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
