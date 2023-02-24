package com.example.furniture.Services;

import com.example.furniture.Entity.Order;
import com.example.furniture.Entity.Product;
import com.example.furniture.Pojo.OrderPojo;

import java.util.List;

public interface OrderServices {
    OrderPojo save(OrderPojo orderPojo);
    List<Order> findOrderById(Integer id);

    List<Order> findAll();

    Order findById(Integer id);
    void deleteById(Integer id);
}
