package com.xi.hugeNrOfReqHandling.repo;

import com.xi.hugeNrOfReqHandling.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author xichen created on 10/02/2020
 */

@Repository("anotherFakeDb")
public class AnotherOrderRepositoryImpl implements OrderRepository {

    @Override
    public int insertOrder(UUID id, Order order) {
        return 0;
    }

    @Override
    public List<Order> getAllOrders() {
        return List.of(new Order(UUID.randomUUID(), "order from another db"));
    }

    @Override
    public boolean deleteOrderById(UUID id) {
        return false;
    }

    @Override
    public boolean updateOrderById(UUID id, Order order) {
        return false;
    }

    @Override
    public Optional<Order> getOrderById(UUID id) {
        return Optional.empty();
    }
}
