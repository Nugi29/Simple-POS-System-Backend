package edu.nugi.repository;

import edu.nugi.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
    List<ItemEntity> findByName(String name);

    List<ItemEntity> findByCategoryId(Integer categoryId);
}


