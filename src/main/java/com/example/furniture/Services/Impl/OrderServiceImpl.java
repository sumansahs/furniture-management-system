package com.example.furniture.Services.Impl;

import com.example.furniture.Entity.Order;
import com.example.furniture.Entity.Product;
import com.example.furniture.Pojo.OrderPojo;
import com.example.furniture.Pojo.ProductPojo;
import com.example.furniture.Repo.OrderRepo;
import com.example.furniture.Repo.ProductRepo;
import com.example.furniture.Repo.UserRepo;
import com.example.furniture.Services.OrderServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderServices {
    private  final OrderRepo orderRepo;
    private  final UserRepo userRepo;
    private  final ProductRepo productRepo;
    @Override
    public OrderPojo save(OrderPojo orderPojo) {
        Order order=new Order();
        if(orderPojo.getId()!=null){
            order.setId(order.getId());
        }
        order.setAddress(orderPojo.getAddress());
        order.setQuantity(orderPojo.getQuantity());
        order.setUser_id(userRepo.findById(orderPojo.getUser_id()).orElseThrow());
        order.setProduct_id(productRepo.findById(orderPojo.getProduct_id()).orElseThrow());

        orderRepo.save(order);
        return new OrderPojo(order);
    }

    @Override
    public List<Order> findOrderById(Integer id) {
        return findAllinList(orderRepo.findOrderById(id));
    }


//    @Override
//    public Order findOrderById(Integer id) {
//            Order order=orderRepo.findById(id).orElseThrow(()-> new RuntimeException("not found"));
//            order=Order.builder()
//                    .id(order.getId())
//                    .quantity(order.getQuantity())
//                    .user_id(order.getUser_id())
//                    .product_id(order.getProduct_id())
//                    .address(order.getAddress())
//                    .build();
//            return order;
//    }


    @Override
    public List<Order> findAll() {
        return findAllinList(orderRepo.findAll());
    }
    public List<Order> findAllinList(List<Order> list){

        Stream<Order> allJobsWithImage = list.stream().map(product ->
                Order.builder()
                        .id(product.getId())
                        .quantity(product.getQuantity())
                        .address(product.getAddress())
                        .user_id(product.getUser_id())
                        .product_id(product.getProduct_id())
                        .build()
        );
        list = allJobsWithImage.toList();
        return list;
    }




    @Override
    public Order findById(Integer id) {
        Order product=orderRepo.findById(id).orElseThrow(()-> new RuntimeException("not found"));
        product=Order.builder()
                .id(product.getId())
                .quantity(product.getQuantity())
                .address(product.getAddress())
                .user_id(product.getUser_id())
                .product_id(product.getProduct_id())
                .build();
        return product;
    }

    @Override
    public void deleteById(Integer id) {
        orderRepo.deleteById(id);
    }
}
