package com.hieushsoft.ecommerce.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

// Gọi class này mỗi lần yêu cầu HTTP request
public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Lấy JWT(JSON Web Token) từ header
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        // Bearer token
        if (jwt != null) {
            // Nếu có jwt lấy phần token
            jwt = jwt.substring(7);

            try {
                // Tạo khóa key
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

                // Lấy phần claims(dữ liệu) từ header với key và jwt
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                // Lấy thuộc tính email từ Clams trả về
                String email = String.valueOf(claims.get("email"));

                // Lấy role người dùng từ Clams
                String authorities = String.valueOf(claims.get("authorities")); // Example: ROLE_CUSTOMER, ROLE_ADMIN

                // Tạo List đối tượng tội  GrantedAuthority(đại diện quyền hạn mỗi người, mỗi phần từ được cắt từ dấu ","
                List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                // Tạo đối tượng thông tin cho người dùng với email với dánh sách quyền mới được tạo
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorityList);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                throw new BadCredentialsException("invalid token...");
            }
        }

        filterChain.doFilter(request , response);
    }

}
