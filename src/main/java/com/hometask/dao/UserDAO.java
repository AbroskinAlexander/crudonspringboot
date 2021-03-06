package com.hometask.dao;

import com.hometask.model.User;

import java.util.List;

public interface UserDAO {

    List<User> getAllUsers();

    void addUser(User user);

    void deleteUser(User user);

    User getUserById(Long id);

    void updateUser(User updateUser);

    boolean existUser(User user);

   User getUserByEmail(String email);
}
