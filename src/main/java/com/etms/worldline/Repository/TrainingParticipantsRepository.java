package com.etms.worldline.Repository;

import com.etms.worldline.model.TrainingParticipants;
import com.etms.worldline.payload.response.TrainerTrainingsResponse;
import com.etms.worldline.payload.response.TrainingsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingParticipantsRepository extends JpaRepository<TrainingParticipants,Long> {
    @Query("SELECT new com.etms.worldline.payload.response.TrainingsResponse(t.trainings.id,t.trainings.training_name,t.trainings.training_date,t.trainings.slot_from,t.trainings.slot_to,t.trainings.trainer.user.username) from TrainingParticipants t where t.user.user_id=:user_id AND t.trainings.training_date>=:oneMonthago AND t.trainings.training_date<=:currentDate")
    Page<TrainingsResponse> getPastTrainings(@Param("user_id") Long user_id,@Param("oneMonthago") LocalDate oneMonthago, @Param("currentDate") LocalDate currentDate, Pageable pageable);
    @Query("SELECT COUNT(t) FROM TrainingParticipants t WHERE t.trainings.trainer.user.user_id=:user_id AND t.trainings.id=:id")
    int getParticipants(@Param("user_id") Long user_id,@Param("id") Long training_id);


}
