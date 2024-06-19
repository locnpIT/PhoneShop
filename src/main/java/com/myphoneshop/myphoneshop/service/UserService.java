package com.myphoneshop.myphoneshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myphoneshop.myphoneshop.domain.Role;
import com.myphoneshop.myphoneshop.domain.User;
import com.myphoneshop.myphoneshop.domain.dto.registerDTO;
import com.myphoneshop.myphoneshop.repository.OrderRepository;
import com.myphoneshop.myphoneshop.repository.ProductRepository;
import com.myphoneshop.myphoneshop.repository.RoleRepository;
import com.myphoneshop.myphoneshop.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public String handleHello() {
        return "Hello from service";
    }

    public Page<User> getAllUser(Pageable pageable) {
        return this.userRepository.findAll(pageable);
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

    public long countUser() {
        return this.userRepository.count();
    }

    public long countProduct() {
        return this.productRepository.count();
    }

    public long countOrder() {
        return this.orderRepository.count();
    }

}
