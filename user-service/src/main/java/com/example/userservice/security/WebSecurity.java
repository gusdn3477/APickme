package com.example.userservice.security;

import com.example.userservice.service.CustomOAuth2UserService;
import com.example.userservice.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Environment env;
    private CustomOAuth2UserService customOAuth2UserService;


    public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder,
                       CustomOAuth2UserService customOAuth2UserService) {
        this.env = env;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customOAuth2UserService = customOAuth2UserService;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/users/**").permitAll();
        http.authorizeRequests().antMatchers("/health_check/**").permitAll();
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
        http.authorizeRequests().antMatchers("/swagger-ui/**").permitAll();
        http.authorizeRequests().antMatchers("/v2/**").permitAll();
        http.authorizeRequests().antMatchers("/logout/**").permitAll();
//        http.authorizeRequests().antMatchers("/actuator/**").permitAll();
        http.authorizeRequests()
                .antMatchers("/**")
//                .hasIpAddress(env.getProperty("gateway.ip"))
                .access("hasIpAddress('172.30.144.1') or hasIpAddress('172.18.0.5') or hasIpAddress('127.0.0.1')")
                .and()
                .addFilter(getAuthenticationFilter());
        ;
       // http.logout().logoutSuccessUrl("/").permitAll(); //일단 로그아웃 후 리다이렉트 url 임시로 작성.
         http.logout().logoutSuccessUrl("/www.naver.com").permitAll();
        http.headers().frameOptions().disable();
        http.oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
    }

    //logoutUrl("/doLogout")
    //.logoutSuccessUrl("/login");

    //http.logout()
    //     .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    //     .logoutSuccessUrl("/")
    //     .invalidateHttpSession(true);

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(
                authenticationManager(), userService, env);

        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
