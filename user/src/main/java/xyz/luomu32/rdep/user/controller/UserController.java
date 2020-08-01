package xyz.luomu32.rdep.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.user.pojo.UserCreateCmd;
import xyz.luomu32.rdep.user.pojo.UserRequest;
import xyz.luomu32.rdep.user.pojo.UserResponse;
import xyz.luomu32.rdep.user.pojo.UserUpdateCmd;
import xyz.luomu32.rdep.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    void create(@Validated UserCreateCmd createCmd) {
        userService.create(createCmd);
    }

    @PutMapping("{id}")
    void update(UserUpdateCmd userUpdateCmd) {
        userService.update(userUpdateCmd);
    }

    @PostMapping("{id}/change-pass")
    void changePass(@PathVariable Long id, @RequestParam String newPass, @RequestParam String oldPass) {
        userService.changePass(id, newPass, oldPass);
    }

    @PostMapping("auth")
    public UserResponse auth(@RequestParam String username, @RequestParam String password) {
        return userService.auth(username, password);
    }

    @GetMapping()
    Page<UserResponse> fetch(UserRequest request, Pageable pageable) {
        return userService.fetch(request, pageable);
    }


    @GetMapping("search")
    List<UserResponse> fetch(String username) {
        return userService.search(username);
    }

    @GetMapping("{id:\\d+}")
    UserResponse fetch(@PathVariable Long id) {
        return userService.fetch(id);
    }

}
