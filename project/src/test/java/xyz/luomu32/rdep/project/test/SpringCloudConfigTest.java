//package xyz.luomu32.rdep.project.test;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.io.StringReader;
//import java.util.Properties;
//
//public class SpringCloudConfigTest {
//
//    /**
//     * "{[/{name}-{profiles}.yml || /{name}-{profiles}.yaml],methods=[GET]}"
//     * "{[/{name}-{profiles}.properties],methods=[GET]}"
//     * "{[/{name}/{profiles:.*[^-].*}],methods=[GET]}"
//     * "{[/{name}/{profiles}/{label:.*}],methods=[GET]}"
//     * "{[/{label}/{name}-{profiles}.properties],methods=[GET]}"
//     * "{[/{name}-{profiles}.json],methods=[GET]}"
//     * "{[/{label}/{name}-{profiles}.json],methods=[GET]}"
//     * "{[/{label}/{name}-{profiles}.yml || /{label}/{name}-{profiles}.yaml],methods=[GET]}"
//     * "{[/{label}/{name}-{profiles}.yml || /{label}/{name}-{profiles}.yaml],methods=[GET]}"
//     * "{[/{name}/{profile}/{label}/**],methods=[GET],produces=[application/octet-stream]}"
//     * "{[/{name}/{profile}/{label}/**],methods=[GET]}"
//     * "{[/{name}/{profile}/**],methods=[GET],params=[useDefaultLabel]}"
//     */
//
//    @Test
//    public void testGetKeys() {
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("http://localhost:8900/payment-dev.properties")
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            String result = response.body().string();
//            Properties properties = new Properties();
//            properties.load(new StringReader(result));
//
//            properties.forEach((k, v) -> {
//                System.out.println("key:" + k.toString() + "---values:" + v.toString());
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
