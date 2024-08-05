package com.hieushsoft.ecommerce.service;

import com.hieushsoft.ecommerce.model.User;
import com.hieushsoft.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            throw  new UsernameNotFoundException("user not found with email " + username);
        }

//        USER_ROLE role = user.getUserRole();


        return null;
    }
}
