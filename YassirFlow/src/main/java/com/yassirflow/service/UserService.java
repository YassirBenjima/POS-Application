package com.yassirflow.service;

import com.yassirflow.exception.UserException;
import com.yassirflow.model.User;
import java.util.List;

public interface UserService {

    User getUserFromJwtToken(String token) throws UserException;

    User getCurrentUser() throws UserException;

    User getUserByEmail(String email);

    User getUserById(Long id);

    List<User> getAllUsers();

}
