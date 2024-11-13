package com.etms.worldline.Service;

import com.etms.worldline.Repository.*;
import com.etms.worldline.model.*;
import com.etms.worldline.payload.request.TrainingRequest;
import com.etms.worldline.payload.request.nominationsRequest;
import com.etms.worldline.payload.response.*;
import org.apache.coyote.Response;
import org.apache.poi.ss.formula.functions.T;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainingService {
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NominationsRepository nominationsRepository;
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private TrainingsRepository trainingRepository;
    @Autowired
    private TrainingParticipantsRepository trainingParticipantsRepository;
    @Autowired
    private TrainingRequestsRepository trainingRequestsRepository;

    public ResponseEntity<?> applyNominations(nominationsRequest nom){
        try{

        User user=userRepository.findById(nom.getUser_id()).get();
        for(String skill:nom.getSkills()) {
            System.out.println(skill);
            if (nominationsRepository.getNominee(nom.getUser_id(), skill).isPresent()) {
                return ResponseEntity.ok(new MessageResponse(40000, "User is already approved for nominations"));
            }
            SkillSet skillSet = skillRepository.findBySkillName(skill).get();
            Nominations nominations = new Nominations();
            nominations.setUser(user);
            nominations.setSkillSet(skillSet);
            nominationsRepository.save(nominations);

        }
            return ResponseEntity.ok(new MessageResponse(20001, "Registration Successful"));
    }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse(50000,"Internal server error"));
        }
    }
    @Transactional
    public ResponseEntity<?> ackNominationsMan(Long user_id,String skill_name,boolean acknowledgement){
        try {
            Long id=skillRepository.findBySkillName(skill_name).get().getId();
            if(acknowledgement==false){
                nominationsRepository.updateStatusManAdm(user_id,id,"rejected", LocalDateTime.now());
                return ResponseEntity.ok(new MessageResponse(20001,"Rejection Successful from Manager"));
            }
            else if(acknowledgement==true){
                nominationsRepository.updateStatus(user_id,id,"approved",LocalDateTime.now());
                return ResponseEntity.ok(new MessageResponse(20001,"Approval Successful from Manager"));
            }
            return ResponseEntity.internalServerError().body(new MessageResponse(50000,"Internal Server error"));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(new MessageResponse(20001,"Internal Server error"));
        }
    }
    @Transactional
    public ResponseEntity<?> ackNominationsAdm(Long user_id,Long skill_id,boolean acknowledgement){
        try {
            if(acknowledgement==false){
                nominationsRepository.updateStatusAdm(user_id,skill_id,"rejected",LocalDateTime.now());
                return ResponseEntity.ok(new MessageResponse(20001,"Rejection Successful from Admin"));
            }
            if(acknowledgement==true){
                nominationsRepository.updateStatusAdm(user_id,skill_id,"approved",LocalDateTime.now());
                if(trainerRepository.findByUserId(user_id).isPresent()){
                    return ResponseEntity.ok(new MessageResponse(20001,"Approval Successful from Admin"));
                }
                else{
                    Trainer trainer=new Trainer();
                    User user=userRepository.findById(user_id).get();
                    trainer.setUser(user);
                    userRepository.save(user);
                    trainerRepository.save(trainer);
                    }
                return ResponseEntity.ok(new MessageResponse(20001,"Approval Successful from Admin"));
            }

            return ResponseEntity.internalServerError().body(new MessageResponse(50000,"Internal Server error"));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new MessageResponse(50000,"Internal Server error"));
        }
    }
    public List<Nominations> getPendingNomineesforManager(){
        return nominationsRepository.getPendingNomineesforManager();
    }
    public List<Nominations> getPendingNomineesforAdmin(){
        return nominationsRepository.getPendingNomineesforAdmin();
    }
    public List<String> getApprovedSkills(Long user_id){
        return nominationsRepository.getApprovedSkills(user_id);
    }
    public List<Trainings> getAllTrainings() {
        return trainingRepository.findAll();
    }

    public List<Trainings> saveTraining(List<TrainingRequest> trainingLists) {
        List<Trainings> tr_lists=new ArrayList<>();
        for(TrainingRequest training:trainingLists){
            Trainings trainings=new Trainings(training.getTraining_name(),training.getTraining_date(),training.getSlot_from(),training.getSlot_to(),training.getTopics());
            Trainer trainer=trainerRepository.findBytrainerId(training.getTrainer_id()).get();
            trainings.setTrainer(trainer);
            tr_lists.add(trainings);
        }

        return trainingRepository.saveAll(tr_lists);
    }
    public Trainings updateTraining(TrainingRequest trainings){
        Trainings training=trainingRepository.getById(trainings.getId());
        training.setTraining_name(trainings.getTraining_name());
        training.setTraining_date(trainings.getTraining_date());
        training.setSlot_from(trainings.getSlot_from());
        training.setSlot_to(trainings.getSlot_to());
        training.setTopics(trainings.getTopics());
        training.setModifiedAt(LocalDateTime.now());
        Trainer trainer=trainerRepository.findBytrainerId(trainings.getTrainer_id()).get();
        training.setTrainer(trainer);
        return trainingRepository.save(training);
    }

    public Optional<Trainings> getTrainingById(Long id) {
        return trainingRepository.findById(id);
    }
    public ResponseEntity<?> saveTopicsforTraining(TrainingRequest trainings){
        try {
            Trainings training=trainingRepository.getById(trainings.getId());
            training.setTopics(trainings.getTopics());
            training.setModifiedAt(LocalDateTime.now());
            trainingRepository.save(training);
            return ResponseEntity.ok(new ErrorResponse(20001,"Topics Added successfully"));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new ErrorResponse(50000,"Internal Server error"));
        }
    }
    public FetchTrainingsResponse getPastTrainings(Long user_id,int page,int size){
        FetchTrainingsResponse fetchTrainingsResponse=new FetchTrainingsResponse();
        try{
            Pageable pageable = PageRequest.of(page, size);
            LocalDate currentDate=LocalDate.now();
            LocalDate oneMonthago=currentDate.minusMonths(1);
            fetchTrainingsResponse.setResponse_code(20001);
            fetchTrainingsResponse.setMessage("Fetch records Successfully");
            Page<TrainingsResponse> pastTrainings=trainingParticipantsRepository.getPastTrainings(user_id,oneMonthago,currentDate,pageable);
            for(TrainingsResponse trainingsResponse:pastTrainings){
                trainingsResponse.setTopics(trainingRepository.getById(trainingsResponse.getId()).getTopics());
            }
            fetchTrainingsResponse.setTrainings(pastTrainings);
            return fetchTrainingsResponse;
        }
        catch (Exception e){
            e.printStackTrace();
            fetchTrainingsResponse.setResponse_code(50000);
            fetchTrainingsResponse.setMessage("Internal Server error");
            return fetchTrainingsResponse;
        }
    }
    public FetchTrainerTrainingResponse getPastTrainingsforTrainer(Long user_id,int page,int size){
        FetchTrainerTrainingResponse fetchTrainingsResponse=new FetchTrainerTrainingResponse();
        try{
            Pageable pageable = PageRequest.of(page, size);
            LocalDate currentDate=LocalDate.now();
            LocalDate oneMonthago=currentDate.minusMonths(1);
            fetchTrainingsResponse.setResponse_code(20001);
            fetchTrainingsResponse.setMessage("Fetch records Successfully");
            Page<TrainerTrainingsResponse> pastTrainings=trainingRepository.getPastTrainingssforTrainer(oneMonthago,currentDate,user_id,pageable);
            for(TrainerTrainingsResponse trainingsResponse:pastTrainings){
                trainingsResponse.setTopics(trainingRepository.getById(trainingsResponse.getId()).getTopics());
                trainingsResponse.setParticipants(trainingParticipantsRepository.getParticipants(user_id,trainingsResponse.getId()));
            }
            fetchTrainingsResponse.setTrainings(pastTrainings);
            return fetchTrainingsResponse;
        }
        catch (Exception e){
            e.printStackTrace();
            fetchTrainingsResponse.setResponse_code(50000);
            fetchTrainingsResponse.setMessage("Internal Server error");
            return fetchTrainingsResponse;
        }
    }
    public boolean isTrainer(Long user_id){
        return trainerRepository.findByUserId(user_id).isPresent();
    }
    public FetchTrainingsResponse getUpcomingTrainings(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        LocalDate currentDate=LocalDate.now();
        LocalDate oneMonthago=currentDate.plusMonths(1);
        System.out.println(oneMonthago);
        FetchTrainingsResponse fetchTrainingsResponse=new FetchTrainingsResponse();
        try{
            fetchTrainingsResponse.setResponse_code(20001);
            fetchTrainingsResponse.setMessage("Fetch records Successfully");

            Page<TrainingsResponse> upcomingTrainings=trainingRepository.getUpcomingTrainingss(oneMonthago,currentDate,pageable);
            System.out.println(upcomingTrainings.getContent());
            for(TrainingsResponse trainingsResponse:upcomingTrainings){
                trainingsResponse.setTopics(trainingRepository.getById(trainingsResponse.getId()).getTopics());
            }
            fetchTrainingsResponse.setTrainings(upcomingTrainings);
            return fetchTrainingsResponse;
        }
        catch (Exception e){
            e.printStackTrace();
            fetchTrainingsResponse.setResponse_code(50000);
            fetchTrainingsResponse.setMessage("Internal Server error");
            return fetchTrainingsResponse;
        }
    }
    public FetchTrainerTrainingResponse getUpcomingTrainingsforTrainer(Long trainer_id,int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        LocalDate currentDate=LocalDate.now();
        LocalDate oneMonthago=currentDate.plusMonths(1);
        System.out.println(oneMonthago);
        FetchTrainerTrainingResponse fetchTrainingsResponse=new FetchTrainerTrainingResponse();
        try{
            fetchTrainingsResponse.setResponse_code(20001);
            fetchTrainingsResponse.setMessage("Fetch records Successfully");

            Page<TrainerTrainingsResponse> upcomingTrainings=trainingRepository.getUpcomingTrainingssforTrainer(oneMonthago,currentDate,trainer_id,pageable);
            System.out.println(upcomingTrainings.getContent());
            for(TrainerTrainingsResponse trainingsResponse:upcomingTrainings){
                trainingsResponse.setTopics(trainingRepository.getById(trainingsResponse.getId()).getTopics());
                trainingsResponse.setParticipants(trainingParticipantsRepository.getParticipants(trainer_id,trainingsResponse.getId()));
            }
            fetchTrainingsResponse.setTrainings(upcomingTrainings);
            return fetchTrainingsResponse;
        }
        catch (Exception e){
            e.printStackTrace();
            fetchTrainingsResponse.setResponse_code(50000);
            fetchTrainingsResponse.setMessage("Internal Server error");
            return fetchTrainingsResponse;
        }
    }
    public ResponseEntity<?> userTrainingReg(Long user_id,Long training_id){
        try{
            User user=userRepository.findById(user_id).get();
            Trainings trainings=trainingRepository.getById(training_id);
            if(trainings.getTrainer().getUser().getUser_id()==user_id || trainingRequestsRepository.getUserTrainingRequestsDetails(user_id,training_id).isPresent()){
                return ResponseEntity.ok(new MessageResponse(20001,"User can't be enrolled to the training"));
            }
            TrainingRequests trainingRequests=new TrainingRequests();
            trainingRequests.setUser(user);
            trainingRequests.setTrainings(trainings);
            trainingRequestsRepository.save(trainingRequests);
            if(trainingRequests.getStatus().equals("approved")){
                TrainingParticipants trainingParticipants=new TrainingParticipants();
                trainingParticipants.setUser(user);
                trainingParticipants.setTrainings(trainings);
                trainingParticipantsRepository.save(trainingParticipants);
            }
            return ResponseEntity.ok(new MessageResponse(20001,"Request sent successfully"));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new MessageResponse(50000,"Internal Server error"));
        }
    }
    public List<TrainingStatus> getTrainingStatusofUser(Long user_id){
        List<TrainingStatus> trainingStatusResponse=new ArrayList<>();
        try {
          return trainingRequestsRepository.getTrainingStatusforUser(user_id);
        }
        catch (Exception e){
            e.printStackTrace();
            return trainingStatusResponse;
        }
    }
    public ResponseEntity<?> isTrainerCheck(Long user_id){
        try{
            boolean is_present=trainerRepository.findByUserId(user_id).isPresent();
            return ResponseEntity.ok(new TrainerConfirmation(is_present));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new ErrorResponse(50000,"Internal server error"));
        }
    }
    public ResponseEntity<?> getNominatedSkillsstatus(Long user_id){
        try{
            List<NominatedSkillsStatusResponse> nominatedSkillsStatus=nominationsRepository.getSkillsStatus(user_id);
            Map<String, NominatedSkillsStatusResponse> skillMap = nominatedSkillsStatus.stream()
                    .collect(Collectors.toMap(
                            NominatedSkillsStatusResponse::getSkill_name,
                            nomination -> nomination,
                            (existing, replacement) -> {
                                if ("pending".equalsIgnoreCase(replacement.getAdmin_approval()) || "approved".equalsIgnoreCase(replacement.getAdmin_approval())) {
                                    System.out.println(existing);
                                    System.out.println(replacement);
                                    return replacement;
                                }
                             return existing;
                            }
                    ));
            List<NominatedSkillsStatusResponse> finalNominations = new ArrayList<>(skillMap.values());
            return ResponseEntity.ok(new NominatedSkillsResponse(20000,"Fetch records successfully",finalNominations));
        }
        catch (Exception e){
              return ResponseEntity.internalServerError().body(new ErrorResponse(50000,"Internal server error"));
        }
    }
//    public Page<TrainingsResponse> getUpcomingTrainings(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<TrainingsResponse> userdetails=trainingRepository.getUpcomingTrainingss(pageable);
//        return userdetails;
//
//    }
}
