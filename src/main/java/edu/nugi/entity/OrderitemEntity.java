package edu.nugi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orderitem")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderitemEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "quantity")
    private Integer quantity;
    @Basic
    @Column(name = "unitprice")
    private Double unitprice;
    @Basic
    @Column(name = "subtotal")
    private Double subtotal;

}
