package com.example.hrservice.security;

import com.example.hrservice.service.HrService;
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
    private HrService hrService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Environment env;

    public WebSecurity(Environment env, HrService hrService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.env = env;
        this.hrService = hrService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/**").permitAll();
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
        http.authorizeRequests().antMatchers("/swagger-ui/**").permitAll();
        http.authorizeRequests().antMatchers("/v2/**").permitAll();
        http.authorizeRequests().antMatchers("/actuator/**").permitAll();
//        http.authorizeRequests()
//                .antMatchers("/**")
//                //.hasIpAddress(env.getProperty("gateway.ip"))
//                .access("hasIpAddress('127.0.0.1') or hasIpAddress('10.0.0.37') or hasIpAddress('52.20.91.194')")
//                .and()
//                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable();
    }

/*    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(
                                        authenticationManager(), hrService, env);

        return authenticationFilter;
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService).passwordEncoder(bCryptPasswordEncoder);
    }
}
