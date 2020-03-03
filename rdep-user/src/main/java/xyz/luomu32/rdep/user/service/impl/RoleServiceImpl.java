package xyz.luomu32.rdep.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.user.entity.Role;
import xyz.luomu32.rdep.user.pojo.RoleCreateCmd;
import xyz.luomu32.rdep.user.pojo.RoleRequest;
import xyz.luomu32.rdep.user.pojo.RoleResponse;
import xyz.luomu32.rdep.user.pojo.RoleUpdateCmd;
import xyz.luomu32.rdep.user.repo.RoleRepo;
import xyz.luomu32.rdep.user.repo.UserRepo;
import xyz.luomu32.rdep.user.service.RoleService;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public void create(RoleCreateCmd createCmd) {
        Role role = new Role();
        role.setName(createCmd.getName());
        role.setRemark(createCmd.getRemark());
        roleRepo.save(role);
    }

    @Override
    public void update(RoleUpdateCmd updateCmd) {
        Role role = roleRepo.findById(updateCmd.getId()).orElseThrow(() -> new ServiceException("role.not.found"));
        role.setName(updateCmd.getName());
        role.setRemark(updateCmd.getRemark());
        roleRepo.save(role);
    }

    @Override
    public void delete(Long id) {
        if (userRepo.countByRoleId(id) > 0) {
            throw new ServiceException("role.have.users");
        }
        Optional<Role> role = roleRepo.findById(id);
        if (!role.isPresent()) {
            LOGGER.warn("role not found:{}", id);
            return;
        }
        roleRepo.delete(role.get());
    }

    @Override
    public Page<RoleResponse> fetch(Pageable pageable) {
        Page<RoleResponse> result = roleRepo.findAll(pageable).map(role -> {
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setId(role.getId());
            roleResponse.setName(role.getName());
            roleResponse.setRemark(role.getRemark());
            roleResponse.setCreateDatetime(role.getCreateDatetime());
            roleResponse.setCreateBy(role.getCreateBy());
            roleResponse.setLastUpdateBy(role.getLastUpdateBy());
            roleResponse.setLastUpdateDatetime(role.getLastUpdateDatetime());

            roleResponse.setUserCount(userRepo.countByRoleId(role.getId()));
            return roleResponse;
        });
        return result;
    }
}
