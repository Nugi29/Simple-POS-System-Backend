package edu.nugi.controller;

import edu.nugi.dto.Customer;
import edu.nugi.dto.Order;
import edu.nugi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService service;

    @GetMapping("/get-all/list")
    public List<Customer> getAll() {
        return service.getAll();
    }

    @PostMapping("/add")
    public void addCustomer(@RequestBody Customer customer) {
        service.addCustomer(customer);
    }

    @PutMapping("/update")
    public void updateCustomer(@RequestBody Customer customer) {
        service.updateCustomer(customer);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable Integer id) {
        service.deleteCustomer(id);
    }

    @GetMapping("/search-by-id/{id}")
    public Customer searchById(@PathVariable Integer id) {
        return service.searchById(id);
    }

    @GetMapping("/search-by-name/{name}")
    public List<Customer> searchByName(@PathVariable String name) {
        return service.searchByName(name);
    }

    @GetMapping("/{id}/orders")
    public List<Order> getCustomerOrderHistory(@PathVariable Integer id) {
        return service.getCustomerOrderHistory(id);
    }



}
