package com.yassirflow.payload.dto;

import com.yassirflow.model.UserRole;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    
    private String fullName;
    
    private String email;
    
    private String phone;
    
    private UserRole role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
}
