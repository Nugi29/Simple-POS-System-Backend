package edu.nugi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "paymentmethod")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentmethodEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "paymentmethod")
    private Collection<OrderEntity> ordersById;

}
