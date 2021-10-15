package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    Environment env;
    RestTemplate restTemplate;

    OrderServiceClient orderServiceClient;

    CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           Environment env,
                           RestTemplate restTemplate,
                           OrderServiceClient orderServiceClient,
                           CircuitBreakerFactory circuitBreakerFactory) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;
        this.restTemplate = restTemplate;
        this.orderServiceClient = orderServiceClient;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null)
            throw new UsernameNotFoundException(email + ": not found");
        // User is an UserDetails
        User user = new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());

        return user;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        //userDto.setCreatedAt(now()); 어노테이션으로 자동저장인것같음

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        return null;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null)
            throw new UsernameNotFoundException("User not found");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(userEntity, UserDto.class);

        /* order-service에서 주문 내역 조회
        *  #1) RestTemplate
        * */
/*        List<ResponseOrder> ordersList = new ArrayList<>();
        String orderUrl = String.format(env.getProperty("order-service.url"), userId);
        ResponseEntity<List<ResponseOrder>> responseOrderList = restTemplate.exchange(orderUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ResponseOrder>>() {
                });
        ordersList = responseOrderList.getBody();*/

        /* #2) Open Feign */
       // List<ResponseOrder> ordersList = orderServiceClient.getOrders(userId);
        List<ResponseOrder> ordersList = null;
        try {
            ordersList = orderServiceClient.getOrders(userId); //feign client 사용부분 => 여기서 처리 후에 125번줄의 dto로 넣어줌
        } catch (FeignException ex) {
            log.error(ex.getMessage());
        }

/*        *//* Circuit Breaker *//*
        log.info("Before call order-service");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("my-circuitbreaker");
        ordersList = circuitBreaker.run(() -> orderServiceClient.getOrders(userId),
                throwable -> new ArrayList<>());
        log.info("After call order-service");*/

        userDto.setOrders(ordersList);

        return userDto;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null)
            throw new UsernameNotFoundException(email);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(userEntity, UserDto.class);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteByUserId(userId);

    }

    @Override
    public UserDto updateByUserId(UserDto userDto, UserDto userDetails) {

        UserEntity userEntity = userRepository.findByUserId(userDto.getUserId());
        ModelMapper mapper = new ModelMapper();
        UserDto userUpdateDto = mapper.map(userEntity, UserDto.class);

        userUpdateDto.setName(userDetails.getName());
        userUpdateDto.setPwd(userDetails.getPwd());
        userUpdateDto.setAddress(userDetails.getAddress());
        userUpdateDto.setPhone(userDetails.getPhone());

        ModelMapper usermapper = new ModelMapper();
        usermapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userUpdateEntity = usermapper.map(userUpdateDto, UserEntity.class);

        userUpdateEntity.setId(userEntity.getId());
        userUpdateEntity.setEncryptedPwd(bCryptPasswordEncoder.encode(userUpdateDto.getPwd()));

        userRepository.save(userUpdateEntity);

        return null;
    }



}
