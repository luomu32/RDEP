package xyz.luomu32.rdep.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import xyz.luomu32.rdep.common.DeleteFlag;
import xyz.luomu32.rdep.common.exception.ServiceException;
import xyz.luomu32.rdep.user.entity.Role;
import xyz.luomu32.rdep.user.entity.User;
import xyz.luomu32.rdep.user.entity.UserStatus;
import xyz.luomu32.rdep.user.pojo.UserCreateCmd;
import xyz.luomu32.rdep.user.pojo.UserRequest;
import xyz.luomu32.rdep.user.pojo.UserResponse;
import xyz.luomu32.rdep.user.pojo.UserUpdateCmd;
import xyz.luomu32.rdep.user.repo.RoleRepo;
import xyz.luomu32.rdep.user.repo.UserRepo;
import xyz.luomu32.rdep.user.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    private PasswordGenerator passwordGenerator = new PasswordGenerator();

    @Override
    public void create(UserCreateCmd createCmd) {
        Role role = roleRepo.findById(createCmd.getRoleId()).orElseThrow(() -> new ServiceException("role.not.found"));

        Optional<User> user = userRepo.findByUsername(createCmd.getUsername());
        if (user.isPresent())
            throw new ServiceException("user.username.existed");

        createCmd.setPassword(passwordGenerator.generate(8, 2));

        userRepo.save(User.builder()
                .deleted(DeleteFlag.UN_DELETED)
                .status(UserStatus.NORMAL)
                .realName(createCmd.getRealName())
                .username(createCmd.getUsername())
                .password(createCmd.getPassword())
                .email(createCmd.getEmail())
                .role(role)
                .build());
    }

    @Override
    public void update(UserUpdateCmd updateCmd) {

    }

    @Override
    public void changePass(Long id, String newPass, String oldPass) {
        User user = userRepo.findById(id).orElseThrow(() -> new ServiceException("user.not.found"));
        if (!user.getPassword().equals(oldPass)) {
            throw new ServiceException("user.password.not.match");
        }

        user.setPassword(newPass);
        userRepo.save(user);
    }

    @Override
    public UserResponse auth(String username, String password) {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new ServiceException("user.not.found"));
        if (user.getDeleted() == DeleteFlag.DELETED) {
            throw new ServiceException("user.not.found");
        }
        if (!user.getPassword().equals(password)) {
            throw new ServiceException("user.password.not.match");
        }

        return UserResponse.from(user);
    }

    @Override
    public Page<UserResponse> fetch(UserRequest request, Pageable pageable) {
        return userRepo.findAll(Example.of(User.builder()
                .deleted(DeleteFlag.UN_DELETED)
                .build()), pageable).map(UserResponse::from);
    }

    @Override
    public List<UserResponse> search(String username) {
        List<User> userResponses = userRepo.findAll((Specification) (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("username"), "%" + username + "%");
        });
        return userResponses.stream().map(UserResponse::from).collect(Collectors.toList());
    }

    @Override
    public UserResponse fetch(Long id) {

        return UserResponse.from(userRepo.findById(id).orElse(null));
    }
}
