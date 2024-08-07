package com.hieushsoft.ecommerce.response;

import com.hieushsoft.ecommerce.enums.user.RoleEnum;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String jwt;
    private String message;
    private RoleEnum roleEnum;

}
