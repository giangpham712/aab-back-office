package com.aabplastic.backoffice.controllers.rest;

import com.aabplastic.backoffice.models.dto.UserDto;
import com.aabplastic.backoffice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UsersRestController {

    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid UserDto userEntry) {
        UserDto created = userService.create(userEntry);

        return created;
    }

}
