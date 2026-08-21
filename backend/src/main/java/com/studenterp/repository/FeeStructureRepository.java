package com.studenterp.repository;

import com.studenterp.entity.FeeStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeeStructureRepository extends JpaRepository<FeeStructure, Long> {
    List<FeeStructure> findByProgramIdAndSemesterId(Long programId, Long semesterId);
}
