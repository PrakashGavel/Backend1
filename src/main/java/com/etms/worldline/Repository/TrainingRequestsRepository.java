package com.etms.worldline.Repository;

import com.etms.worldline.model.TrainingRequests;
import com.etms.worldline.payload.response.TrainingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRequestsRepository extends JpaRepository<TrainingRequests,Long> {
    @Query("SELECT new com.etms.worldline.payload.response.TrainingStatus(t.trainings.id,t.status) from TrainingRequests t where t.user.user_id=:user_id")
    List<TrainingStatus> getTrainingStatusforUser(@Param("user_id") Long user_id);
    @Query("SELECT t from TrainingRequests t where t.user.user_id=:user_id AND t.trainings.id=:training_id")
    Optional<TrainingRequests> getUserTrainingRequestsDetails(@Param("user_id") Long user_id,@Param("training_id") Long tranining_id);
}
