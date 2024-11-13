package com.etms.worldline.Repository;

import com.etms.worldline.model.Nominations;
import com.etms.worldline.payload.response.NominatedSkillsStatusResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NominationsRepository extends JpaRepository<Nominations,Long> {
    @Modifying
    @Query("UPDATE Nominations n SET n.manager_approval = :ack,n.modifiedAt=:modifiedAt WHERE n.user.user_id = :user_id AND n.skillSet.id = :skill_id AND n.manager_approval='pending'")
    int updateStatus(@Param("user_id") Long user_id, @Param("skill_id") Long skill_id, @Param("ack") String ack, @Param("modifiedAt") LocalDateTime modifiedAt);
    @Modifying
    @Query("UPDATE Nominations n SET n.manager_approval = :ack,n.admin_approval=:ack,n.modifiedAt=:modifiedAt WHERE n.user.user_id = :user_id AND n.skillSet.id = :skill_id AND n.manager_approval='pending'")
    int updateStatusManAdm(@Param("user_id") Long user_id, @Param("skill_id") Long skill_id, @Param("ack") String ack, @Param("modifiedAt")LocalDateTime modifiedAt);

    @Modifying
    @Query("UPDATE Nominations n SET n.admin_approval = :ack,n.modifiedAt=:modifiedAt WHERE n.user.user_id = :user_id AND n.skillSet.id = :skill_id AND n.admin_approval='pending'")
    int updateStatusAdm(@Param("user_id") Long user_id, @Param("skill_id") Long skill_id, @Param("ack") String ack,@Param("modifiedAt")LocalDateTime modifiedAt);

    // Corrected select query with proper syntax
    @Query("SELECT n FROM Nominations n WHERE n.user.user_id = :user_id AND n.skillSet.skillName = :skillName AND (n.admin_approval='approved' OR n.admin_approval='pending')")
    Optional<Nominations> getNominee(@Param("user_id") Long user_id, @Param("skillName") String skillName);

    @Query("SELECT n FROM Nominations n WHERE n.manager_approval='approved'")
    List<Nominations> getPendingNomineesforAdmin();
    @Query("SELECT n FROM Nominations n WHERE n.manager_approval='pending'")
    List<Nominations> getPendingNomineesforManager();
    @Query("SELECT n.skillSet.skillName FROM Nominations n WHERE n.admin_approval='approved' AND n.user.user_id=:user_id")
    List<String> getApprovedSkills(@Param("user_id") Long user_id);
    @Query("SELECT new com.etms.worldline.payload.response.NominatedSkillsStatusResponse(t.skillSet.skillName,t.manager_approval,t.admin_approval) FROM Nominations t WHERE t.user.user_id=:user_id")
    List<NominatedSkillsStatusResponse> getSkillsStatus(@Param("user_id") Long user_id);
}
