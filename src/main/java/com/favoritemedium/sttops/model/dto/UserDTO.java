package com.favoritemedium.sttops.model.dto;

import lombok.*;

import java.io.Serializable;

/**
 * Data transfer object to send on the wire as response
 */
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
