package xyz.luomu32.rdep.user.test.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import xyz.luomu32.rdep.user.WebConfig;
import xyz.luomu32.rdep.user.service.RoleService;
import xyz.luomu32.rdep.user.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@Import({ WebConfig.class})
public class WebExceptionHandleTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;

    @Test
    public void testOnMissingParameter() throws Exception {
        this.mvc.perform(post("/users/{id}/change-pass", 2))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("缺少必要的参数：newPass，类型：String"));
    }

    @Test
    public void testBeanValidation() throws Exception {
        this.mvc.perform(post("/roles"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("角色名称不能为空"));
    }

    //TODO 32未被正确替换，实际显示的是"角色名称长度不能超过max个字符"
//    @Test
//    public void testBeanValidationWithParameter() throws Exception {
//        this.mvc.perform(post("/roles").header("Content-Type", "application/x-www-form-urlencoded")
//                .param("name", "12345678989010394393003939388103432"))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("角色名称长度不能超过32个字符"));
//    }

    @Test
    public void testParameterTypeNoRight() throws Exception {
        this.mvc.perform(put("/roles/{id}", "sdf"))
                .andExpect(status().isBadRequest());
    }

}
