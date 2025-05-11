package edu.nugi.repository;

import edu.nugi.dto.Order;
import edu.nugi.entity.OrderEntity;
import edu.nugi.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
//    List<OrderEntity> findByName(String name);
//
//    List<OrderEntity> findByCategoryId(Integer categoryId);

    Order findByCode(String code);


//    @Query("SELECT o.code FROM Order o WHERE o.code LIKE CONCAT('ORD-', :year, '-%') ORDER BY o.code DESC")
//    List<String> getLastOrderCode(@Param("year") String year, Pageable pageable);

    @Query(value = "SELECT code FROM orders WHERE code LIKE CONCAT('ORD-', :year, '-%') ORDER BY code DESC LIMIT 1", nativeQuery = true)
    String getLastOrderCode(@Param("year") String year);
}
