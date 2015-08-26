package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.User;
import com.aabplastic.backoffice.models.dto.UserDto;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByUsername(String username);

    Collection<User> getAllUsers();

    UserDto create(UserDto userDto);
}
