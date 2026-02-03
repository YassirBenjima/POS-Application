package com.yassirflow.service;

import com.yassirflow.payload.dto.UserDto;
import com.yassirflow.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse register(UserDto userDto);
    AuthResponse login(UserDto userDto);
}
