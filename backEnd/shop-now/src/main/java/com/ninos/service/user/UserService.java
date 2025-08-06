package com.ninos.service.user;

import com.ninos.dto.UserDTO;
import com.ninos.model.User;
import com.ninos.request.CreateUserRequest;
import com.ninos.request.UpdateUserRequest;

public interface UserService {

    UserDTO createUser(CreateUserRequest request);
    UserDTO updateUser(UpdateUserRequest request, Long userId);
    UserDTO getUserById(Long userId);
    void deleteUser(Long userId);

}
