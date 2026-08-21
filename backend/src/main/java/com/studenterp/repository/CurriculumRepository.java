package com.studenterp.repository;

import com.studenterp.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
    List<Curriculum> findByProgramIdAndSemesterId(Long programId, Long semesterId);
}
