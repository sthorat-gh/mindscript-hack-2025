package com.promptrepo.controller;

import com.promptrepo.dto.CreatePromptRequest;
import com.promptrepo.dto.PromptDto;
import com.promptrepo.dto.UpdatePromptRequest;
import com.promptrepo.model.Prompt;
import com.promptrepo.service.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prompts")
@RequiredArgsConstructor
@CrossOrigin
public class PromptController {
    
    private final PromptService promptService;
    
    @PostMapping
    public ResponseEntity<?> createPrompt(@RequestBody CreatePromptRequest request) {
        try {
            Prompt prompt = promptService.createPrompt(
                    request.getTitle(),
                    request.getContent(),
                    request.getDescription(),
                    request.getCategoryIds(),
                    request.getTags(),
                    request.getVariables()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(promptService.getPromptById(prompt.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrompt(@PathVariable String id,
                                         @RequestBody UpdatePromptRequest request) {
        try {
            Prompt prompt = promptService.updatePrompt(
                    id,
                    request.getTitle(),
                    request.getContent(),
                    request.getDescription(),
                    request.getCategoryIds(),
                    request.getTags(),
                    request.getVariables()
            );
            
            return ResponseEntity.ok(promptService.getPromptById(prompt.getId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrompt(@PathVariable String id) {
        try {
            promptService.deletePrompt(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<Page<PromptDto>> getAllPrompts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC") ? 
                Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        return ResponseEntity.ok(promptService.getAllPrompts(pageable));
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<PromptDto>> searchPrompts(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(promptService.searchPrompts(q, pageable));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPromptById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(promptService.getPromptById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<PromptDto>> getPromptsByCategory(
            @PathVariable String categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(promptService.getPromptsByCategory(categoryId, pageable));
    }
    
    @GetMapping("/tag/{tag}")
    public ResponseEntity<Page<PromptDto>> getPromptsByTag(
            @PathVariable String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(promptService.getPromptsByTag(tag, pageable));
    }
}