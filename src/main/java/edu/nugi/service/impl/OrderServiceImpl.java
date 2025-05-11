package edu.nugi.service.impl;

import edu.nugi.dto.*;
import edu.nugi.entity.*;
import edu.nugi.repository.*;
import edu.nugi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    final OrderRepository orderRepository;
    final AdminRepository adminRepository;
    final CustomerRepository customerRepository;
    final ItemRepository itemRepository;
    final PaymentmethodRepository paymentmethodRepository;

    final ModelMapper mapper;

    // ✅ Return All Orders as DTOs
    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();

        for (OrderEntity orderEntity : orderRepository.findAll()) {

            List<Item> itemList = orderEntity.getOrderitems()
                    .stream()
                    .map(orderItem -> {
                        ItemEntity itemEntity = orderItem.getItem();
                        CategoryEntity categoryEntity = itemEntity.getCategory();

                        Category category = new Category(
                                categoryEntity.getId(),
                                categoryEntity.getName()
                        );

                        return new Item(
                                itemEntity.getId(),
                                itemEntity.getCode(),
                                itemEntity.getName(),
                                itemEntity.getPrice(),
                                itemEntity.getDiscount(),
                                String.valueOf(itemEntity.getStock()),
                                itemEntity.getDoexpire().toString(),
                                category
                        );
                    })
                    .toList();

            Order order = new Order();
            order.setId(orderEntity.getId());
            order.setCode(orderEntity.getCode());
            order.setDatetime(orderEntity.getDatetime().toString());
            order.setDiscount(orderEntity.getDiscount());
            order.setTotal(orderEntity.getTotal());
            order.setCustomer(mapCustomer(orderEntity.getCustomer()));
            order.setAdmin(mapAdmin(orderEntity.getAdmin()));
            order.setPaymentmethod(mapPayment(orderEntity.getPaymentmethod()));
            order.setItems(itemList);

            orders.add(order);
        }

        return orders;
    }

    // ✅ Add Order with mapping from DTO to Entity
    @Override
    public void addOrder(Order orderDto) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setCode(orderDto.getCode());
        orderEntity.setDatetime(orderDto.getDatetime());
        orderEntity.setDiscount(orderDto.getDiscount());
        orderEntity.setTotal(orderDto.getTotal());

        // Set customer, admin, payment method by ID
        orderEntity.setCustomer(customerRepository.findById(orderDto.getCustomer().getId()).orElse(null));
        orderEntity.setAdmin(adminRepository.findById(orderDto.getAdmin().getId()).orElse(null));
        orderEntity.setPaymentmethod(paymentmethodRepository.findById(orderDto.getPaymentmethod().getId()).orElse(null));

        // Set OrderItems
        List<OrderitemEntity> orderItems = new ArrayList<>();
        for (Item itemDto : orderDto.getItems()) {
            ItemEntity itemEntity = itemRepository.findById(itemDto.getId()).orElse(null);
            if (itemEntity != null) {
                OrderitemEntity orderItem = new OrderitemEntity();
                orderItem.setItem(itemEntity);
                orderItem.setOrder(orderEntity);
                orderItem.setQuantity(1); // You can customize this if needed
                orderItem.setUnitprice(itemEntity.getPrice());
                orderItem.setSubtotal(itemEntity.getPrice()); // or calculate based on qty
                orderItems.add(orderItem);
            }
        }
        orderEntity.setOrderitems(orderItems);

        orderRepository.save(orderEntity);
    }

    @Override
    public void updateOrder(Order orderDto) {
        // This is same as addOrder for now (upsert)
        addOrder(orderDto);
    }

    @Override
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order searchById(Integer id) {
        return mapper.map(orderRepository.findById(id).orElse(null), Order.class);
    }

    @Override
    public Order searchByCode(String code) {
        return mapper.map(orderRepository.findByCode(code), Order.class);
    }

    @Override
    public String getOrderCode() {
        return orderRepository.getLastOrderCode(String.valueOf(Year.now().getValue()));
    }


    // ✅ Mapping Helpers
    private Customer mapCustomer(CustomerEntity customerEntity) {
        return new Customer(
                customerEntity.getId(),
                customerEntity.getName(),
                customerEntity.getEmail(),
                customerEntity.getPhone(),
                customerEntity.getLoyaltypoints(),
                customerEntity.getPreferences()
        );
    }

    private Paymentmethod mapPayment(PaymentmethodEntity entity) {
        return new Paymentmethod(
                entity.getId(),
                entity.getName()
        );
    }

    private Admin mapAdmin(AdminEntity entity) {
        return new Admin(
                entity.getId(),
                entity.getName()
        );
    }

}
