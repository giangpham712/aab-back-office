package com.aabplastic.backoffice.services;

import com.aabplastic.backoffice.models.User;
import com.aabplastic.backoffice.models.dto.UserDto;
import com.aabplastic.backoffice.repositories.UserRepository;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserById(long id) {
        return null;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Collection<User> getAllUsers() {
        return null;
    }

    @Override
    public UserDto create(UserDto userDto) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        User user = mapperFactory.getMapperFacade().map(userDto, User.class);
        user.setPasswordHash(new BCryptPasswordEncoder().encode(userDto.getPassword()));

        user = userRepository.save(user);
        return mapperFactory.getMapperFacade().map(user, UserDto.class);
    }
}
