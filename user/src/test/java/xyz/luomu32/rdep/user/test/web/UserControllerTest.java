package xyz.luomu32.rdep.user.test.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.validator.PublicClassValidator;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StringUtils;
import xyz.luomu32.rdep.user.controller.UserController;
import xyz.luomu32.rdep.user.pojo.SimpleRoleResponse;
import xyz.luomu32.rdep.user.pojo.UserCreateCmd;
import xyz.luomu32.rdep.user.pojo.UserResponse;
import xyz.luomu32.rdep.user.service.RoleService;
import xyz.luomu32.rdep.user.service.UserService;

import java.util.Collections;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureRestDocs
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;

    @Test
    public void testCreate() throws Exception {
        ConstrainedFields fields = new ConstrainedFields(UserCreateCmd.class);

        UserCreateCmd userCreateCmd = new UserCreateCmd();
        userCreateCmd.setUsername("zhangsan");
        userCreateCmd.setPassword("123456");
        userCreateCmd.setRealName("张三");
        userCreateCmd.setEmail("zhangsan@xxx.com");
        userCreateCmd.setRoleId(1L);

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/users")
                                .param("username", userCreateCmd.getUsername())
                                .param("realName", userCreateCmd.getRealName())
                                .param("email", userCreateCmd.getEmail())
                                .param("roleId", userCreateCmd.getRoleId().toString())
                                .param("password", userCreateCmd.getPassword())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("user/create", RequestDocumentation.requestParameters(
                        Attributes.attributes(Attributes.key("title").value("Fields for user creation")),
                        fields.parameter("username").description("用户名"),
                        fields.parameter("password").description("密码"),
                        fields.parameter("realName").description("真实姓名"),
                        fields.parameter("email").description("电子邮件"),
                        fields.parameter("roleId").description("所属角色ID")
                )));

        Mockito.verify(this.userService, Mockito.times(1)).create(userCreateCmd);
    }

    @Test
    public void testUpdate() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/users/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("user/update",
                        RequestDocumentation.pathParameters(RequestDocumentation.parameterWithName("id").description("用户ID"))));
    }

    @Test
    public void testChangePass() throws Exception {
        this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/users/{id}/change-pass", 1)
                        .param("newPass", "123456")
                        .param("oldPass", "1234567")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("user/changePass",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("id").description("用户ID")
                        ),
                        RequestDocumentation.requestParameters(
                                RequestDocumentation.parameterWithName("newPass").description("新密码"),
                                RequestDocumentation.parameterWithName("oldPass").description("旧密码")
                        )));

        Mockito.verify(this.userService).changePass(1L, "123456", "1234567");
    }

    @Test
    public void testAuth() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/users/auth")
                        .param("username", "zhangs")
                        .param("password", "1234567")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("user/auth",
                        RequestDocumentation.requestParameters(
                                RequestDocumentation.parameterWithName("username").description("用户名"),
                                RequestDocumentation.parameterWithName("password").description("密码")
                        )));
    }

    @Test
    public void testFetch() throws Exception {

        UserResponse userResponse = new UserResponse();
        userResponse.setUsername("zhangsan");
        userResponse.setRealName("张三");
        userResponse.setId(1L);
        userResponse.setStatus("NEW");
        userResponse.setEmail("zhangsn@der.com");
        SimpleRoleResponse roleResponse = new SimpleRoleResponse();
        roleResponse.setId(1L);
        roleResponse.setName("开发");
        userResponse.setRole(roleResponse);
        Page<UserResponse> responses = new PageImpl<UserResponse>(Collections.singletonList(userResponse));
        BDDMockito.given(this.userService.fetch(Mockito.any(), Mockito.any())).willReturn(responses);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("user/fetch",
                        RequestDocumentation.requestParameters(
                                RequestDocumentation.parameterWithName("roleId").description("所属角色ID").optional(),
                                RequestDocumentation.parameterWithName("username").description("用户名").optional(),
                                RequestDocumentation.parameterWithName("email").description("电子邮箱").optional(),
                                RequestDocumentation.parameterWithName("realName").description("实名").optional(),
                                RequestDocumentation.parameterWithName("pageNo").description("页码").optional(),
                                RequestDocumentation.parameterWithName("pageSize").description("每页显示数").optional()
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("empty").description("是否为空"),
                                PayloadDocumentation.fieldWithPath("first").description("第一页"),
                                PayloadDocumentation.fieldWithPath("last").description("最后一页"),
                                PayloadDocumentation.fieldWithPath("size").description("每页显示最大数量"),
                                PayloadDocumentation.fieldWithPath("number").description("页码"),
                                PayloadDocumentation.fieldWithPath("numberOfElements").description("记录数"),
                                PayloadDocumentation.fieldWithPath("totalPages").description("总页数"),
                                PayloadDocumentation.fieldWithPath("totalElements").description("总记录数"),
                                PayloadDocumentation.fieldWithPath("pageable").description("分页信息"),
                                PayloadDocumentation.fieldWithPath("sort").type(JsonFieldType.OBJECT).description("排序"),
                                PayloadDocumentation.fieldWithPath("sort.unsorted").description("不排序"),
                                PayloadDocumentation.fieldWithPath("sort.sorted").description("排序"),
                                PayloadDocumentation.fieldWithPath("sort.empty").description("排序字段是否为空"),
                                PayloadDocumentation.fieldWithPath("content").type(JsonFieldType.ARRAY).description("内容"),
                                PayloadDocumentation.fieldWithPath("content[].id").description("用户ID"),
                                PayloadDocumentation.fieldWithPath("content[].username").description("用户ID"),
                                PayloadDocumentation.fieldWithPath("content[].realName").description("用户ID"),
                                PayloadDocumentation.fieldWithPath("content[].email").description("用户ID"),
                                PayloadDocumentation.fieldWithPath("content[].status").description("用户ID"),
                                PayloadDocumentation.fieldWithPath("content[].role").type(JsonFieldType.OBJECT).description("用户所属角色"),
                                PayloadDocumentation.fieldWithPath("content[].role.id").description("角色ID"),
                                PayloadDocumentation.fieldWithPath("content[].role.name").description("角色名称")
                        )
                ));
    }


    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return PayloadDocumentation.fieldWithPath(path).attributes(Attributes.key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }

        private ParameterDescriptor parameter(String path) {

            String cons = StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ");
            return RequestDocumentation.parameterWithName(path).attributes(Attributes.key("constraints").value(cons));
        }
    }
}
