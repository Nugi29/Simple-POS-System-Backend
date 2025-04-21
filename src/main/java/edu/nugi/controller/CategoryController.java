package edu.nugi.controller;

import edu.nugi.dto.Category;
import edu.nugi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService service;

    @RequestMapping("/get-all/list")
    public List<Category> getAll() {
        return service.getAll();
    }
}
