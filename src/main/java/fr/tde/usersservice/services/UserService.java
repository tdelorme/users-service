package fr.tde.usersservice.services;

import fr.tde.usersservice.models.User;
import fr.tde.usersservice.repositories.UserRepository;
import javassist.NotFoundException;
import jdk.jshell.spi.ExecutionControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User upsertUser(User user) throws CloneNotSupportedException {
        log.debug("upsert user in databases");
        if(this.getUserByUsername(user.getUsername()) != null) { //TODO: Ajouter la vérification sur l'adresse mail
            throw new CloneNotSupportedException("user already exist with username : " + user.getUsername());
        }
        else {
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
}
