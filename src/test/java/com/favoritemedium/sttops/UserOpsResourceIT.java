package com.favoritemedium.sttops;

import com.favoritemedium.sttops.model.dto.UserDTO;
import com.favoritemedium.sttops.resource.UserOpsResource;
import com.favoritemedium.sttops.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

@WebMvcTest(UserOpsResource.class)
public class UserOpsResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void test_get_all_users() throws Exception{

        Mockito.when(userService.findAllUsers()).thenReturn(Arrays.asList(UserDTO.builder()
                .username("a1").id(1).password("qweqwese").fullName("abc bcc").email("q@q.com").active(true)
                .build()));

        mockMvc.perform(get("/api/v1/users")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().json("[{\"id\":1,\"fullName\":\"abc bcc\",\"username\":\"a1\",\"password\":\"qweqwese\",\"email\":\"q@q.com\",\"active\":true}]"));
    }

}
