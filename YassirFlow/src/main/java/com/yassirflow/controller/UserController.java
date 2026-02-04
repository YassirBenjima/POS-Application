package com.yassirflow.controller;

import com.yassirflow.exception.UserException;
import com.yassirflow.mapper.UserMapper;
import com.yassirflow.model.User;
import com.yassirflow.payload.dto.UserDto;
import com.yassirflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
        User user = userService.getUserFromJwtToken(jwt);
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws UserException {
        User user = userService.getUserById(id);
        if (user==null) {
            throw new UserException("User with the specified identifier does not exist.");
        }
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }
}
