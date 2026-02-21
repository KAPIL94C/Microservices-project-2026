package com.accenture.userservice.config;

import com.accenture.userservice.entity.Role;
import com.accenture.userservice.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleDataInitializer {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        createIfNotExists("ROLE_USER");
        createIfNotExists("ROLE_ADMIN");
        createIfNotExists("ROLE_GUEST");
    }

    private void createIfNotExists(String name) {
        if (roleRepository.findByName(name).isEmpty()) {
            roleRepository.save(new Role(name));
        }
    }
}
