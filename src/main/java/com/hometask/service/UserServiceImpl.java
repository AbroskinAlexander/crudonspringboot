package com.hometask.service;

import com.hometask.dao.UserDAO;
import com.hometask.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public boolean addUser(User user) {
        if (userDAO.existUser(user)) {
            userDAO.addUser(user);
            return true;
        }
        return false;
    }

    @Override
    public void deleteUser(User userold) {
        userDAO.deleteUser(userold);
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public void updateUser(User updateUserold) {
        userDAO.updateUser(updateUserold);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }


}
