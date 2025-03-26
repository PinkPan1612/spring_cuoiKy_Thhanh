package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.service.UserService;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public UserDetailsService userDetailsService(com.example.demo.service.UserService userService) {
                return new CustomUserDetailsService(userService); // custom at service
        }

        // Xác thực người dùng khi đăng nhập
        @Bean
        public DaoAuthenticationProvider authProvider(
                        PasswordEncoder passwordEncoder,
                        UserDetailsService userDetailsService) {

                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
                authProvider.setUserDetailsService(userDetailsService);
                authProvider.setPasswordEncoder(passwordEncoder);
                // authProvider.setHideUserNotFoundExceptions(false);
                return authProvider;
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http, UserService userService)
                        throws Exception {
                // ver6. lamda
                http
                                .authorizeHttpRequests(authorize -> authorize
                                                .dispatcherTypeMatchers(DispatcherType.FORWARD,
                                                                DispatcherType.INCLUDE)
                                                .permitAll()
                                                .requestMatchers("/", "/login", "/cart", "/about", "menu")
                                                .permitAll()
                                                .requestMatchers("/client/img/**", "/client/css/**", "/client/js/**")
                                                .permitAll()
                                                .requestMatchers("/admin/**").hasRole("ADMIN")

                                                .anyRequest().authenticated())

                                .formLogin(formLogin -> formLogin
                                                .loginPage("/login").loginProcessingUrl("/authenticateTheUser")
                                                .failureUrl("/login?error")
                                                .defaultSuccessUrl("/", true) // Chuyển hướng sau khi đăng nhập thành
                                                                              // công
                                                .permitAll())

                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login?logout")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID").permitAll())

                                .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny"));

                return http.build();
        }

}
