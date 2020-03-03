package xyz.luomu32.rdep.user.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RoleRequest {

    private Long id;

    @NotBlank(message = "{role.name.not.blank}")
    @Size(max = 32, message = "{role.name.size.too.long}")
    private String name;
    private String remark;
}
