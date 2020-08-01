package xyz.luomu32.rdep.user.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RoleUpdateCmd {

    private Long id;

    @NotBlank(message = "{role.name.not.blank}")
    @Size(max = 32, message = "{role.name.size.too.long}")
    private String name;

    @Size(max = 256, message = "{role.remark.size.too.long}")
    private String remark;
}
