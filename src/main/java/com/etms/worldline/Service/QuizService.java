package com.etms.worldline.Service;

import com.etms.worldline.Repository.*;
import com.etms.worldline.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuizforSkillRepository quizforSkillRepository;
    @Autowired
    private SelectedQuizRepository selectedQuizRepository;
    @Autowired
    private UserQuizRepository userQuizRepository;
    public List<Quiz> getallQuestions(){
        return quizRepository.findAll();
    }
    public void createquizQuestion(List<Quiz> quizzes){
        Quiz lastQuiz = quizRepository.findFirstByOrderByQuesIdDesc();
        Long lastQuizNo = 0L;
        if(lastQuiz!=null) {
            System.out.println(lastQuizNo);
            // Increment the last quiz_no
            lastQuizNo = lastQuiz.getQuizNo()+1;
//        quiz.setQuizNo(newQuizNo);
//        quizRepository.save(quiz);
        }
        for(Quiz quiz:quizzes){
            quiz.setQuizNo(lastQuizNo);
            quizRepository.save(quiz);
        }
    }

    @Transactional
    public void assignQuiz(String[] reqSkills,Long manangerId){
         List<User> employee=userRepository.findByRoleName("USER");
        Quiz lastQuiz = quizRepository.findFirstByOrderByQuesIdDesc();


        System.out.println(lastQuiz);
          Long lastQuizNo = lastQuiz.getQuizNo();
        System.out.println(lastQuizNo);
        // Increment the last quiz_no
//        Long newQuizNo = lastQuizNo + 1;
//         for(User emp:employee){
//             boolean hasSkills=true;
//             for(String skill:reqSkills){
//                 boolean hasSkill=false;
//                 for(SkillSet skil:emp.()){
//                     if(skil.getSkillName().equals(skill)){
//                         hasSkill=true;
//                         break;
//                     }
//                 }
//                 if(!hasSkill){
//                     hasSkills=false;
//                     break;
//                 }
//             }
//             if(hasSkills) {
//                 emp.setQuiz_status("Assigned");
//
//                 userRepository.save(emp);
//                 saveQuizAssignment(emp,lastQuizNo,0,manangerId);
//             }
//         }
//        List<Quiz> allQuizzes = quizRepository.findAll();
//
//// Filter out quizzes with quiz numbers greater than lastQuizNo
//        Long finalLastQuizNo = lastQuizNo;
//        List<Quiz> newQuizzes = allQuizzes.stream()
//                .filter(quiz -> quiz.getQuizNo() > finalLastQuizNo)
//                .collect(Collectors.toList());
//
//// Increment newQuizNo for each new quiz and save
//        for (Quiz quiz : newQuizzes) {
//            quiz.setQuizNo(++newQuizNo);
//            quizRepository.save(quiz);
//        }
        List<Quiz> unassignedQuizzes = quizRepository.findUnassignedQuizzes();
        System.out.println(unassignedQuizzes);
        for (Quiz quiz : unassignedQuizzes) {
                quiz.setAssignedstatus(true);

        }
//
//        // Update the quizzes in bulk
//        quizRepository.updateQuizzesAssignStatus(unassignedQuizzes);
    }
    private void saveQuizAssignment(User user, Long quizNo, int marksObtained,Long managerId) {
        UserQuizAssignment userQuizAssignment = new UserQuizAssignment();
        userQuizAssignment.setUser(user);
        userQuizAssignment.setQuizNo(quizNo);
        userQuizAssignment.setQuizStatus("Assigned");
        userQuizAssignment.setMarks(marksObtained);
        userQuizAssignment.setManagerId(managerId);
        userQuizRepository.save(userQuizAssignment);
    }
    public void updateMarks(Long user, Long quizNo, int marksObtained){
        UserQuizAssignment userQuizAssignment=userQuizRepository.findByUserAndQuizNo(user,quizNo);
        if(userQuizAssignment!=null){
            userQuizAssignment.setMarks(marksObtained);
            userQuizAssignment.setQuizStatus("Completed");
            userQuizRepository.save(userQuizAssignment);
        }
        else{
            System.out.println("No Quiz Assignment");
        }
    }
    public void stopQuiz(){
        List<User> employee=userRepository.findByRoleName("USER");
        for(User emp:employee){
            emp.setQuiz_status("Not_Assigned");
            userRepository.save(emp);

        }
    }
    public List<Quiz> getQuiz(Long quiznum){
        List<Quiz> quizzes=quizRepository.findByQuizNo(quiznum);
        return quizzes;
    }
    public List<Long> getQuizNo(Long userId){
        List<Long> quiznos=userQuizRepository.findQuizNoByUserId(userId);
        return quiznos;
    }
    public void createQuizforSkillques(QuizforSkill quizforSkill){
        quizforSkillRepository.save(quizforSkill);
    }
    public String getRandomQuestions(String skill) {
        List<QuizforSkill> quizforSkills=quizforSkillRepository.findRandomQuestions(skill,2);
        System.out.println(quizforSkills);
        String quizId = UUID.randomUUID().toString();

        for(QuizforSkill quiz1:quizforSkills){
            SelectedQuiz quiz=new SelectedQuiz();
            quiz.setQuestions(quiz1.getQues());
            List<String> selecChoicesCopy = new ArrayList<>(quiz1.getChoice());
            quiz.setSelecChoices(selecChoicesCopy);
            quiz.setAnsIndex(quiz1.getAnsIndex());
            quiz.setEncrId(quizId);
            selectedQuizRepository.save(quiz);
        }
        return "https://example.com/quiz/" + quizId;
    }
    public List<SelectedQuiz> getQuess(String encrId){
        List<SelectedQuiz> selectedQuizs=selectedQuizRepository.findByEncrId(encrId);
        return selectedQuizs;
    }
}
