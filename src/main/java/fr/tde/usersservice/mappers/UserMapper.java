package fr.tde.usersservice.mappers;

import fr.tde.usersservice.controllers.requests.UserRequest;
import fr.tde.usersservice.controllers.responses.UserResponse;
import fr.tde.usersservice.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UserResponse convertToDto(User userCreated) {
        UserResponse response = modelMapper.map(userCreated, UserResponse.class);
        return response;
    }

    public User convertToEntity(UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        return user;
    }
}
