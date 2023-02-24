package com.example.furniture.Repo;

import com.example.furniture.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Integer> {
    @Query(value = "SELECT * FROM orders where user_id=?1", nativeQuery = true)
    List<Order> findOrderById(Integer id);
}
