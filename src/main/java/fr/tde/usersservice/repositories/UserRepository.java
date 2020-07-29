package fr.tde.usersservice.repositories;

import fr.tde.usersservice.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);

}
