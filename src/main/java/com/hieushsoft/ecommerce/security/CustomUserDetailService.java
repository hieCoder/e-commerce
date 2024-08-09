package com.hieushsoft.ecommerce.security;

import com.hieushsoft.ecommerce.entity.User;
import com.hieushsoft.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Tìm người dùng trong cơ sở dữ liệu bằng username
        User user = userRepository.findByEmail(username);
        if (user != null) {
            throw  new UsernameNotFoundException("user not found with email " + username);
        }



        return null;
    }
}
