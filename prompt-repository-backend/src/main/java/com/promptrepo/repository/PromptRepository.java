package com.promptrepo.repository;

import com.promptrepo.model.Prompt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PromptRepository extends JpaRepository<Prompt, String> {
    
    @Query("SELECT p FROM Prompt p WHERE " +
           "(LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.content) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Prompt> searchPrompts(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT p FROM Prompt p JOIN p.categories c WHERE c.id = :categoryId")
    Page<Prompt> findByCategoryId(@Param("categoryId") String categoryId, Pageable pageable);
    
    @Query("SELECT p FROM Prompt p JOIN p.tags t WHERE t = :tag")
    Page<Prompt> findByTag(@Param("tag") String tag, Pageable pageable);
}
