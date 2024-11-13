package com.etms.worldline.controllers;

import com.etms.worldline.Repository.TrainerRepository;
import com.etms.worldline.Repository.UserRepository;
import com.etms.worldline.Service.QuizService;
import com.etms.worldline.Service.TrainingService;
import com.etms.worldline.Service.UserService;
import com.etms.worldline.Service.UserSkillsService;
import com.etms.worldline.model.*;
import com.etms.worldline.payload.request.NomineeReq;
import com.etms.worldline.payload.request.TrainingRequest;
import com.etms.worldline.payload.request.nominationsRequest;
import com.etms.worldline.payload.request.skilldetails;
import com.etms.worldline.payload.response.*;
import com.etms.worldline.security.JwtAuthFilter;
import com.etms.worldline.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private UserSkillsService userSkillsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @GetMapping("/profile")
    public ResponseEntity<?> sample(){
        return ResponseEntity.ok(new MessageResponse(20001,"Hello User!"));
    }
    @GetMapping("getempid")
    public User getempidbyname(@RequestParam @PathVariable String name){
        return userService.getempid(name);
    }
    @GetMapping("getempidbyid")
    public User getempidbyid(@RequestParam @PathVariable Long id){
        return userService.getU(id);
    }

    @PostMapping("/complete")
    public ResponseEntity<?> completeQuiz(@RequestParam @PathVariable Long user_id,@RequestParam @PathVariable Long marks){
        return userService.quizCompletion(user_id);
    }
    @GetMapping("/getQuiz")
    public List<Quiz> getQuiz(@RequestParam @PathVariable Long quiznum){
        return quizService.getQuiz(quiznum);
    }
    @GetMapping("/getquiznos")
    public List<Long> getquiznos(@RequestParam @PathVariable Long userId){
        return quizService.getQuizNo(userId);
    }
    @GetMapping("/getUsersProjects")
    public List<UserProjectDetails> projectDetails(@RequestParam @PathVariable Long id){
        return userService.getTheUsersProjects(id);
    }
    @GetMapping("/getallempskills")
    public List<skilldetails> getallskillsdetails(HttpServletRequest request){
        Long id=jwtTokenProvider.getUserIdFromToken(jwtAuthFilter.getJwtRequest(request));
        return userSkillsService.getallempskills(id);
    }
    @GetMapping("/getemplackingskills")
    public List<lackingskilldetails> getemplackskills(@RequestParam @PathVariable Long id){
        return userSkillsService.getemplackingskills(id);
    }
    @GetMapping("getempskill")
    public List<skilldetails> getempSkills(@RequestParam @PathVariable Long id){
        return userSkillsService.getempskill(id);
    }
    @GetMapping("/skill")
    public ResponseEntity<?> getskill(){
     return userService.fetchSkills();
    }
    @PostMapping("/trainer/nominee")
    public ResponseEntity<?> applyNominations(HttpServletRequest request,@RequestBody nominationsRequest nom){
           nom.setUser_id(jwtTokenProvider.getUserIdFromToken(jwtAuthFilter.getJwtRequest(request)));
           return trainingService.applyNominations(nom);
    }
    @GetMapping("/getAppSkills")
    public List<String> getApprovedSkills(HttpServletRequest request){
        Long user_id=jwtTokenProvider.getUserIdFromToken(jwtAuthFilter.getJwtRequest(request));
        return trainingService.getApprovedSkills(user_id);
    }
    @PutMapping("/nomineeAck")
    public ResponseEntity<?> updateAck(HttpServletRequest request,@RequestBody NomineeReq nomineeReq){
        return trainingService.ackNominationsMan(nomineeReq.getUser_id(),nomineeReq.getSkill_name(),nomineeReq.isAcknowledgement());
    }
    @PutMapping("/nomineeAckAdm")
    public ResponseEntity<?> updateAckAdm(@RequestBody NomineeReq nomineeReq){
        return trainingService.ackNominationsAdm(nomineeReq.getUser_id(),nomineeReq.getSkill_id(),nomineeReq.isAcknowledgement());
    }

    @GetMapping("/getTraining")
    public Trainings getTrainingById(@RequestParam @PathVariable Long id) {
        return trainingService.getTrainingById(id)
                .orElseThrow(() -> new RuntimeException("Training not found with id: " + id));
    }

    @GetMapping("/getAlltrainings")
    public List<Trainings> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @PostMapping("/registerTraining")
    public List<Trainings> createTraining(@RequestBody List<TrainingRequest> training) {
        return trainingService.saveTraining(training);
    }
    @PutMapping("/updateTraining")
    public Trainings updateTraining(@RequestBody TrainingRequest trainingRequest){
        return trainingService.updateTraining(trainingRequest);
    }
    @PutMapping("/saveTopicsforTraining")
    public ResponseEntity<?> saveTopics(@RequestBody TrainingRequest trainings){
        return trainingService.saveTopicsforTraining(trainings);
    }
    @GetMapping("/training/history")
    public FetchTrainingsResponse getPastTrainings(HttpServletRequest request,@RequestParam @PathVariable int page,@RequestParam @PathVariable int size){
        Long user_id=jwtTokenProvider.getUserIdFromToken(jwtAuthFilter.getJwtRequest(request));
        return trainingService.getPastTrainings(user_id,page,size);
    }
    @GetMapping("/trainer/history")
    public FetchTrainerTrainingResponse getPastTrainingsforTrainer(HttpServletRequest request,@RequestParam @PathVariable int page,@RequestParam @PathVariable int size){
            Long user_id=jwtTokenProvider.getUserIdFromToken(jwtAuthFilter.getJwtRequest(request));
            return trainingService.getPastTrainingsforTrainer(user_id,page,size);

    }
    @GetMapping("/training/upcoming")
    public FetchTrainingsResponse getUpcomingTrainings(@RequestParam @PathVariable int page,@RequestParam @PathVariable int size){
        return trainingService.getUpcomingTrainings(page,size);
    }
    @GetMapping("/trainer/upcoming")
    public FetchTrainerTrainingResponse getUpcomingTrainingsfortrainer(HttpServletRequest request,@RequestParam @PathVariable int page,@RequestParam @PathVariable int size){
        Long trainer_id=jwtTokenProvider.getUserIdFromToken(jwtAuthFilter.getJwtRequest(request));
        return trainingService.getUpcomingTrainingsforTrainer(trainer_id,page,size);
    }
    @PostMapping("/training/register")
    public ResponseEntity<?> userReg(HttpServletRequest request,@RequestBody NomineeReq userTrainReg){
        userTrainReg.setUser_id(jwtTokenProvider.getUserIdFromToken(jwtAuthFilter.getJwtRequest(request)));
      return trainingService.userTrainingReg(userTrainReg.getUser_id(),userTrainReg.getTraining_id());
    }
    @GetMapping("/training/status")
    public ResponseEntity<?> getUserTrainingstatus(HttpServletRequest request){
            Long user_id=jwtTokenProvider.getUserIdFromToken(jwtAuthFilter.getJwtRequest(request));
            return ResponseEntity.ok(new TrainingStatusResponse(20001,"Fetch Records Successfully",trainingService.getTrainingStatusofUser(user_id)));
    }
    @GetMapping("/trainer/check")
    public ResponseEntity<?> getTrainerconfirmation(HttpServletRequest request){
            Long user_id=jwtTokenProvider.getUserIdFromToken(jwtAuthFilter.getJwtRequest(request));
            return trainingService.isTrainerCheck(user_id);
    }
    @PostMapping("/skillhub/nominate")
    public ResponseEntity<?> userRequestsforSkillhub(HttpServletRequest request,@RequestBody nominationsRequest nomineeReq){
        nomineeReq.setUser_id(jwtTokenProvider.getUserIdFromToken(jwtAuthFilter.getJwtRequest(request)));
        return userSkillsService.applySkillhubReqs(nomineeReq);
    }
    @GetMapping("/nomination/status")
    public ResponseEntity<?> getNominationStatus(HttpServletRequest request){
        Long user_id=jwtTokenProvider.getUserIdFromToken(jwtAuthFilter.getJwtRequest(request));
        return trainingService.getNominatedSkillsstatus(user_id);
    }
}
