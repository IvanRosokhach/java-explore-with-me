package ru.practicum.user.service;

import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

import java.util.List;

public interface UserService {

    UserDto saveUser(UserDto userDto);

    List<UserDto> getUsers(List<Long> usersId, int from, int size);

    void deleteUser(long userId);

    User findById(long userId);

}
