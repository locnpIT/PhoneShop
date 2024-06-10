package com.myphoneshop.myphoneshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myphoneshop.myphoneshop.domain.Role;
import com.myphoneshop.myphoneshop.domain.User;
import com.myphoneshop.myphoneshop.domain.dto.registerDTO;
import com.myphoneshop.myphoneshop.repository.OrderDetailRepository;
import com.myphoneshop.myphoneshop.repository.OrderRepository;
import com.myphoneshop.myphoneshop.repository.RoleRepository;
import com.myphoneshop.myphoneshop.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String handleHello() {
        return "Hello from service";
    }

    public List<User> getAllUser() {

        return this.userRepository.findAll();

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

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public User registerDTOtoUser(registerDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);

    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

}
