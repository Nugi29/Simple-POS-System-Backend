package edu.nugi.controller;

import edu.nugi.dto.Admin;
import edu.nugi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService service;

    @GetMapping("/get-all/list")
    public List<Admin> getAll() {
        return service.getAll();
    }

    @PostMapping("/add")
    public void addAdmin(@RequestBody Admin admin) {
        service.addAdmin(admin);
    }

    @PutMapping("/update")
    public void updateAdmin(@RequestBody Admin admin) {
        service.updateAdmin(admin);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAdmin(@PathVariable Integer id) {
        service.deleteAdmin(id);
    }

}
