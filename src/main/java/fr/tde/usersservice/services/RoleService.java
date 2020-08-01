package fr.tde.usersservice.services;

import fr.tde.usersservice.models.Role;
import fr.tde.usersservice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getRoleByName(String name) {
        Optional<Role> optionalRole = roleRepository.findByName(name);

        return optionalRole.orElse(null);
    }

}
