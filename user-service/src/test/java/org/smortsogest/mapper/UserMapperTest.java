package org.smortsogest.mapper;

import org.junit.jupiter.api.Test;
import org.smortsogest.dto.UserDTO;
import org.smortsogest.model.User;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    @Test
    void shouldMapUserToUserDTO(){
        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setName("John Doe");

        UserDTO dto = UserMapper.toUserDTO(user);

        assertNotNull(dto);
        assertEquals(user.getId(),dto.getId());
        assertEquals(user.getEmail(),dto.getEmail());
        assertEquals(user.getName(),dto.getName());
    }

    @Test
    void shouldHandleEmptyUserObject(){
        User user = new User();

        UserDTO dto = UserMapper.toUserDTO(user);

        assertNotNull(dto);
        assertNull(dto.getId());
        assertNull(dto.getEmail());
        assertNull(dto.getName());
    }
}
