package fr.tde.usersservice.controllers.requests;

import fr.tde.usersservice.models.Role;
import lombok.Data;

@Data
public class UserRequest {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;

}
