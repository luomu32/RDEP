package xyz.luomu32.rdep.user.test.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import xyz.luomu32.rdep.user.WebConfig;
import xyz.luomu32.rdep.user.controller.UserController;
import xyz.luomu32.rdep.user.service.UserService;

import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureRestDocs
@Import(WebConfig.class)
public class ErrorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void errorExample() throws Exception {

        BDDMockito.given(this.userService.fetch(Mockito.any(), Mockito.any())).willThrow(new RuntimeException("something happened"));

        this.mockMvc
                .perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code", is(emptyOrNullString())))
                .andExpect(jsonPath("message", is(notNullValue())))
                .andDo(document("error",
                        responseFields(
                                fieldWithPath("code").description("错误码"),
                                fieldWithPath("message").description("错误消息"),
                                fieldWithPath("detail").description("错误详细信息"))));
    }
}
