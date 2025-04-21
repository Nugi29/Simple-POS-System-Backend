package edu.nugi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Orderitem {

    private Integer id;
    private Integer quantity;
    private Double unitprice;
    private Double subtotal;
    private Item item;
    private Order order;

}
