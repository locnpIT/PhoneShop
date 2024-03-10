package com.myphoneshop.myphoneshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.myphoneshop.myphoneshop.domain.User;
import com.myphoneshop.myphoneshop.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String handleHello() {
        return "Hello from service";
    }

    public List<User> getAllUser() {

        return this.userRepository.findAll();

    }

    public List<User> getAllUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    public User handleSubmit(User user) {
        User phuocloc = userRepository.save(user);
        System.out.println(phuocloc);
        return phuocloc;
    }

    public void deleteAUser(long id) {
        this.userRepository.deleteById(id);
    }

}
