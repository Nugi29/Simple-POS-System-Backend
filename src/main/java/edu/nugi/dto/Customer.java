package edu.nugi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private Integer loyalitypoints;
    private String preferences;

}
