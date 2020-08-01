package xyz.luomu32.rdep.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.luomu32.rdep.user.pojo.RoleCreateCmd;
import xyz.luomu32.rdep.user.pojo.RoleRequest;
import xyz.luomu32.rdep.user.pojo.RoleResponse;
import xyz.luomu32.rdep.user.pojo.RoleUpdateCmd;

public interface RoleService {
    void create(RoleCreateCmd createCmd);

    void update(RoleUpdateCmd updateCmd);

    void delete(Long id);

    Page<RoleResponse> fetch(Pageable pageable);
}
