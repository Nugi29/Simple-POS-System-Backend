package edu.nugi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {

    private Integer id;
    private String code;
    private String name;
    private Double price;
    private Double discount;
    private String stock;
    private String doexpire;
    private Category category;

}
