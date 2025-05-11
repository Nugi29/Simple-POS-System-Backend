package edu.nugi.controller;

import edu.nugi.dto.Item;
import edu.nugi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@CrossOrigin
@RequiredArgsConstructor
public class ItemController {

    @Autowired
    ItemService service;

    @GetMapping("/get-all/list")
    public List<Item> getAll() {
        return service.getAll();
    }

    @PostMapping("/add")
    public void addItem(@RequestBody Item item) {
        service.addItem(item);
    }

    @PutMapping("/update")
    public void updateItem(@RequestBody Item item) {
        service.updateItem(item);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteItem(@PathVariable Integer id) {
        service.deleteItem(id);
    }

    @GetMapping("/search-by-id/{id}")
    public Item searchById(@PathVariable Integer id) {
        return service.searchById(id);
    }

    @GetMapping("/search-by-name/{name}")
    public List<Item> searchByName(@PathVariable String name) {
        return service.searchByName(name);
    }

    @GetMapping("/search-by-category/{categoryId}")
    public List<Item> searchByCategory(@PathVariable Integer categoryId) {
        return service.searchByCategory(categoryId);
    }

    @GetMapping("/search-by-code/{code}")
    public Item searchByCode(@PathVariable String code) {
        return service.searchByCode(code);
    }


}
