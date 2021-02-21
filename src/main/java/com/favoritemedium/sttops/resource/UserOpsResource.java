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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserOpsResource {

    @Autowired
    private final UserService userService;

    @PostMapping(path = "/user1", consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
        userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/users", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers(){
        List<UserDTO> dtos = userService.findAllUsers();
        return ResponseEntity.ok(dtos);
    }
}
