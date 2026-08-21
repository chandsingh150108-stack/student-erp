package com.studenterp.repository;

import com.studenterp.entity.FeeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FeeCategoryRepository extends JpaRepository<FeeCategory, Long> {
    Optional<FeeCategory> findByName(String name);
}
