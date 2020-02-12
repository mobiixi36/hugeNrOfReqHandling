package com.xi.hugeNrOfReqHandling.repo;

import com.xi.hugeNrOfReqHandling.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    int insertOrder(UUID id, Order order);

    List<Order> getAllOrders();

    boolean deleteOrderById(UUID id);

    boolean updateOrderById(UUID id, Order order);

    Optional<Order> getOrderById(UUID id);

    default int insertOrder(Order order) {
        UUID id = UUID.randomUUID();
        return insertOrder(id, order);
    }
}
