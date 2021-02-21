package com.favoritemedium.sttops.model.dto;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder
@ToString
public class UserDTO implements Serializable {

    private int id;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private boolean active;
}
