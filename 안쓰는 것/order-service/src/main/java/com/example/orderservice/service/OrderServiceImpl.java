package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.jpa.OrderRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderEntity = mapper.map(orderDto, OrderEntity.class);

        orderRepository.save(orderEntity);

        OrderDto returnValue = mapper.map(orderEntity, OrderDto.class);

        return returnValue;
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        OrderDto orderDto = new ModelMapper().map(orderEntity, OrderDto.class);

        return orderDto;
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Iterable<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderDto updateByOrderId(OrderDto orderDto, OrderDto orderDetails) {

        OrderEntity orderEntity = orderRepository.findByOrderId(orderDto.getOrderId());
        ModelMapper mapper = new ModelMapper();
        OrderDto orderUpdateDto = mapper.map(orderEntity, OrderDto.class);

        orderUpdateDto.setStatusCode(orderDetails.getStatusCode());

        ModelMapper orderMapper = new ModelMapper();
        orderMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderUpdateEntity = orderMapper.map(orderUpdateDto, OrderEntity.class);

        orderUpdateEntity.setId(orderEntity.getId());
        //orderUpdateEntity.setStatusCode(orderEntity.getStatusCode());
        orderRepository.save(orderUpdateEntity);

        return null;
    }
}