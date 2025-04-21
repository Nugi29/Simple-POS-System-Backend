package edu.nugi.service;

import edu.nugi.dto.Customer;
import edu.nugi.dto.Order;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll();

    void addCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(Integer id);

    Customer searchById(Integer id);

    List<Customer> searchByName(String name);

    List<Order> getCustomerOrderHistory(Integer id);
}
