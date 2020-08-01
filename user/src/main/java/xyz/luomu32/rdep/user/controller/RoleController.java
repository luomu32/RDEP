package xyz.luomu32.rdep.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.luomu32.rdep.user.pojo.RoleCreateCmd;
import xyz.luomu32.rdep.user.pojo.RoleRequest;
import xyz.luomu32.rdep.user.pojo.RoleResponse;
import xyz.luomu32.rdep.user.pojo.RoleUpdateCmd;
import xyz.luomu32.rdep.user.service.RoleService;

@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    void create(@Validated RoleCreateCmd createCmd) {
        roleService.create(createCmd);
    }

    @PutMapping("{id}")
    void update(@PathVariable Long id,
                @Validated RoleUpdateCmd updateCmd) {
        updateCmd.setId(id);
        roleService.update(updateCmd);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable Long id) {
        roleService.delete(id);
    }

    @GetMapping
    Page<RoleResponse> fetch(Pageable pageable) {
        return roleService.fetch(pageable);
    }
}
