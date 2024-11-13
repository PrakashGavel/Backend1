package com.etms.worldline.Service;


import com.etms.worldline.Repository.*;
import com.etms.worldline.model.*;
import com.etms.worldline.payload.request.NomineeReq;
import com.etms.worldline.payload.request.nominationsRequest;
import com.etms.worldline.payload.request.skilldetails;
import com.etms.worldline.payload.response.MessageResponse;
import com.etms.worldline.payload.response.Userdetails;
import com.etms.worldline.payload.response.lackingskilldetails;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserSkillsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private UserSkillsRepository userSkillsRepository;
    @Autowired
    private SkillHubRepository skillHubRepository;

    public List<UserSkills> showempskilllevel(Long id) {
        List<UserSkills> user = userSkillsRepository.findbyuserid(id);
        return user;
    }

    public List<lackingskilldetails> getemplackingskills(Long id) {
        List<lackingskilldetails> lackingskilldetailsList = userSkillsRepository.findtheallemplackingskillsbyid(id);
        System.out.println(lackingskilldetailsList);
        for (lackingskilldetails ls : lackingskilldetailsList) {
            int need_level = ls.getNeedskill_level();
            ls.setUrls(skillRepository.findBySkillName(ls.getSkillName()).get().getSkillLevels().stream().filter(matchedLevel -> matchedLevel.getLevel() == need_level).findFirst().get().getUrls());
        }
        return lackingskilldetailsList;
    }

    @Transactional
    public void updateUserskills(Long user_id, List<skilldetails> skilldetailsList) {
        for (skilldetails sd : skilldetailsList) {
            System.out.println(sd);
            User user = userRepository.findById(user_id).get();
            UserSkills userSkills = new UserSkills(user, skillRepository.findBySkillName(sd.getSkillName()).get(), sd.getSkill_level());
            userSkillsRepository.save(userSkills);
//            int updated = userSkillsRepository.updateSkillLevel(user_id, skillRepository.findBySkillName(sd.getSkillName()).get().getId(), sd.getSkill_level());
//            if (updated == 0) {
//              User user=userRepository.findById(user_id).get();
//              SkillSet skillSet=skillRepository.findBySkillName(sd.getSkillName()).get();
//              UserSkills userSkills=new UserSkills(user,skillSet, sd.getSkill_level());
//              userSkillsRepository.save(userSkills);
//            }
//            System.out.println(updated);
        }

    }

    public List<skilldetails> getempskill(Long user_id) {
        List<skilldetails> skills = userSkillsRepository.findtheempskillsbyid(user_id);
        return skills;
    }

    @Transactional
    public ResponseEntity<?> updatetrskillandlevel(Long id, List<skilldetails> skilldetailsList) {
        try {
            User user = userRepository.findById(id).get();
            for (skilldetails sd : skilldetailsList) {
                int updated = userSkillsRepository.updateTrainingandSetskilllevel(id, skillRepository.findBySkillName(sd.getSkillName()).get().getId(), sd.getSkill_level());
                if (updated == 0) {

                    SkillSet skillSet = skillRepository.findBySkillName(sd.getSkillName()).get();
                    UserSkills userSkills = new UserSkills(user, skillSet, sd.getSkill_level(), true);
                    userSkillsRepository.save(userSkills);
                }
            }
            return ResponseEntity.ok(new MessageResponse("Employee training skills and its level is updated successfully"));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new MessageResponse(50000,"Something is wrong, Try again Later!"));}
        }


    public void assignTraining(Long user_id) {
        User user = userRepository.findById(user_id).get();
        user.setTraining(true);
        userRepository.save(user);
    }

    public List<skilldetails> getallempskills(Long id) {
        return userSkillsRepository.findtheallempskillsbyid(id);
    }

    public List<Userdetails> getAllLackingUsers() {
        List<Userdetails> users = userRepository.getUserslackinginskills();
        for (Userdetails user : users) {
            user.setLackingskilldetails(userSkillsRepository.findtheallemplackingskillsbyid(user.getUser_id()));
        }
        return users;
    }

    public ResponseEntity<?> applySkillhubReqs(nominationsRequest nomineeReq) {
        try {
            User user = userRepository.findById(nomineeReq.getUser_id()).get();
            for (String skill : nomineeReq.getSkills()) {
                SkillSet skillSet = skillRepository.findBySkillName(skill).get();
                SkillHub requests = new SkillHub();
                requests.setUser(user);
                requests.setSkillSet(skillSet);
                skillHubRepository.save(requests);
            }
            return ResponseEntity.ok(new MessageResponse(20001,"Registration Successful"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new MessageResponse(50000,"Internal Server error"));
        }
    }
}
