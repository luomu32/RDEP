package xyz.luomu32.rdep.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.luomu32.rdep.user.pojo.UserCreateCmd;
import xyz.luomu32.rdep.user.pojo.UserRequest;
import xyz.luomu32.rdep.user.pojo.UserResponse;

import java.util.List;

public interface UserService {
    void create(UserCreateCmd createCmd);

    void changePass(Long id, String newPass, String oldPass);

    UserResponse auth(String username, String password);

    Page<UserResponse> fetch(UserRequest request, Pageable pageable);

    List<UserResponse> search(String username);

    UserResponse fetch(Long id);
}
