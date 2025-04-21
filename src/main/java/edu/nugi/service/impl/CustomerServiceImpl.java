package edu.nugi.service.impl;

import edu.nugi.dto.Customer;
import edu.nugi.dto.Order;
import edu.nugi.entity.CustomerEntity;
import edu.nugi.repository.CustomerRepository;
import edu.nugi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    final CustomerRepository repository;
    final ModelMapper mapper;

    @Override
    public List<Customer> getAll() {
        ArrayList<Customer> customerList = new ArrayList<>();
        List<CustomerEntity> all = repository.findAll();

        all.forEach(customerEntity -> {
            customerList.add(mapper.map(customerEntity, Customer.class));
        });
        return customerList;
    }

    @Override
    public void addCustomer(Customer customer) {
        repository.save(mapper.map(customer, CustomerEntity.class));
    }

    @Override
    public void updateCustomer(Customer customer) {
        repository.save(mapper.map(customer, CustomerEntity.class));
    }

    @Override
    public void deleteCustomer(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Customer searchById(Integer id) {
        return mapper.map(repository.findById(id), Customer.class);
    }

    @Override
    public List<Customer> searchByName(String name) {
        List<CustomerEntity> byName = repository.findByName(name);
        List<Customer> customerList = new ArrayList<>();
        byName.forEach(customerEntity -> {
            customerList.add(mapper.map(customerEntity, Customer.class));
        });
        return customerList;

    }

    @Override
    public List<Order> getCustomerOrderHistory(Integer id) {
        CustomerEntity customerEntity = repository.findById(id).orElse(null);
        if (customerEntity != null) {
            List<Order> orderList = new ArrayList<>();
            customerEntity.getOrders().forEach(orderEntity -> {
                orderList.add(mapper.map(orderEntity, Order.class));
            });
            return orderList;
        }
        return null;

    }
}
