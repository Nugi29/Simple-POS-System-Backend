package edu.nugi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "category")
    private Collection<ItemEntity> items;

}
