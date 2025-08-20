package com.promptrepo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptDto {
    private String id;
    private String title;
    private String content;
    private String description;
    private Set<CategoryDto> categories;
    private Set<String> tags;
    private Set<PromptVariableDto> variables;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


