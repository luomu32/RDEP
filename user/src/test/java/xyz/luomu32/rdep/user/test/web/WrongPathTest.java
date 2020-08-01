package xyz.luomu32.rdep.user.test.web;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import xyz.luomu32.rdep.user.controller.UserController;
import xyz.luomu32.rdep.user.pojo.UserRequest;
import xyz.luomu32.rdep.user.pojo.UserUpdateCmd;
import xyz.luomu32.rdep.user.service.UserService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class WrongPathTest {
    @Autowired
    private UserController userController;
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testWrongHttpMethod() throws Exception {
        given(this.userService.fetch(any(UserRequest.class), eq(PageRequest.of(0, 20))))
                .willReturn(Page.empty());

        this.mockMvc.perform(get("/users/auth"))
                .andExpect(status().is(405));

        this.mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());

        this.mockMvc.perform(put("/users/1"));
        verify(this.userService, times(1)).update(any(UserUpdateCmd.class));

        this.mockMvc.perform(get("/users/1"));
        verify(this.userService, times(1)).fetch(any());
    }
}
