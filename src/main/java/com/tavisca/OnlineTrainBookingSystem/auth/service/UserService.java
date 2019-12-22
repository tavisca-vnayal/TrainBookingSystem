package com.tavisca.OnlineTrainBookingSystem.auth.service;

import com.tavisca.OnlineTrainBookingSystem.auth.dao.UserRepository;
import com.tavisca.OnlineTrainBookingSystem.auth.model.LoginForm;
import com.tavisca.OnlineTrainBookingSystem.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String checkRole(User user) {
        return user.getRole();
    }

    public boolean validateUser(User user, String inputpswd) {
        return user.getPassword().equals(inputpswd) ? true : false;
    }

    public User checkValidityOfUser(LoginForm loginForm) {
        Optional<User> userObject = Optional.ofNullable(userRepository.findByUsername(loginForm.getUsername()));
        if (userObject.isPresent()) {
            if (validateUser(userObject.get(), loginForm.getPassword())) {
                return userObject.get();
            }
            else {
                return null;
            }
        }
        return null;
    }
}