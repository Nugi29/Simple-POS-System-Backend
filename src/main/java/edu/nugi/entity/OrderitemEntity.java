package edu.nugi.entity;

import jakarta.persistence.*;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

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
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    private ItemEntity item;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private OrderEntity order;


}
