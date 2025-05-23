package edu.nugi.controller;

import edu.nugi.dto.Category;
import edu.nugi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    CategoryService service;

    @RequestMapping("/get-all/list")
    public List<Category> getAll() {
        return service.getAll();
    }

    @RequestMapping("/add")
    public void addCategory(@RequestBody Category category) {
        service.addCategory(category);
    }
}
