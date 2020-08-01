package xyz.luomu32.rdep.project.test;


import okhttp3.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.luomu32.rdep.project.service.JenkinsService;

import java.io.IOException;
import java.util.stream.Stream;

//@RunWith(SpringRunner.class)
//@ImportAutoConfiguration(FeignAutoConfiguration.class)
//@WebMvcTest
public class JenkinsTest {

    @Autowired
    private JenkinsService jenkinsService;

//    @Test
    public void testJob() {
        OkHttpClient client = new OkHttpClient();
        //user
        //new FormBody.Builder()
        //                        .add("user", "root:123456")
        //                        .build()


        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://127.0.0.1:8989/job/dubbo-demo/build?token=1234")
                .method("POST", body)
                .addHeader("Jenkins-Crumb", "9edc43c3e99ede0f135097a00483ce25a6575feac5acde96ab8cd87b73a4e0fe")
                .addHeader("Authorization", "Basic cm9vdDoxMjM0NTY=")
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.code());
            System.out.println(response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Test
    public void testCreateBuild() {
        Assertions.assertThat(jenkinsService).isNotNull();
    }

}
