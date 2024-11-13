package com.etms.worldline.Repository;

import com.etms.worldline.model.Quiz;
import com.etms.worldline.model.User;
import com.etms.worldline.model.UserQuizAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuizRepository extends JpaRepository<UserQuizAssignment,Long>{
    @Query("SELECT uqa FROM UserQuizAssignment uqa WHERE uqa.user.user_id = :userId AND uqa.quizNo = :quizNo")
    UserQuizAssignment findByUserAndQuizNo(@Param("userId") Long userId, @Param("quizNo") Long quizNo);
    @Query("SELECT uqa.quizNo FROM UserQuizAssignment uqa WHERE uqa.user.user_id=?1")
    List<Long> findQuizNoByUserId(Long userId);
}
