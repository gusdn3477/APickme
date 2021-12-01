package com.example.hrservice.security;

import com.example.hrservice.dto.HrDto;
import com.example.hrservice.service.HrService;
import com.example.hrservice.vo.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private HrService hrService;
    private Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                HrService hrService,
                                Environment env) {
        super.setAuthenticationManager(authenticationManager);
        this.hrService = hrService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(), creds.getPwd(), new ArrayList<>())
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String userName = ((User)authResult.getPrincipal()).getUsername();
        HrDto userDetails = hrService.getUserDetailsByEmail(userName);

        String token = Jwts.builder()
                .setSubject(userDetails.getEmpNo())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        response.addHeader("token", token);
        response.addHeader("empNo", userDetails.getEmpNo());
        //test 겸 추가
        //response.addHeader("email", userDetails.getEmail()); //이거랑
        //response.addHeader("Parent", userDetails.getParents());
        //response.addHeader("Auth", userDetails.getAuth()); //이건 필요 없을듯 F면 에러떠서
        response.addHeader("corpNo", userDetails.getCorpNo());
    }
}
