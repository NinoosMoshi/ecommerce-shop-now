package com.ninos.service.user;

import com.ninos.dto.UserDTO;
import com.ninos.model.User;
import com.ninos.repository.UserRepository;
import com.ninos.request.CreateUserRequest;
import com.ninos.request.UpdateUserRequest;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


//    @Override
//    public UserDTO createUser(CreateUserRequest request) {
//        return Optional.of(request)
//                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
//                .map(req -> {
//                    User user = new User();
//                    user.setFirstName(request.getFirstName());
//                    user.setLastName(request.getLastName());
//                    user.setEmail(request.getEmail());
//                    user.setPassword(request.getPassword());
//                    return userRepository.save(user);
//                }).orElseThrow(() -> new EntityNotFoundException("Oops! " + request.getEmail() + " already exists!"));
//    }
    @Override
    public UserDTO createUser(CreateUserRequest request) {
        boolean existUser = userRepository.existsByEmail(request.getEmail());
        if(existUser){
            throw new EntityExistsException("User with email " + request.getEmail() + " already exists");
        }
        User user = modelMapper.map(request, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }


    @Override
    public UserDTO updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            User updatedUser = userRepository.save(existingUser);
            return modelMapper.map(updatedUser, UserDTO.class);
        }).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
//    @Override
//    public UserDTO updateUser(UpdateUserRequest request, Long userId) {
//        User existUser = userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
//        existUser.setFirstName(request.getFirstName());
//        existUser.setLastName(request.getLastName());
//        User savedUser = userRepository.save(existUser);
//        return modelMapper.map(savedUser, UserDTO.class);
//    }



//    @Override
//    public User getUserById(Long userId) {
//        return userRepository.findById(userId)
//                .orElseThrow(() -> new EntityNotFoundException("User not found"));
//    }
    @Override
    public UserDTO getUserById(Long userId) {
       User user = userRepository.findById(userId)
               .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
       return modelMapper.map(user, UserDTO.class);
    }

//    @Override
//    public void deleteUser(Long userId) {
//       userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () -> {
//           throw new EntityNotFoundException("User not found");
//       });
//    }
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        userRepository.delete(user);
    }
}
