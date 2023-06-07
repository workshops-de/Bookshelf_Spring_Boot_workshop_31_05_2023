package de.workshops.bookshelf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
class SecurityConfiguration {

    private final JdbcTemplate jdbc;

    SecurityConfiguration(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(
                                        "/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/webjars/swagger-ui/**"
                                ).permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .logout(logout -> logout.logoutUrl("/logout"))
                .build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> jdbc.queryForObject("SELECT * FROM bookshelf_user WHERE username = ?", (rs, rowNum) -> new User(
                rs.getString("username"),
                rs.getString("password"),
                List.of(new SimpleGrantedAuthority(rs.getString("role")))
        ), username);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
