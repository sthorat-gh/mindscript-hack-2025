package com.promptrepo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePromptRequest {
    private String title;
    private String content;
    private String description;
    private Set<String> categoryIds;
    private Set<String> tags;
    private Set<PromptVariableDto> variables;
}
