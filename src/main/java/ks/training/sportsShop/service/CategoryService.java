package ks.training.sportsShop.service;

import jakarta.validation.Valid;
import ks.training.sportsShop.entity.Category;
import ks.training.sportsShop.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    public Page<Category> getAllCategories(String name, Pageable pageable) {
        return categoryRepository.findByCategoryByName(name,pageable);
    }

    public void createCategory(@Valid Category category) {
        categoryRepository.save(category);
    }

    public Category fetchCategoryById(long id) {
        return this.categoryRepository.findById(id);
    }
}
