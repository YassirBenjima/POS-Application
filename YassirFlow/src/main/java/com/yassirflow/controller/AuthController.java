package com.yassirflow.controller;

import com.yassirflow.exception.UserException;
import com.yassirflow.payload.dto.UserDto;
import com.yassirflow.payload.response.AuthResponse;
import com.yassirflow.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    // auth/signup

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signupHandler(
            @RequestBody UserDto userDto
    ) throws UserException {
        return ResponseEntity.ok(
                authService.signup(userDto)
        );
    }

    // auth/signin

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signinHandler(
            @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(
                authService.signin(userDto)
        );
    }

    
}
