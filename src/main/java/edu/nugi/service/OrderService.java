package edu.nugi.service;

import edu.nugi.dto.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();

    void addOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(Integer id);

    Order searchById(Integer id);

    Order searchByCode(String code);
}
