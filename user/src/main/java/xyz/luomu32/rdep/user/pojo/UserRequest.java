package xyz.luomu32.rdep.user.pojo;

import lombok.Data;

@Data
public class UserRequest {

    private Long roleId;

    //    @NotBlank(message = "user.username.not.blank")
    private String username;

    private String password;

    private String email;

    private String realName;
}
