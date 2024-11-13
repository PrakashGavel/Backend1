package com.etms.worldline.Repository;

import com.etms.worldline.model.Trainer;
import com.etms.worldline.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer,Long> {
    @Query("SELECT t from Trainer t WHERE t.user.user_id=:id")
    Optional<Trainer> findByUserId(@Param("id")Long id);
    @Query("SELECT t from Trainer t WHERE t.id=:id")
    Optional<Trainer> findBytrainerId(@Param("id")Long id);
}
