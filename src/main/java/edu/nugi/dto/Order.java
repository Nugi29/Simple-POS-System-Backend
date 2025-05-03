package edu.nugi.dto;

import edu.nugi.entity.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

    private Integer id;
    private String code;
    private String datetime;
    private Double discount;
    private Double total;
    private Customer customer;
    private Admin admin;
    private Paymentmethod paymentmethod;
    private List<Item> items;

}
