package com.promptrepo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prompt_variables")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptVariable extends BaseEntity {
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column
    private String defaultValue;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean required = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prompt_id", nullable = false)
    private Prompt prompt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private VariableType type = VariableType.TEXT;
    
    public enum VariableType {
        TEXT,
        NUMBER,
        BOOLEAN,
        SELECT,
        MULTILINE
    }
}
