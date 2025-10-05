package org.example.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.example.entity.UserEvent;
import org.springframework.transaction.annotation.Transactional;
import org.example.entity.User;
import org.example.entity.UserRepository;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponseDto createUser(UserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new EntityExistsException(requestDto.getEmail());
        }

        User user = modelMapper.map(requestDto, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        return userRepository.findById(id)
                .map(user -> {
                    modelMapper.map(requestDto, user);
                    User updatedUser = userRepository.save(user);
                    return modelMapper.map(updatedUser, UserResponseDto.class);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
