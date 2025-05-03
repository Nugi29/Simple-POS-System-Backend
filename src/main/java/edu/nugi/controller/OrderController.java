package edu.nugi.controller;

import edu.nugi.dto.Order;
import edu.nugi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    OrderService service;

    @GetMapping("/get-all/list")
    public List<Order> getAll() {
        return service.getAll();
    }

    @PostMapping("/add")
    public void addOrder(@RequestBody Order order) {
        service.addOrder(order);
    }

    @PutMapping("/update")
    public void updateOrder(@RequestBody Order order) {
        service.updateOrder(order);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        service.deleteOrder(id);
    }

    @GetMapping("/search-by-id/{id}")
    public Order searchById(@PathVariable Integer id) {
        return service.searchById(id);
    }

    @GetMapping("/search-by-code/{code}")
    public Order searchByCode(@PathVariable String code) {
        return service.searchByCode(code);
    }



}
