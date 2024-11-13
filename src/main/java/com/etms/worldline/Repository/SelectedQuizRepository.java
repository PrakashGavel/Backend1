package com.etms.worldline.Repository;

import com.etms.worldline.model.QuizforSkill;
import com.etms.worldline.model.SelectedQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectedQuizRepository extends JpaRepository<SelectedQuiz,Long> {
    List<SelectedQuiz> findByEncrId(String encrId);
}
