package com.example.userservice.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder2 implements ErrorDecoder {
    Environment env;

    @Override
    public Exception decode(String methodKey, Response response) {

        switch(response.status()){
            case 400 : break;
            case 404 :
                if (methodKey.contains("getOrders")) {
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()),
                            "There is no items");
//                                env.getProperty("order-service.exception.orders-is-empty"));
                }
                break;
            default:
                return new Exception(response.reason());
        }

        return null;
    }
}
