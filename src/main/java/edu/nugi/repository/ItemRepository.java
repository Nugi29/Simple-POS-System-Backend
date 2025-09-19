package edu.nugi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.nugi.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
    List<ItemEntity> findByName(String name);

    List<ItemEntity> findByCategoryId(Integer categoryId);

    ItemEntity findByCode(String code);
}
