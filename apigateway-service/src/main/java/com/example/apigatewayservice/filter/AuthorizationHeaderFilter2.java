package com.example.apigatewayservice.filter;


import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter2 extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>{

    private Environment env;

    public AuthorizationHeaderFilter2(Environment env){
        this.env=env;
    }
//inner 클래스형태로 설정 관련 작업하기 위한
    private static class Config{

    }


//    사용자가 어떤 요청했을때 헤더에다가 로그인했을때 받았떤 토큰을
//    전달해주는 역할, 토큰이 잘들어가있나, 적절한 인증 처리되있는가
//    제대로 발급되있는가 등 확인후 통과시키는 작업
    // 로그인 -> 토큰발급 -> 작업들 , 서버쪽에서는 토큰이 잘맞는지 판단, 해당 토큰으 헤더안에 토큰이 포함되어 있다. 그래서 헤더값안에 토큰이 있는지 검사
    @Override
    public GatewayFilter apply(AuthorizationHeaderFilter.Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) { //Http 헤더값에서 Authorizztion 관련된 값이 있는지 확인
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED); // 401 ,포함이 안되있는 경우
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0); //request에서 해당하는 값을 가지고 올겁니다.배열이되기떄문에 0 인덱스
            String jwt = authorizationHeader.replace("Bearer", ""); // bearer토큰이라해서

            //맞는 토큰인지 검증
            if (!isJwtValid(jwt)) {
                return onError(exchange, "JWT is not valid", HttpStatus.UNAUTHORIZED); // 401 //맞지않으면 에러발생시킴
            }

            return chain.filter(exchange);
        };
    }

    //토큰 검사 , jwt가 정상적인지 확인
    private boolean isJwtValid(String jwt) {
        //먼저 returnValue 선언
        boolean returnValue = true;
        //jwt 아에는 subject라는 것이있어요 원했던 데이터 타입이 정상적인가 아닌지 확인할 수 있음. subject를 추출한다음에 이값이 정상적인지 아닌지 확인하면 됨.
        String subject = null;

        //subject의 값이 정상적인지 아닌지 판단 , subject라는값이 유저(user-service)안에서 어떻게 만들어지는지 봐야함 .
        //user-service에서 토큰생성할 때 .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")) 암호화 사용했었음
        //복호화(디코드) 할때는 해당하는 값을 가지고 있어야함. application.yml에 토큰 정보 추가.

        try{
            subject = Jwts.parser().setSigningKey(env.getProperty("token_secret"))
                    .parseClaimsJws(jwt).getBody()//복호화할 대상, 우리가 가진 토큰을 문자형 데이터값으로 파싱하기 위해서 parseClaimsJws로 파싱하고
                    .getSubject(); //서브젝트 추출

        }catch(Exception ex){
            returnValue = false;
        }


        if(subject == null || subject.isEmpty() ){
            returnValue = false;
        }
        //subject 값이 정상적인지 따져보자



        return returnValue;
    }

    //에러 발생하면 해당 에러값을 응답해주는
    //SPring mvc 에서는 Servlet 사용하겠지만 , WebFlux 에서는 ServerHttpResponse 사용
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        //exchange로 부터 response 객체하나 준비
        ServerHttpResponse response = exchange.getResponse();
        //response에 전달받았던 상태 코드를 저장
        response.setStatusCode(httpStatus);
        //에러 메시지 출력 .
        log.error(err);
        return response.setComplete(); //mono타입으로 전달할 수 있다. mono 타입은 WebFlux관련
    }

}
