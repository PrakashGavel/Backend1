package com.etms.worldline.Repository;

import com.etms.worldline.model.ProjectSkills;
import com.etms.worldline.model.UserSkills;
import com.etms.worldline.payload.request.projectskilldetails;
import com.etms.worldline.payload.request.skilldetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProjectSkillsRepository extends JpaRepository<ProjectSkills,Long> {
    @Query("select new com.etms.worldline.payload.request.projectskilldetails(u.skillSet.skillName,u.projectskill_level) from ProjectSkills u where u.project.pro_id=:proId")
    List<projectskilldetails> findtheproskillsbyid(@Param("proId") Long proId);
    @Modifying
    @Transactional
    @Query("DELETE FROM ProjectSkills ps WHERE ps.project.pro_id = :proId")
    void deleteByProjectId(@Param("proId") Long proId);
    @Query("select u from ProjectSkills u where u.project.pro_id=:proId")
    List<ProjectSkills> findbyproid(@Param("proId") Long proId);
    @Modifying
    @Query("UPDATE ProjectSkills u SET u.projectskill_level=:skillLevel WHERE u.project.pro_id=:proId AND u.skillSet.id=:skillId")
    int updateSkillLevel(@Param("proId") Long proId,@Param("skillId") Long skillId, @Param("skillLevel") int skillLevel);

}
