package com.hieushsoft.ecommerce.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       /*
        Thiết lập SessionCreationPolicy tở trạng thái STATELESS(không tạo session để lưu trạng thái người dùng trong khi sử dụng các xác thực người dùng
        bằng JWT) */
        httpSecurity.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize -> Authorize
                        .requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER", "ADMIN")
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigrationSource()));

        return null;
    }

//    Config Cors chỉ cho các đường dẫn sau truy cập vào tài nguyên api
    private CorsConfigurationSource corsConfigrationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();

                // Danh sách đường dẫn được truy cập vào tài nguyên api
                cfg.setAllowedOrigins(Arrays.asList(
                        "http://localhost:3000"
                ));

                // Cho phép tất cả method từ danh sách đường dẫn trên được truy cập vào api
                cfg.setAllowedMethods(Collections.singletonList("*"));

                // cho phép được gửi các thông tin xác thực như cookie, headers vào api
                cfg.setAllowCredentials(true);
                // kết hợp với AllowCredentials để chỉ định tất cả các yêu cầu xác thực header được chấp nhận như Basic Authentication(Content-Type, Authorization,...)
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                // Cho phép các chỉ định  của header mà client có thể thấy mà trình duyệt cho phép
                cfg.setExposedHeaders(Arrays.asList("Authorization"));
                 /*
                     Set max age trình duyện cache lại cơ chế preflight(gửi 1 yêu cầu trước khi gửi yêu cầu chính cho server
                     nhằm xác nhận các phương thức và header có được server chấp nhận không giúp giảm tải cho server)

                 */
                cfg.setMaxAge(3600L); // set thời gian cache là 1h
                return cfg;
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
