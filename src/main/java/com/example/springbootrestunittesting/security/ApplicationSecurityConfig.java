package com.example.springbootrestunittesting.security;

import com.example.springbootrestunittesting.jwt.JwtConfig;
import com.example.springbootrestunittesting.jwt.JwtTokenVerifier;
import com.example.springbootrestunittesting.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                       .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig))
                .addFilterAfter(new JwtTokenVerifier(jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/","index","/css/**","/js/**").permitAll()
                .anyRequest()
                .authenticated();
    }

    @Override
    protected UserDetailsService userDetailsService() {

        UserDetails adminEyraf = User.builder()
                .username("eyraf")
                .password(passwordEncoder.encode("12345"))
                .authorities(ApplicationUserRole.ADMIN.getAuthorities())
                .build();

        UserDetails userKenan = User.builder()
                .username("kenan")
                .password(passwordEncoder.encode("12345"))
                .authorities(ApplicationUserRole.USER.getAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                adminEyraf,
                userKenan
        );
    }
}
