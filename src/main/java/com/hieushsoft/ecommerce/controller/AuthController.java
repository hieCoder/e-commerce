package com.hieushsoft.ecommerce.controller;

import com.hieushsoft.ecommerce.config.JwtProvide;
import com.hieushsoft.ecommerce.model.User;
import com.hieushsoft.ecommerce.repository.CartRepository;
import com.hieushsoft.ecommerce.repository.UserRepository;
import com.hieushsoft.ecommerce.response.AuthResponse;
import com.hieushsoft.ecommerce.service.CustomerUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvide jwtProvide;

    @Autowired
    private CustomerUserDetailService userDetailService;


    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {
        User isEmailExist = userRepository.findByEmail(user.getEmail());

        return null;
    }







}
