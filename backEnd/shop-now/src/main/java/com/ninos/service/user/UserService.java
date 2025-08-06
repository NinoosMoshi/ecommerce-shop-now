package com.ninos.service.user;

import com.ninos.model.User;
import com.ninos.request.CreateUserRequest;
import com.ninos.request.UpdateUserRequest;

public interface UserService {

    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    User getUserById(Long userId);
    void deleteUser(Long userId);

}
