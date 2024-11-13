package com.etms.worldline.Repository;

import com.etms.worldline.model.SkillHub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillHubRepository extends JpaRepository<SkillHub,Long> {

}
