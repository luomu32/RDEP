package xyz.luomu32.rdep.user.test.web;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import xyz.luomu32.rdep.user.UserConfig;
import xyz.luomu32.rdep.user.controller.UserController;
import xyz.luomu32.rdep.user.service.UserService;

import javax.servlet.RequestDispatcher;

import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureRestDocs
@Import(UserConfig.class)
public class ErrorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void errorExample() throws Exception {

        BDDMockito.given(this.userService.fetch(Mockito.any(), Mockito.any())).willThrow(new RuntimeException());

        this.mockMvc
                .perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code", is(isEmptyOrNullString())))
                .andExpect(jsonPath("message", is(notNullValue())))
                .andDo(document("error",
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("错误码"),
                                fieldWithPath("message").description("错误消息"))));
    }
}
