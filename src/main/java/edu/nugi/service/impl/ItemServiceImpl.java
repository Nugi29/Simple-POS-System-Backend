package edu.nugi.service.impl;

import edu.nugi.dto.Item;
import edu.nugi.entity.ItemEntity;
import edu.nugi.repository.ItemRepository;
import edu.nugi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    final ItemRepository repository;
    final ModelMapper mapper;

    @Override
    public List<Item> getAll() {
        ArrayList<Item> itemList = new ArrayList<>();
        List<ItemEntity> all = repository.findAll();

        all.forEach(itemEntity -> {
            itemList.add(mapper.map(itemEntity, Item.class));
        });
        return itemList;
    }

    @Override
    public void addItem(Item item) {
        repository.save(mapper.map(item, ItemEntity.class));
    }

    @Override
    public void updateItem(Item item) {
        repository.save(mapper.map(item, ItemEntity.class));
    }

    @Override
    public void deleteItem(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Item searchById(Integer id) {
        return mapper.map(repository.findById(id), Item.class);
    }

    @Override
    public List<Item> searchByName(String name) {
        List<ItemEntity> byName = repository.findByName(name);
        List<Item> itemList = new ArrayList<>();
        byName.forEach(itemEntity -> {
            itemList.add(mapper.map(itemEntity, Item.class));
        });
        return itemList;

    }

    @Override
    public List<Item> searchByCategory(Integer categoryId) {
        List<ItemEntity> byCategoryId = repository.findByCategoryId(categoryId);
        List<Item> itemList = new ArrayList<>();
        byCategoryId.forEach(itemEntity -> {
            itemList.add(mapper.map(itemEntity, Item.class));
        });
        return itemList;

    }


}
