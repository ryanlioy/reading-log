package dev.ryanlioy.bookloger.controller;

import dev.ryanlioy.bookloger.dto.UserDto;
import dev.ryanlioy.bookloger.dto.meta.EnvelopeDto;
import dev.ryanlioy.bookloger.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create a user
     * @param userDto the request
     * @return the created user
     */
    @PostMapping("/add")
    public ResponseEntity<EnvelopeDto<UserDto>> addUser(@RequestBody UserDto userDto) {
        UserDto dto = userService.addUser(userDto);

        return new ResponseEntity<>(new EnvelopeDto<>(dto), HttpStatus.CREATED);
    }

    /**
     * Get a user by ID
     * @param id the ID of the user
     * @return body with the found user, null if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<EnvelopeDto<UserDto>> getUser(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        HttpStatus status = HttpStatus.OK;
        if (user == null) {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(new EnvelopeDto<>(user),  status);
    }

    /**
     * Delete a user by ID
     * @param id the ID to delete
     * @return 204 with no body
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<EnvelopeDto<UserDto>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
