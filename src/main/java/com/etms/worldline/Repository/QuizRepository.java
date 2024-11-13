package com.etms.worldline.Repository;

import com.etms.worldline.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {
    Quiz findFirstByOrderByQuesIdDesc();
    List<Quiz> findByQuizNoGreaterThan(Long quizNo);
    List<Quiz> findByQuizNo(Long quizNo);

    @Query("SELECT q FROM Quiz q WHERE q.assignedstatus = false")
    List<Quiz> findUnassignedQuizzes();
    @Modifying
    @Query("UPDATE Quiz q SET q.quizNo = q.quizNo + 1, q.assignedstatus = true WHERE q IN :quizzes")
    void updateQuizzesAssignStatus(@Param("quizzes") List<Quiz> quizzes);

}
