package com.example.hrservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

/* 밑에 코드 4줄: 커스터 마이징 기능
주석처리 이유 : apiinfo는 swagger 3.0.0 에서 더이상 사용하지 않는다 함
3.0.0 사용하는 이유 : 강의에 spring boot 2.4.2이상 버전을 사용하는 사람은
swagger 버전 3.0.0을 사용하라 적혀있음
 */
//    private static final Contact DEFAULT_CONTACT = new Contact("team3","http://www.naver.com","szpdkss3@gmail.com");
//
//    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("API Title", "My User management REST API service",
//            "1.0","run:tos",DEFAULT_CONTACT,"Apache","http://www.apache.rog/licenses");

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("HR-Servcie API 문서")
                        .description("controller에 대해서만 작성")
                        .version("v1.0"));


    }
}