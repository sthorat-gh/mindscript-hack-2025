package com.promptrepo.service;

import com.promptrepo.dto.CategoryDto;
import com.promptrepo.dto.PromptDto;
import com.promptrepo.dto.PromptVariableDto;
import com.promptrepo.model.*;
import com.promptrepo.repository.CategoryRepository;
import com.promptrepo.repository.PromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PromptService {
    
    private final PromptRepository promptRepository;
    private final CategoryRepository categoryRepository;
    
    public Prompt createPrompt(String title, String content, String description,
                              Set<String> categoryIds, Set<String> tags,
                              Set<PromptVariableDto> variables) {
        
        Prompt prompt = Prompt.builder()
                .title(title)
                .content(content)
                .description(description)
                .tags(tags != null ? tags : new HashSet<>())
                .build();
        
        // Add categories
        if (categoryIds != null && !categoryIds.isEmpty()) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));
            prompt.setCategories(categories);
        }
        
        prompt = promptRepository.save(prompt);
        
        // Add variables
        if (variables != null && !variables.isEmpty()) {
            Prompt finalPrompt = prompt;
            Set<PromptVariable> promptVariables = variables.stream()
                    .map(v -> PromptVariable.builder()
                            .name(v.getName())
                            .description(v.getDescription())
                            .defaultValue(v.getDefaultValue())
                            .required(v.getRequired() != null ? v.getRequired() : true)
                            .type(v.getType() != null ? v.getType() : PromptVariable.VariableType.TEXT)
                            .prompt(finalPrompt)
                            .build())
                    .collect(Collectors.toSet());
            prompt.setVariables(promptVariables);
            prompt = promptRepository.save(prompt);
        }
        
        return prompt;
    }
    
    public Prompt updatePrompt(String id, String title, String content, String description,
                              Set<String> categoryIds, Set<String> tags,
                              Set<PromptVariableDto> variables) {
        
        Prompt prompt = promptRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
        
        if (title != null) prompt.setTitle(title);
        if (content != null) prompt.setContent(content);
        if (description != null) prompt.setDescription(description);
        if (tags != null) prompt.setTags(tags);
        
        // Update categories
        if (categoryIds != null) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));
            prompt.setCategories(categories);
        }
        
        return promptRepository.save(prompt);
    }
    
    public void deletePrompt(String id) {
        Prompt prompt = promptRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
        promptRepository.delete(prompt);
    }
    
    public Page<PromptDto> getAllPrompts(Pageable pageable) {
        return promptRepository.findAll(pageable).map(this::toDto);
    }
    
    public Page<PromptDto> searchPrompts(String search, Pageable pageable) {
        return promptRepository.searchPrompts(search, pageable).map(this::toDto);
    }
    
    public PromptDto getPromptById(String id) {
        Prompt prompt = promptRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prompt not found"));
        return toDto(prompt);
    }
    
    public Page<PromptDto> getPromptsByCategory(String categoryId, Pageable pageable) {
        return promptRepository.findByCategoryId(categoryId, pageable).map(this::toDto);
    }
    
    public Page<PromptDto> getPromptsByTag(String tag, Pageable pageable) {
        return promptRepository.findByTag(tag, pageable).map(this::toDto);
    }
    
    private PromptDto toDto(Prompt prompt) {
        return PromptDto.builder()
                .id(prompt.getId())
                .title(prompt.getTitle())
                .content(prompt.getContent())
                .description(prompt.getDescription())
                .categories(prompt.getCategories().stream()
                        .map(this::toCategoryDto)
                        .collect(Collectors.toSet()))
                .tags(prompt.getTags())
                .variables(prompt.getVariables().stream()
                        .map(this::toVariableDto)
                        .collect(Collectors.toSet()))
                .createdAt(prompt.getCreatedAt())
                .updatedAt(prompt.getUpdatedAt())
                .build();
    }
    
    private CategoryDto toCategoryDto(Category category) {
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
    
    private PromptVariableDto toVariableDto(PromptVariable variable) {
        return PromptVariableDto.builder()
                .name(variable.getName())
                .description(variable.getDescription())
                .defaultValue(variable.getDefaultValue())
                .required(variable.getRequired())
                .type(variable.getType())
                .build();
    }
}