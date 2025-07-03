package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.dto.meta.EnvelopeDto;
import dev.ryanlioy.bookloger.dto.UserDto;
import dev.ryanlioy.bookloger.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<EnvelopeDto<UserDto>> addUser(@RequestBody UserDto userDto) {
        UserDto resource = userService.addUser(userDto);

        return new ResponseEntity<>(new EnvelopeDto<>(resource), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvelopeDto<UserDto>> getUser(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        HttpStatus status = HttpStatus.OK;
        if (user == null) {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(new EnvelopeDto<>(user),  status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EnvelopeDto<UserDto>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
