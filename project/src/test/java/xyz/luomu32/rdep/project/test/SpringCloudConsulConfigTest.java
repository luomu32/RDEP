//package xyz.luomu32.rdep.project.test;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * 基于Spring Cloud Consul的配置中心，操作测试
// */
//public class SpringCloudConsulConfigTest {
//
//    @Test
//    public void testGetKeys() {
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("http://127.0.0.1:8500/v1/kv/spring-cloud-demo/user,dev/?keys=true")
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            System.out.println(response.body().string());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void profileOverwrite() {
//
//        String[] paths = {
//                "spring-cloud-demo/user,dev/",
//                "spring-cloud-demo/user,dev/spring.datasource.jdbc.username",
//                "spring-cloud-demo/user,prod/",
//                "spring-cloud-demo/user,prod/server.port",
//                "spring-cloud-demo/user/",
//                "spring-cloud-demo/user/server.port"
//        };
//
//        String noProfilePrefix = "spring-cloud-demo/user/";
//
//        List<String> newPaths = Stream.of(paths)
//                .filter(key -> !key.equals(noProfilePrefix) && key.startsWith(noProfilePrefix))
//                .map(key -> key.substring(noProfilePrefix.length()))
//                .collect(Collectors.toList());
//
//        assert newPaths.size() == 1;
//        assert newPaths.get(0).equals("server.port");
//
//        String profilePrefix = "spring-cloud-demo/user,dev/";
//        newPaths = Stream.of(paths)
//                .filter(key -> !key.equals(profilePrefix) && key.startsWith(profilePrefix))
//                .map(key -> key.substring(profilePrefix.length()))
//                .collect(Collectors.toList());
//        assert newPaths.size() == 1;
//        assert newPaths.get(0).equals("spring.datasource.jdbc.username");
//    }
//
//    @Test
//    public void testGetValues() {
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("http://127.0.0.1:8500/v1/kv/spring-cloud-demo/user/spring.datasource.jdbc.usernamei?raw=true")
//                .build();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        try (Response response = client.newCall(request).execute()) {
//            String v = response.body().string();
//
//            System.out.println(v.length());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
