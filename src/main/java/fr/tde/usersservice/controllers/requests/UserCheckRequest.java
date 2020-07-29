package fr.tde.usersservice.controllers.requests;

import lombok.Data;

@Data
public class UserCheckRequest {

    private String username;
    private String password;

}
