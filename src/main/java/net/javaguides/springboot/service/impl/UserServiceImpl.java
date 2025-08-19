package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.EmailAlreadyExistException;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.mapper.AutoUserMapper;

import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {

     //   User user = UserMapper.mapToUser(userDto);
      //  User user = modelMapper.map(userDto, User.class);

        Optional<User> optionalUser= userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()){
            throw new EmailAlreadyExistException("email already exist for user");
        }

        User user = AutoUserMapper.MAPPER.mapToUser(userDto);


        User saved = userRepository.save(user);

       // UserDto userDto1=UserMapper.mapToUserDto(user);
      //  UserDto userDto1= modelMapper.map(user, UserDto.class);
        UserDto userDto1=AutoUserMapper.MAPPER.mapToUserDto(user);

            return userDto1;
    }


    @Override
    public UserDto getUserById(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User","id",userId)
        );

        //return UserMapper.mapToUserDto(user);
        //return modelMapper.map(user, UserDto.class);
        return AutoUserMapper.MAPPER.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users= userRepository.findAll();

       /* return users.stream().map(UserMapper::mapToUserDto)
                .collect(Collectors.toUnmodifiableList());
*//*
        return users.stream().map((user) -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toUnmodifiableList());
*/
        return users.stream().map((user) ->AutoUserMapper.MAPPER.mapToUserDto(user))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public UserDto updateUser(UserDto user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(
                ()-> new ResourceNotFoundException("User", "id", user.getId())
        );

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        User save = userRepository.save(existingUser);
        //return UserMapper.mapToUserDto(save);
     //  return modelMapper.map(save, UserDto.class);
        return AutoUserMapper.MAPPER.mapToUserDto(save);
    }

    @Override
    public void deleteUser(Long userId) {

        User existingUser = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User", "id", userId)
        );

        userRepository.deleteById(userId);

    }


}
