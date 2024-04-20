package com.myphoneshop.myphoneshop.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.myphoneshop.myphoneshop.domain.User User = this.userService.getUserByEmail(username);
        if (User == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // ở đây đặt kiểu UserDetails mà return về kiểu user trong springsecurity bởi vì
        // user là con của Userdetails, vì tính đa hình nên nó tự ép kiểu
        return new org.springframework.security.core.userdetails.User(
                User.getEmail(),
                User.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + User.getRole().getName())));
    }

}
