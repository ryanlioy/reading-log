package dev.ryanlioy.booklogger.service;

import dev.ryanlioy.booklogger.entity.RoleEntity;
import dev.ryanlioy.booklogger.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleEntity getRoleByName(String name) {
        Optional<RoleEntity> roleEntity = roleRepository.findByName(name);
        return roleEntity.orElse(null);
    }
}
