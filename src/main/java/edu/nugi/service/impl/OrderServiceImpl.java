package edu.nugi.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import edu.nugi.dto.Admin;
import edu.nugi.dto.Category;
import edu.nugi.dto.Customer;
import edu.nugi.dto.Item;
import edu.nugi.dto.Order;
import edu.nugi.dto.Orderitem;
import edu.nugi.dto.Paymentmethod;
import edu.nugi.entity.AdminEntity;
import edu.nugi.entity.CategoryEntity;
import edu.nugi.entity.CustomerEntity;
import edu.nugi.entity.ItemEntity;
import edu.nugi.entity.OrderEntity;
import edu.nugi.entity.OrderitemEntity;
import edu.nugi.entity.PaymentmethodEntity;
import edu.nugi.repository.AdminRepository;
import edu.nugi.repository.CustomerRepository;
import edu.nugi.repository.ItemRepository;
import edu.nugi.repository.OrderRepository;
import edu.nugi.repository.PaymentmethodRepository;
import edu.nugi.service.OrderService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    final OrderRepository orderRepository;
    final AdminRepository adminRepository;
    final CustomerRepository customerRepository;
    final ItemRepository itemRepository;
    final PaymentmethodRepository paymentmethodRepository;

    final ModelMapper mapper;

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();

        for (OrderEntity orderEntity : orderRepository.findAll()) {

            // Map order items correctly
            List<Orderitem> orderItemList = orderEntity.getOrderitems()
                    .stream()
                    .map(orderItemEntity -> {
                        ItemEntity itemEntity = orderItemEntity.getItem();
                        CategoryEntity categoryEntity = itemEntity.getCategory();

                        // Create the Category for the Item
                        Category category = new Category(
                                categoryEntity.getId(),
                                categoryEntity.getName());

                        // Create the Item for the OrderItem
                        Item item = new Item(
                                itemEntity.getId(),
                                itemEntity.getCode(),
                                itemEntity.getName(),
                                itemEntity.getPrice(),
                                itemEntity.getDiscount(),
                                String.valueOf(itemEntity.getStock()),
                                itemEntity.getDoexpire().toString(),
                                category);

                        // Create the OrderItem with the Item and the quantity information
                        return new Orderitem(
                                orderItemEntity.getId(),
                                orderItemEntity.getQuantity(),
                                orderItemEntity.getUnitprice(),
                                orderItemEntity.getSubtotal(),
                                item,
                                null // We don't set the order here to avoid circular reference
                        );
                    })
                    .toList();

            // Create and populate the Order
            Order order = new Order();
            order.setId(orderEntity.getId());
            order.setCode(orderEntity.getCode());
            order.setDatetime(orderEntity.getDatetime().toString());
            order.setDiscount(orderEntity.getDiscount());
            order.setTotal(orderEntity.getTotal());
            order.setCustomer(mapCustomer(orderEntity.getCustomer()));
            order.setAdmin(mapAdmin(orderEntity.getAdmin()));
            order.setPaymentmethod(mapPayment(orderEntity.getPaymentmethod()));

            // Set the order items (not just the items)
            order.setItems(orderItemList);

            // Update the order reference in each orderItem if needed
            for (Orderitem orderItem : orderItemList) {
                orderItem.setOrder(order);
            }

            orders.add(order);
        }

        return orders;
    }

    @Override
    public void addOrder(Order orderDto) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setCode(orderDto.getCode());
        // Convert String datetime to Timestamp
        try {
            Timestamp timestamp;
            if (orderDto.getDatetime() == null || orderDto.getDatetime().isEmpty()) {
                timestamp = new Timestamp(System.currentTimeMillis());
            } else {
                // Try to parse the date from the DTO
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                LocalDateTime dateTime = LocalDateTime.parse(orderDto.getDatetime(), formatter);
                timestamp = Timestamp.valueOf(dateTime);
            }
            orderEntity.setDatetime(String.valueOf(timestamp));
        } catch (Exception e) {
            // If parsing fails, use current time
            orderEntity.setDatetime(String.valueOf(new Timestamp(System.currentTimeMillis())));
        }

        orderEntity.setDiscount(orderDto.getDiscount());

        // Set customer, admin, payment method by ID
        CustomerEntity customerEntity = customerRepository.findById(orderDto.getCustomer().getId()).orElse(null);
        orderEntity.setCustomer(customerEntity);

        AdminEntity adminEntity = adminRepository.findById(orderDto.getAdmin().getId()).orElse(null);
        orderEntity.setAdmin(adminEntity);

        PaymentmethodEntity paymentEntity = paymentmethodRepository.findById(orderDto.getPaymentmethod().getId())
                .orElse(null);
        orderEntity.setPaymentmethod(paymentEntity);

        // Calculate total and create order items
        double orderTotal = 0.0;
        List<OrderitemEntity> orderItems = new ArrayList<>();

        for (Orderitem itemDto : orderDto.getItems()) {
            ItemEntity itemEntity = itemRepository.findById(itemDto.getItem().getId()).orElse(null);
            if (itemEntity != null) {
                OrderitemEntity orderItemEntity = new OrderitemEntity();
                orderItemEntity.setItem(itemEntity);
                orderItemEntity.setOrder(orderEntity);
                orderItemEntity.setQuantity(itemDto.getQuantity());
                orderItemEntity.setUnitprice(itemEntity.getPrice());

                // Calculate subtotal (price × quantity)
                double subtotal = itemEntity.getPrice() * itemDto.getQuantity();
                orderItemEntity.setSubtotal(subtotal);

                orderItems.add(orderItemEntity);
                orderTotal += subtotal;
            }
        }

        // Apply order discount if any
        if (orderDto.getDiscount() != null && orderDto.getDiscount() > 0) {
            orderTotal = orderTotal * (1 - (orderDto.getDiscount() / 100.0));
        }

        orderEntity.setTotal(orderTotal);
        orderEntity.setOrderitems(orderItems);

        // Save the order
        orderRepository.save(orderEntity);
    }

    @Override
    public void updateOrder(Order orderDto) {
        // Check if order exists
        Optional<OrderEntity> existingOrderOpt = orderRepository.findById(orderDto.getId());
        if (existingOrderOpt.isPresent()) {
            OrderEntity existingOrder = existingOrderOpt.get();

            // Update basic order information
            if (orderDto.getCode() != null) {
                existingOrder.setCode(orderDto.getCode());
            }

            // Update datetime if provided
            if (orderDto.getDatetime() != null && !orderDto.getDatetime().isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                    LocalDateTime dateTime = LocalDateTime.parse(orderDto.getDatetime(), formatter);
                    existingOrder.setDatetime(String.valueOf(Timestamp.valueOf(dateTime)));
                } catch (Exception ignored) {
                    // Keep existing datetime if parsing fails
                }
            }

            // Update discount if provided
            if (orderDto.getDiscount() != null) {
                existingOrder.setDiscount(orderDto.getDiscount());
            }

            // Update customer if provided
            if (orderDto.getCustomer() != null && orderDto.getCustomer().getId() != null) {
                CustomerEntity customerEntity = customerRepository.findById(orderDto.getCustomer().getId())
                        .orElse(null);
                if (customerEntity != null) {
                    existingOrder.setCustomer(customerEntity);
                }
            }

            // Update admin if provided
            if (orderDto.getAdmin() != null && orderDto.getAdmin().getId() != null) {
                AdminEntity adminEntity = adminRepository.findById(orderDto.getAdmin().getId()).orElse(null);
                if (adminEntity != null) {
                    existingOrder.setAdmin(adminEntity);
                }
            }

            // Update payment method if provided
            if (orderDto.getPaymentmethod() != null && orderDto.getPaymentmethod().getId() != null) {
                PaymentmethodEntity paymentEntity = paymentmethodRepository
                        .findById(orderDto.getPaymentmethod().getId()).orElse(null);
                if (paymentEntity != null) {
                    existingOrder.setPaymentmethod(paymentEntity);
                }
            }

            // Update order items if provided
            if (orderDto.getItems() != null && !orderDto.getItems().isEmpty()) {
                // Remove existing order items
                existingOrder.getOrderitems().clear();

                // Calculate total and create new order items
                double orderTotal = 0.0;
                List<OrderitemEntity> orderItems = new ArrayList<>();

                for (Orderitem itemDto : orderDto.getItems()) {
                    ItemEntity itemEntity = itemRepository.findById(itemDto.getItem().getId()).orElse(null);
                    if (itemEntity != null) {
                        OrderitemEntity orderItemEntity = new OrderitemEntity();
                        orderItemEntity.setItem(itemEntity);
                        orderItemEntity.setOrder(existingOrder);
                        orderItemEntity.setQuantity(itemDto.getQuantity());
                        orderItemEntity.setUnitprice(itemEntity.getPrice());

                        // Calculate subtotal
                        double subtotal = itemEntity.getPrice() * itemDto.getQuantity();
                        orderItemEntity.setSubtotal(subtotal);

                        orderItems.add(orderItemEntity);
                        orderTotal += subtotal;
                    }
                }

                // Apply order discount if any
                if (existingOrder.getDiscount() != null && existingOrder.getDiscount() > 0) {
                    orderTotal = orderTotal * (1 - (existingOrder.getDiscount() / 100.0));
                }

                existingOrder.setTotal(orderTotal);
                existingOrder.setOrderitems(orderItems);
            }

            // Save the updated order
            orderRepository.save(existingOrder);
        } else {
            // If order doesn't exist, create it
            addOrder(orderDto);
        }
    }

    @Override
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order searchById(Integer id) {
        Optional<OrderEntity> orderEntityOpt = orderRepository.findById(id);
        if (orderEntityOpt.isPresent()) {
            return mapOrderEntityToDto(orderEntityOpt.get());
        }
        return null;
    }

    @Override
    public Order searchByCode(String code) {
        OrderEntity orderEntity = orderRepository.findByCode(code);
        if (orderEntity != null) {
            return mapOrderEntityToDto(orderEntity);
        }
        return null;
    }

    @Override
    public String getOrderCode() {
        String currentYear = String.valueOf(Year.now().getValue());
        String lastOrderCode = orderRepository.getLastOrderCode(currentYear);

        if (lastOrderCode == null || lastOrderCode.isEmpty()) {
            // If no orders exist for this year, start with ORD-YYYY-0001
            return "ORD-" + currentYear + "-0001";
        } else {
            try {
                // Extract the numeric part at the end
                String[] parts = lastOrderCode.split("-");
                if (parts.length >= 3) {
                    int lastNumber = Integer.parseInt(parts[2]);
                    int newNumber = lastNumber + 1;
                    // Format with leading zeros (4 digits)
                    return String.format("ORD-%s-%04d", currentYear, newNumber);
                }
            } catch (NumberFormatException e) {
                // If parsing fails, fallback to default format
            }
            // Fallback if parsing fails
            return "ORD-" + currentYear + "-0001";
        }
    }

    // Custom mapping method for Order entity to DTO
    private Order mapOrderEntityToDto(OrderEntity orderEntity) {
        // Create the Order DTO
        Order order = new Order();
        order.setId(orderEntity.getId());
        order.setCode(orderEntity.getCode());
        order.setDatetime(orderEntity.getDatetime().toString());
        order.setDiscount(orderEntity.getDiscount());
        order.setTotal(orderEntity.getTotal());

        // Map customer, admin, payment method
        order.setCustomer(mapCustomer(orderEntity.getCustomer()));
        order.setAdmin(mapAdmin(orderEntity.getAdmin()));
        order.setPaymentmethod(mapPayment(orderEntity.getPaymentmethod()));

        // Map order items
        List<Orderitem> orderItemList = orderEntity.getOrderitems()
                .stream()
                .map(orderItemEntity -> {
                    ItemEntity itemEntity = orderItemEntity.getItem();
                    CategoryEntity categoryEntity = itemEntity.getCategory();

                    // Create the Category for the Item
                    Category category = new Category(
                            categoryEntity.getId(),
                            categoryEntity.getName());

                    // Create the Item for the OrderItem
                    Item item = new Item(
                            itemEntity.getId(),
                            itemEntity.getCode(),
                            itemEntity.getName(),
                            itemEntity.getPrice(),
                            itemEntity.getDiscount(),
                            String.valueOf(itemEntity.getStock()),
                            itemEntity.getDoexpire().toString(),
                            category);

                    // Create the OrderItem with the Item and the quantity information
                    return new Orderitem(
                            orderItemEntity.getId(),
                            orderItemEntity.getQuantity(),
                            orderItemEntity.getUnitprice(),
                            orderItemEntity.getSubtotal(),
                            item,
                            null // We don't set the order here to avoid circular reference
                    );
                })
                .toList();

        // Set the order items
        order.setItems(orderItemList);

        // Update the order reference in each orderItem if needed
        for (Orderitem orderItem : orderItemList) {
            orderItem.setOrder(order);
        }

        return order;
    }

    // ✅ Mapping Helpers
    private Customer mapCustomer(CustomerEntity customerEntity) {
        return new Customer(
                customerEntity.getId(),
                customerEntity.getName(),
                customerEntity.getEmail(),
                customerEntity.getPhone(),
                customerEntity.getLoyaltypoints(),
                customerEntity.getPreferences());
    }

    private Paymentmethod mapPayment(PaymentmethodEntity entity) {
        return new Paymentmethod(
                entity.getId(),
                entity.getName());
    }

    private Admin mapAdmin(AdminEntity entity) {
        return new Admin(
                entity.getId(),
                entity.getName());
    }
}