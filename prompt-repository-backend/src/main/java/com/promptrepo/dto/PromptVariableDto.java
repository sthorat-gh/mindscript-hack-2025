package com.promptrepo.dto;

import com.promptrepo.model.PromptVariable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptVariableDto {
    private String name;
    private String description;
    private String defaultValue;
    private Boolean required;
    private PromptVariable.VariableType type;
}
