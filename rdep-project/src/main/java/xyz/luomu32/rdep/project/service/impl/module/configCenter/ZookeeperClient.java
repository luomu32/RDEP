package xyz.luomu32.rdep.project.service.impl.module.configCenter;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ZookeeperClient implements Client {

    private CuratorFramework client;
    private SpringCloudPathDeterminer pathDeterminer;

    private static final ConcurrentHashMap<String, CuratorFramework> clients = new ConcurrentHashMap<>();

    public static ZookeeperClient getInstance(String url, SpringCloudPathDeterminer pathDeterminer) {
//        return clients.computeIfAbsent(url, (key) -> {
//            CuratorFramework client = CuratorFrameworkFactory.newClient(url, new RetryOneTime(1000));
//            client.start();
//
//            return new ZookeeperClient(client, pathDeterminer);
//        });

        return new ZookeeperClient(clients.computeIfAbsent(url, (key) -> {
            CuratorFramework client = CuratorFrameworkFactory.newClient(url, new RetryOneTime(1000));
            client.start();
            return client;
        }), pathDeterminer);
    }

    private ZookeeperClient(CuratorFramework client, SpringCloudPathDeterminer pathDeterminer) {
        this.client = client;
        this.pathDeterminer = pathDeterminer;
    }

    @Override
    public Map<String, String> list() {
        Map<String, String> result = new HashMap<>();

        String path = this.pathDeterminer.getPath();

        try {
            client.getChildren().forPath(path).stream().forEach(key -> {
                String value = null;
                try {
                    value = new String(client.getData().forPath(path + "/" + key));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                result.put(key, value);
            });
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getValue(String key, String path) {
        try {
            return new String(client.getData().forPath(path + "/" + key));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setValue(String key, String value) {
        String path = this.pathDeterminer.getPath();

        try {
            client.setData().forPath(path + "/" + key, value.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String key) {
        String path = this.pathDeterminer.getPath();

        try {
            client.delete().forPath(path + "/" + key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
