package fr.tde.usersservice.controllers;

import fr.tde.usersservice.controllers.requests.UserCheckRequest;
import fr.tde.usersservice.controllers.requests.UserRequest;
import fr.tde.usersservice.controllers.responses.BooleanResponse;
import fr.tde.usersservice.controllers.responses.UserResponse;
import fr.tde.usersservice.mappers.UserMapper;
import fr.tde.usersservice.models.User;
import fr.tde.usersservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/create")
    public UserResponse create(@RequestBody UserRequest userRequest) {
        User user = userMapper.convertToEntity(userRequest);
        User userCreated = null;
        try {
            userCreated = userService.upsertUser(user);
            return userMapper.convertToDto(userCreated);
        } catch (CloneNotSupportedException e) {
            log.error(e.getMessage(), e);
            //TODO: Ajouter gestion de l'erreur avec code erreur 500
            return null;
        }
    }

    @GetMapping("/get")
    public UserResponse getById(@RequestParam("id") Long id){
        UserResponse response = new UserResponse();
        try {
            response = userMapper.convertToDto(userService.getUserById(id));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return response;
    }

    @GetMapping("/{username}/{password}")
    public UserResponse getByUsernameAndPassword(@PathVariable("username") String username, @PathVariable("password") String password) {
        UserResponse response = new UserResponse();
        try {
            response = userMapper.convertToDto(userService.getUserByUsernameAndPassword(username, password));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return response;
    }


    @PostMapping("/check")
    public BooleanResponse checkUserByUsernameAndPassword(@RequestBody UserCheckRequest userCheckRequest) throws Exception {
        BooleanResponse response = new BooleanResponse();
        User user = userService.getUserByUsernameAndPassword(userCheckRequest.getUsername(), userCheckRequest.getPassword());
        if(user != null && user.getId() != null && user.getId() != 0) {
            response.setResponse(Boolean.TRUE);
        }
        else {
            response.setResponse(Boolean.FALSE);
        }
        return response;
    }

    @PostMapping("/username")
    public UserResponse getUserByUsername(@RequestBody UserCheckRequest userCheckRequest) throws Exception {
        User user = userService.getUserByUsername(userCheckRequest.getUsername());
        return userMapper.convertToDto(user);
    }
}
