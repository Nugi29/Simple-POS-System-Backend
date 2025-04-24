package edu.nugi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "code")
    private String code;
    @Basic
    @Column(name = "datetime")
    private Timestamp datetime;
    @Basic
    @Column(name = "discount")
    private Double discount;
    @Basic
    @Column(name = "total")
    private Double total;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private CustomerEntity customer;
    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id", nullable = false)
    private AdminEntity admin;
    @ManyToOne
    @JoinColumn(name = "paymentmethod_id", referencedColumnName = "id", nullable = false)
    private PaymentmethodEntity paymentmethod;
    @OneToMany(mappedBy = "order")
    private Collection<OrderitemEntity> orderitems;

}
