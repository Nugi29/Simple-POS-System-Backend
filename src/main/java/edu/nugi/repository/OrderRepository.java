package edu.nugi.repository;

import edu.nugi.dto.Order;
import edu.nugi.entity.OrderEntity;
import edu.nugi.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
//    List<OrderEntity> findByName(String name);
//
//    List<OrderEntity> findByCategoryId(Integer categoryId);

    Order findByCode(String code);
}
