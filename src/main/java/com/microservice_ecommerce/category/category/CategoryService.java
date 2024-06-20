package com.microservice_ecommerce.category.category;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    protected CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    protected ResponseEntity<List<CategoryResponse>> findAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(categoryResponses);
    }

    protected void save(Category category) {
        categoryRepository.save(category);
    }

    protected CategoryResponse view(Long id) {
        Category foundCategory = findById(id);

        return new CategoryResponse(foundCategory.getId(), foundCategory.getName());
    }

    protected void update(Long id, Category category) {
        Category existingCategory = findById(id);
        existingCategory.setName(category.getName());

        categoryRepository.save(existingCategory);
    }

    protected void delete(Long id) {
        Category existingCategory = findById(id);
        categoryRepository.deleteById(existingCategory.getId());
    }

    private Category findById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("No category found against this id: " + id));
    }

    private CategoryResponse convertToDTO(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }

}
