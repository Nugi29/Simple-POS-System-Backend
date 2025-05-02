package edu.nugi.service;

import edu.nugi.dto.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAll();

    void addItem(Item item);

    void updateItem(Item item);

    void deleteItem(Integer id);

    Item searchById(Integer id);

    List<Item> searchByName(String name);

    List<Item> searchByCategory(Integer categoryId);

    Item searchByCode(String code);
}
