package edu.nugi.repository;

import edu.nugi.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    List<CustomerEntity> findByName(String name);

}
