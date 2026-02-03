package com.yassirflow.payload.response;

import com.yassirflow.payload.dto.UserDto;
import lombok.Data;

@Data
public class AuthResponse
{
    private String jwt;
    private String message;
    private UserDto user;
}
