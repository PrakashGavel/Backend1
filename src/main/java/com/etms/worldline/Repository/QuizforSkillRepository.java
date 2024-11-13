package com.etms.worldline.Repository;

import com.etms.worldline.model.Quiz;
import com.etms.worldline.model.QuizforSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizforSkillRepository extends JpaRepository<QuizforSkill,Long> {
    @Query(value = "SELECT * FROM Quizfor_Skill WHERE skill=:skill ORDER BY RANDOM() LIMIT :count",nativeQuery = true)
    List<QuizforSkill> findRandomQuestions(@Param("skill") String skill,@Param("count") int count);
}
