package ru.practicum.user.service;

import ru.practicum.dto.UserDto;

import java.util.List;

public interface UserAdminService {

    UserDto saveUser(UserDto userDto);

    List<UserDto> getUsers(List<Long> usersId, int from, int size);

    void deleteUser(Long userId);

}
