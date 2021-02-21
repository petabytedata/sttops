package com.favoritemedium.sttops;

import com.favoritemedium.sttops.model.dto.UserDTO;
import com.favoritemedium.sttops.model.entity.User;
import com.favoritemedium.sttops.model.repository.UserRepository;
import com.favoritemedium.sttops.service.UserService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;



public class UserServiceTest extends BaseTest{

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void test_create_user(){

        when(userRepository.save(any())).thenReturn(User.builder().active(true).id(1).build());

        User user = userService.createUser(UserDTO.builder()
                .active(true).email("a@a.com").fullName("Full Name").username("username").build());

        assertThat(user.getId()).isEqualTo(1);
    }
}
