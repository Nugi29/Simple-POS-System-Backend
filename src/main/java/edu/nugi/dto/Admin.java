package edu.nugi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Admin {

    private Integer id;
    private String name;
    private String fullname;
    private String role;
    private String email;
    private String password;
    private String phone;

}
