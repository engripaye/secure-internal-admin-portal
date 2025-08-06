package org.engripaye.secureinternaladminportal.service;

import org.engripaye.secureinternaladminportal.model.Users;
import org.engripaye.secureinternaladminportal.repository.RoleRepository;
import org.engripaye.secureinternaladminportal.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Users createOrUpdateFromOidc(String email, String name, String picture){
        return userRepository.findByEmail(email).map(u -> {
            u.setName(name);
            u.setPicture(picture);
            return userRepository.save(u);
        }).orElseGet(() -> {
            Users u = new Users();
            u.setEmail(email);
            u.setName(name);
            u.setPicture(picture);
            // assign default role
            roleRepository.findByName("ROLE_VIEWER").ifPresent(r -> u.getRoles().add(r));
            return userRepository.save(u);
        });
    }
}
