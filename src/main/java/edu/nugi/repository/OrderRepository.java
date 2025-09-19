package edu.nugi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.nugi.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    // List<OrderEntity> findByName(String name);
    //
    // List<OrderEntity> findByCategoryId(Integer categoryId);

    OrderEntity findByCode(String code);

    // @Query("SELECT o.code FROM Order o WHERE o.code LIKE CONCAT('ORD-', :year,
    // '-%') ORDER BY o.code DESC")
    // List<String> getLastOrderCode(@Param("year") String year, Pageable pageable);

    @Query(value = "SELECT code FROM orders WHERE code LIKE CONCAT('ORD-', :year, '-%') ORDER BY code DESC LIMIT 1", nativeQuery = true)
    String getLastOrderCode(@Param("year") String year);
}
