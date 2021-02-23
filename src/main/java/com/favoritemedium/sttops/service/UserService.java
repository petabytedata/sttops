package com.favoritemedium.sttops.service;

import com.favoritemedium.sttops.model.dto.UserDTO;
import com.favoritemedium.sttops.model.entity.User;
import com.favoritemedium.sttops.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * This service contain the user management related methods
 */
@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    /**
     * This method create/register new user in our application.
     * @param userDTO
     * @return
     */
    public User createUser(UserDTO userDTO){
        User user = User.builder().active(userDTO.isActive()).email(userDTO.getEmail())
                .fullName(userDTO.getFullName()).password(userDTO.getPassword()).username(userDTO.getUsername())
                .build();
        return userRepository.save(user);
    }

    /**
     * This method returns all the registred users in our application.
     *
     * @return
     */
    public List<UserDTO> findAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(entity ->
            UserDTO.builder().active(entity.isActive()).email(entity.getEmail()).fullName(entity.getFullName())
                    .password(entity.getPassword()).id(entity.getId()).username(entity.getUsername()).build()
        ).collect(toList());
    }
}