package com.xi.hugeNrOfReqHandling.repo;

import com.xi.hugeNrOfReqHandling.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author xichen created on 10/02/2020
 */
@Repository("fake_postgres")
public class OrderRepositoryImpl implements OrderRepository {

    private static List<Order> DB = new ArrayList<>();


    @Override
    public int insertOrder(UUID id, Order order) {
        DB.add(new Order(id, order.getName()));
        return 1;
    }

    @Override
    public List<Order> getAllOrders() {
        return DB;
    }

    @Override
    public boolean deleteOrderById(UUID id) {
        Optional<Order> orderMaybe = getOrderById(id);
        if (orderMaybe.isEmpty()) {
            return false;
        }

        Order order = orderMaybe.get();
        DB.remove(order);
        return true;
    }

    @Override
    public boolean updateOrderById(UUID id, Order updatedOrder) {
        return getOrderById(id).map( ord -> {
            int indexOfOrderToUpdate = DB.indexOf(ord);
            if (indexOfOrderToUpdate >= 0) {
                DB.set(indexOfOrderToUpdate, new Order(id, updatedOrder.getName()));
                return true;
            }
            return false;
        }).orElse(false);
    }

    @Override
    public Optional<Order> getOrderById(UUID id) {
        return DB.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }
}
