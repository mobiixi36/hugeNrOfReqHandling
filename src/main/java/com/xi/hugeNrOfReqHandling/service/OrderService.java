package com.xi.hugeNrOfReqHandling.service;

import com.xi.hugeNrOfReqHandling.repo.OrderRepository;
import com.xi.hugeNrOfReqHandling.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author xichen created on 10/02/2020
 */
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(@Qualifier("fake_postgres") OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public int addOrder(Order order) {
        return orderRepository.insertOrder(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Optional<Order> getOrderById(UUID id) {
        return orderRepository.getOrderById(id);
    }

    public boolean deleteOrder(UUID id) {
        return orderRepository.deleteOrderById(id);
    }

    public boolean updateOrder(UUID id, Order newOrder) {
        return orderRepository.updateOrderById(id, newOrder);
    }
}
