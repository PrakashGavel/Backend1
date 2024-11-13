package com.etms.worldline.Repository;


import com.etms.worldline.model.SkillSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<SkillSet,Long> {
    Optional<SkillSet> findById(Long id);
    Optional<SkillSet> findBySkillName(String skillName);
    @Query("SELECT count(s) from SkillSet s")
    int findthecountofskills();

}
