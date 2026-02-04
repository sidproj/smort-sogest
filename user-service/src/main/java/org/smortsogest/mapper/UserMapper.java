package org.smortsogest.mapper;

import org.smortsogest.dto.UserDTO;
import org.smortsogest.model.User;

public class UserMapper {

    public static UserDTO toUserDTO (User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setId(user.getId());
        return userDTO;
    }
}
