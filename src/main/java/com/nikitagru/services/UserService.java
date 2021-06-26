package com.nikitagru.services;

import com.nikitagru.entities.User;
import com.nikitagru.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * Сервис пользователя
 */
@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }
}
