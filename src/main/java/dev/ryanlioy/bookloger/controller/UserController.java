package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.entity.UserEntity;
import dev.ryanlioy.bookloger.mapper.UserMapper;
import dev.ryanlioy.bookloger.resource.UserResource;
import dev.ryanlioy.bookloger.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<UserResource> addUser(@RequestBody UserResource userResource) {
        UserResource resource = userService.addUser(userResource);

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> getUser(@PathVariable Long id) {
        Optional<UserEntity> optional = userService.getUserById(id);
        UserResource userResource = null;
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (optional.isPresent()) {
            userResource = userMapper.entityToResource(optional.get());
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(userResource,  status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResource> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
