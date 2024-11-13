package com.etms.worldline.Repository;

import com.etms.worldline.model.Trainings;
import com.etms.worldline.payload.response.TrainerTrainingsResponse;
import com.etms.worldline.payload.response.TrainingsResponse;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingsRepository extends JpaRepository<Trainings,Long> {

    @Query("SELECT new com.etms.worldline.payload.response.TrainingsResponse(t.id,t.training_name,t.training_date,t.slot_from,t.slot_to,t.trainer.user.username) from Trainings t where t.training_date<=:oneMonthago AND t.training_date>=:currentDate")
    Page<TrainingsResponse> getUpcomingTrainingss(@Param("oneMonthago") LocalDate oneMonthago, @Param("currentDate") LocalDate currentDate, Pageable pageable);
    @Query("SELECT new com.etms.worldline.payload.response.TrainerTrainingsResponse(t.id,t.training_name,t.training_date,t.slot_from,t.slot_to) from Trainings t where t.training_date<=:oneMonthago AND t.training_date>=:currentDate AND t.trainer.user.user_id=:trainer_id")
    Page<TrainerTrainingsResponse> getUpcomingTrainingssforTrainer(@Param("oneMonthago") LocalDate oneMonthago, @Param("currentDate") LocalDate currentDate, @Param("trainer_id") Long trainer_id, Pageable pageable);
    @Query("SELECT new com.etms.worldline.payload.response.TrainerTrainingsResponse(t.id,t.training_name,t.training_date,t.slot_from,t.slot_to) from Trainings t where t.training_date>=:oneMonthago AND t.training_date<=:currentDate AND t.trainer.user.user_id=:trainer_id")
    Page<TrainerTrainingsResponse> getPastTrainingssforTrainer(@Param("oneMonthago") LocalDate oneMonthago, @Param("currentDate") LocalDate currentDate, @Param("trainer_id") Long trainer_id, Pageable pageable);

}
