package xyz.luomu32.rdep.project.service.impl;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ZookeeperClientManager {

    private final Map<String, CuratorFramework> clients = new ConcurrentHashMap<>();

    public CuratorFramework fetchClient(String address) {
        return clients.computeIfAbsent(address, (key) -> {
            CuratorFramework client = CuratorFrameworkFactory.newClient(key, new RetryOneTime(5000));

            client.start();
            return client;
        });
    }
}
