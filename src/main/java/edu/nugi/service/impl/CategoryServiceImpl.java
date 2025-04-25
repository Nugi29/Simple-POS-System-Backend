package edu.nugi.service.impl;

import edu.nugi.dto.Category;
import edu.nugi.entity.CategoryEntity;
import edu.nugi.repository.CategoryRepository;
import edu.nugi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    final CategoryRepository repository;
    final ModelMapper mapper;

    @Override
    public List<Category> getAll() {
        ArrayList<Category> categoryList = new ArrayList<>();
        List<CategoryEntity> all = repository.findAll();

        all.forEach(categoryEntity -> {
            categoryList.add(mapper.map(categoryEntity, Category.class));
        });
        return categoryList;
    }

    @Override
    public void addCategory(Category category) {
        repository.save(mapper.map(category, CategoryEntity.class));
    }
}
