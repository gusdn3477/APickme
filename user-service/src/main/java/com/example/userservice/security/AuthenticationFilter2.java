package com.example.userservice.security;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

public class AuthenticationFilter2 extends UsernamePasswordAuthenticationFilter {
    private UserService userService;
    private Environment env;

    public AuthenticationFilter2(AuthenticationManager authenticationManager,
                                 UserService uerService,
                                 Environment env) {
        super(authenticationManager);
        this.userService=uerService;
        this.env=env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //요청정보를 보냈을때 처리시켜주는 메서드 정의해보겠따.
        // 사용자 요청 정보가 RequestLogin 클래스로 들어옴. Objectmapper로 변환해보겠음 , 전달되어진 inputStream에
        //전달되어진 inputStream에 어떤 값이 들어가있을때 그 값을 우리가 원하는 class 형태로 바꿈.
        //inputStream으로 받은 이유는 우리가 전달시켜주고자 하는 로그인의 값은 post형태로 전달되죠 post형태로 전달되는 것은 request parameter로 받을수 없기때문에
        //inputStream으로 처리해주시면 수작업으로 데이터가 어떤게 들어오는지 ? 처리하실수 있음
        try {
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

            // 인풋파라미터 넘어왔떤 데이터가 생겼죠실제 인증정보를 만들거임
            // UsernamePasswordAuthenticationFilter에 전달해야 되는데, 이 값을 사용하기 위해 SPring security web authentication 패키지에있는
            //UernamePasswordAuthenticationToekn으로 변경시켜야함

            //사용자가 입력했던 이메일 ,비밀번호를 스프링시큐리티에서 사용할수있는 형태의 값으로 바꿔줄 필요가 있음 UsernamePasswordAuthenticationToken
           // new UsernamePasswordAuthenticationToken(creds.getEmail(),creds.getPassword(), new ArrayList<>()); //인자값 하나더 넣을껀데 권한에 관련된,어떤 권한을 가질건지

            //최종적으로 값을 토큰으로 변경했으니 그 값을 처리해주기위해,인증 처리를위해 getAuthenticationManager에 인증작업을 요청해야한다. 그러면 아이디랑 패스워드를 비교해준다.?
            return getAuthenticationManager().authenticate(new
                    UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                    new ArrayList<>()));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //실제 로그인 성공했을때 정확하게 어떤 처리해주실건지, 토큰을 만든다거나 토큰의 만료시간은 언제 로그인할때 반환값은 어떤거? 등등등의 작업을한다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String userName = ((User)authResult.getPrincipal()).getUsername();
       UserDto userDetails = userService.getUserDetailsByEmail(userName);

        //DTO 객체로 토큰 추가 작업
        //1. 먼저, dependency 추가
        // 2.application.yml 에 토큰 관련작업
        // compact로 생성하는거 같음
        String token = Jwts.builder()
                .setSubject(userDetails.getUserId())  //userId로 토큰 만들꺼구
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(env.getProperty("token.expiration_time")))) //토큰 유효기간 , System.currentTimeMillis()는 현재시간 ,application.yml에서 가져오는건 String 형태라바꿈꿈
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")) //암호화 하기위해 singWith에 시그니쳐 알고리즘 추가하겠다., 이때 키 조합할 수있는 값도 같이 지정합시다
                .compact();

                response.addHeader("token" , token);
                response.addHeader("userId", userDetails.getUserId());
    }
}
//    successfulAuthentication에서 토큰발행해야하는데
//    사용자의 정보를 토대로
//        토큰생성할건데 이메일과 패스워드를 가지고 회원가입하면
//        userid가있죠 우리는 이거가지고 토큰을 만들거임
//        successfulAhthenticationl() 안에서 이메일로 에서 UserDto를 가져올거임
//        여기 userId 에서 JWT를 생성할거에요
//        생성되 토큰값을 response에 헤더갑에 추가할거고
//        클라이어트에게 반환될거임