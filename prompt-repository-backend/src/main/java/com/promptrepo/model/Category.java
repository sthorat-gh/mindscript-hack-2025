package com.promptrepo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {
    
    @Column(unique = true, nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    @Builder.Default
    private String icon = "📁"; // Default icon
    
    @Column(nullable = false)
    @Builder.Default
    private String color = "#3B82F6"; // Default blue color
    
    @ManyToMany(mappedBy = "categories")
    private Set<Prompt> prompts;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;
}
