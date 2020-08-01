package fr.tde.usersservice.services;

import fr.tde.usersservice.models.Role;
import fr.tde.usersservice.models.User;
import fr.tde.usersservice.repositories.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.dsig.DigestMethod;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private static final String SALT = "+TDE-";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    public User upsertUser(User user) throws CloneNotSupportedException {
        log.debug("upsert user in databases");
        if(this.getUserByUsername(user.getUsername()) != null) { //TODO: Ajouter la vérification sur l'adresse mail
            throw new CloneNotSupportedException("user already exist with username : " + user.getUsername());
        }
        else {

            user.setPassword(SALT + DigestUtils.sha256Hex(user.getPassword()));

            Role role = roleService.getRoleByName(user.getRole().getName());
            user.setRole(role);
            return userRepository.save(user);
        }
    }

    public User getUserById(Long id) throws NotFoundException {
        log.debug("Find the user with id : " + id);
        Optional<User> optionalUser = userRepository.findById(id);
        if( optionalUser.isPresent() )
            return optionalUser.get();
        else
            throw new NotFoundException("User not found with id :" + id);
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        log.debug("find the user with username : " + username);
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);
        return optionalUser.orElse(null);
    }

    public User getUserByUsername(String username) {
        log.debug("find the user with username : " + username);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElse(null);
    }

    public Boolean checkUser(String username, String password) {

        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, SALT + DigestUtils.sha256Hex(password));

        return optionalUser.isPresent();
    }
}
