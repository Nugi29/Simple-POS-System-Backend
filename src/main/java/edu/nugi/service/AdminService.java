package edu.nugi.service;

import edu.nugi.dto.Category;
import edu.nugi.dto.Admin;

import java.util.List;

public interface AdminService {
    List<Admin> getAll();

    void addAdmin(Admin admin);

    void updateAdmin(Admin admin);

    void deleteAdmin(Integer id);

}
