package com.promptrepo.controller;

import com.promptrepo.dto.CategoryDto;
import com.promptrepo.model.Category;
import com.promptrepo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(categoryService.getCategoryById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto request) {
        try {
            Category category = categoryService.createCategory(
                    request.getName(),
                    request.getDescription(),
                    request.getIcon(),
                    request.getColor(),
                    request.getDisplayOrder()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(categoryService.getCategoryById(category.getId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the category");
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id,
                                           @RequestBody CategoryDto request) {
        try {
            Category category = categoryService.updateCategory(
                    id,
                    request.getName(),
                    request.getDescription(),
                    request.getIcon(),
                    request.getColor(),
                    request.getDisplayOrder()
            );
            
            return ResponseEntity.ok(categoryService.getCategoryById(category.getId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the category");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the category");
        }
    }
}