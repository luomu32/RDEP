package xyz.luomu32.rdep.user.pojo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserCreateCmd {

    @NotNull(message = "{user.role.id.not.null}")
    private Long roleId;

    @NotBlank(message = "{user.username.not.blank}")
    @Size(max = 64, message = "{user.username.size.too.long}")
    private String username;

    @NotBlank(message = "{user.password.not.blank}")
    @Size(max = 16, message = "{user.password.size.too.long}")
    private String password;

    @NotBlank(message = "{user.email.not.blank}")
    @Email(message = "{user.email.not.valid}")
    @Size(max = 16, message = "{user.email.size.too.long}")
    private String email;

    @NotBlank(message = "{user.real.name.not.blank}")
    @Size(max = 8, message = "{user.real.name.size.too.long}")
    private String realName;
}
