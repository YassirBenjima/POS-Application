package com.yassirflow.service.impl;

import com.yassirflow.config.JwtProvider;
import com.yassirflow.model.User;
import com.yassirflow.model.UserRole;
import com.yassirflow.payload.dto.UserDto;
import com.yassirflow.payload.response.AuthResponse;
import com.yassirflow.repository.UserRepository;
import com.yassirflow.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImpl customUserImpl;
    private final com.yassirflow.mapper.UserMapper userMapper;

    @Override
    public AuthResponse signup(UserDto userDto) {
        User isEmailExist = userRepository.findByEmail(userDto.getEmail());
        if (isEmailExist != null) {
            throw new RuntimeException("An account with this email address already exists.");
        }

        if (userDto.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new RuntimeException("Administrative accounts cannot be created via public registration.");
        }

        User createdUser = new User();
        createdUser.setEmail(userDto.getEmail());
        createdUser.setFullName(userDto.getFullName());
        createdUser.setPhone(userDto.getPhone());
        createdUser.setRole(userDto.getRole());
        createdUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        createdUser.setCreatedAt(LocalDateTime.now());
        createdUser.setLastLogin(LocalDateTime.now());
        createdUser.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signup Success");
        authResponse.setUser(userMapper.toUserDto(savedUser));

        return authResponse;
    }

    @Override
    public AuthResponse signin(UserDto userDto) {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        Authentication authentication = authenticate(email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String role = authorities.iterator().next().getAuthority();

        String token = jwtProvider.generateToken(authentication);

        User user = userRepository.findByEmail(email);

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signin Success");
        authResponse.setUser(userMapper.toUserDto(user));

        return authResponse;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserImpl.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Authentication failed: Invalid email or password.");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Authentication failed: Invalid email or password.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
