package com.hieushsoft.ecommerce.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvide {
    private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    private String generateToken(Authentication authentication) {
        // Lấy các quyền của người dùng từ đối tượng Authentication
        Collection<? extends GrantedAuthority> authority = authentication.getAuthorities();
        // Chuyển đổi danh sách quyền thành chuỗi ký tự phân tách bởi dấu phẩy
        String roles = getRoleFormJwtToken(authority);

        // Tạo JWT với thông tin bao gồm thời gian phát hành, thời gian hết hạn, email và các quyền
        String jwt = Jwts.builder()
                .setIssuedAt(new Date()) // Set thời gian phát hành bằng thời gian hiện tại
                .setExpiration((new Date(new Date().getTime() + 86400000))) // Đặt thời gian hết hạn là 1 ngày sau thời gian hiện tại
                .claim("email", authentication.getName()) // Thêm email vào payload của JWT
                .claim("authorities", roles) // Thêm danh sách quyền vào payload của JWT
                .signWith(key) // Ký mã thông báo bằng khóa bí mật
                .compact(); // Hoàn tất và nén mã thông báo

        return jwt; // Trả về mã thông báo đã tạo
    }

    // Get mail từ jwt
    public String getEmailFormJwtToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return claims.get("email", String.class);
    }

    // Cắt String role thành một List tập hợp role
    private String getRoleFormJwtToken(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authors = new HashSet<>();

        for (GrantedAuthority auths:authorities) {
            authors.add(auths.getAuthority());
        }

        return String.join(",", authors);
    }

}
