package edu.nugi.service;

import edu.nugi.dto.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    void addCategory(Category category);
}
