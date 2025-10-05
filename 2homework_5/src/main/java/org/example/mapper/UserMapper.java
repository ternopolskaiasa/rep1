package org.example.mapper;

import org.example.entity.User;
import org.example.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDto(User user) {
        if (user == null) return null;
        return new UserDTO(user.getName(), user.getEmail(), user.getAge());
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) return null;
        return new User(dto.getName(), dto.getEmail(), dto.getAge());
    }

    public void updateEntityFromDto(UserDTO dto, User user) {
        if (dto == null || user == null) return;
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
    }
}
