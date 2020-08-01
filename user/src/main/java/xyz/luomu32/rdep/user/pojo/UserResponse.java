package xyz.luomu32.rdep.user.pojo;

import lombok.Data;
import xyz.luomu32.rdep.user.entity.User;

@Data
public class UserResponse {

    private Long id;

    private String username;

    private String realName;

    private String email;

    private String status;

    private SimpleRoleResponse role;

    public static UserResponse from(User user) {
        if (null == user) {
            return null;
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setRealName(user.getRealName());
        SimpleRoleResponse simpleRoleResponse = new SimpleRoleResponse();
        simpleRoleResponse.setId(user.getRole().getId());
        simpleRoleResponse.setName(user.getRole().getName());
        userResponse.setRole(simpleRoleResponse);
        userResponse.setEmail(user.getEmail());
        userResponse.setStatus(user.getStatus().name());
        return userResponse;
    }

}

