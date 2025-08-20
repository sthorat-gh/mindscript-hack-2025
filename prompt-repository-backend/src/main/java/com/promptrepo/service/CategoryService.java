package com.promptrepo.service;

import com.promptrepo.dto.CategoryDto;
import com.promptrepo.model.Category;
import com.promptrepo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    public Category createCategory(String name, String description, String icon, String color, Integer displayOrder) {
        if (categoryRepository.existsByName(name)) {
            throw new IllegalArgumentException("Category with this name already exists");
        }
        
        Category category = Category.builder()
                .name(name)
                .description(description)
                .icon(icon != null ? icon : "📁")
                .color(color != null ? color : "#3B82F6")
                .displayOrder(displayOrder != null ? displayOrder : 0)
                .build();
        
        return categoryRepository.save(category);
    }
    
    public Category updateCategory(String id, String name, String description, String icon, String color, Integer displayOrder) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        
        if (name != null && !name.equals(category.getName())) {
            if (categoryRepository.existsByName(name)) {
                throw new IllegalArgumentException("Category with this name already exists");
            }
            category.setName(name);
        }
        
        if (description != null) category.setDescription(description);
        if (icon != null) category.setIcon(icon);
        if (color != null) category.setColor(color);
        if (displayOrder != null) category.setDisplayOrder(displayOrder);
        
        return categoryRepository.save(category);
    }
    
    public void deleteCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        categoryRepository.delete(category);
    }
    
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    public CategoryDto getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return toDto(category);
    }
    
    private CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .icon(category.getIcon())
                .color(category.getColor())
                .displayOrder(category.getDisplayOrder())
                .promptCount(category.getPrompts() != null ? category.getPrompts().size() : 0)
                .build();
    }
}
