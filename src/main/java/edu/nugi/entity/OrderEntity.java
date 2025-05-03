package edu.nugi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Collection<OrderitemEntity> orderitems;

}
