package com.favoritemedium.sttops.resource;

import com.favoritemedium.sttops.model.dto.UserDTO;
import com.favoritemedium.sttops.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * Web resource contains the endpoints for managing users in our application.
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserOpsResource {

    @Autowired
    private final UserService userService;

    /**
     * This API register new user in our system, which could later upload and use our Speech-to-Text endpoints
     * @param userDTO
     * @return
     */
    @PostMapping(path = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
        userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * This API returns all the registered users
     *
     * @return
     */
    @GetMapping(path = "/users", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers(){
        List<UserDTO> dtos = userService.findAllUsers();
        return ResponseEntity.ok(dtos);
    }
}
