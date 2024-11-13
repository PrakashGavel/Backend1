package com.etms.worldline.controllers;


import com.etms.worldline.Repository.ProjectSkillsRepository;
import com.etms.worldline.Repository.SkillRepository;
import com.etms.worldline.Repository.UserSkillsRepository;
import com.etms.worldline.Service.QuizService;
import com.etms.worldline.Service.TrainingService;
import com.etms.worldline.Service.UserService;
import com.etms.worldline.Service.UserSkillsService;
import com.etms.worldline.model.*;
import com.etms.worldline.payload.request.*;
import com.etms.worldline.payload.response.*;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/manager")
public class ManagerController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private UserSkillsService userSkillsService;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private ProjectSkillsRepository projectSkillsRepository;
    @Autowired
    private SkillRepository skillRepository;
   @GetMapping("/allemployees")
   public List<User> getEmployees(){
       return userService.getEmployees();
   }
   @PutMapping("/assign")
    public ResponseEntity<?> assignProject(@RequestParam @PathVariable Long user_id,@RequestParam @PathVariable Long pro_id,@RequestParam @PathVariable Long man_Id,@RequestParam @PathVariable boolean isTraining){
       userService.assignProject(user_id,pro_id,man_Id,isTraining);
//       userService.assignSkills(user_id,skillIds.getEmp_group());
       return ResponseEntity.ok(new MessageResponse("Project,skills assigned successfully"));
   }
    @PostMapping(value = "/saveProject",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> saveProject(@RequestPart("project") projectsRequest pro, @RequestPart("files")List<MultipartFile> files) throws IOException {
        List<String> docsurls=new ArrayList<>();
        System.out.println(pro);
        String uploadDir="C:\\Users\\w136637\\OneDrive - Worldline SA\\Bureau\\worldline\\user-projects\\";
        for(MultipartFile multipartFile:files){
            String proPath=uploadDir+multipartFile.getOriginalFilename();
            File profile=new File(proPath);
            multipartFile.transferTo(profile);
            docsurls.add(multipartFile.getOriginalFilename());
        }


        ProjectAssign saveePro=new ProjectAssign(pro.getProName(),pro.getGbl(),pro.getProDesc(),pro.getMan_id());
        saveePro.setDocumentUrls(docsurls);
        ProjectAssign project= userService.saveProject(saveePro);
        for(projectskilldetails ps:pro.getProjectskilldetails()){
            SkillSet skillSet=skillRepository.findBySkillName(ps.getSkill_name()).get();
            ProjectSkills projectSkills=new ProjectSkills(project,skillSet,ps.getSkillLevel());
            projectSkillsRepository.save(projectSkills);
        }
//       Set<Long> proskillids=pro.getProskillids();
//       userService.saveProject(saveePro);
//       if(pro.getProskillids()==null || pro.getProskillids().isEmpty()){
//           userService.saveProject(saveePro);
//           return ResponseEntity.ok(new MessageResponse("Project added successfully"));
//       }
//       Set<SkillSet> proskill=skillRepository.findAllById(proskillids).stream().collect(Collectors.toSet());
//       for(SkillSet skillSet:proskill){
//           ProjectSkills projectSkills=new ProjectSkills(saveePro,skillSet);
//           projectSkillsRepository.save(projectSkills);
//
//       }

        return ResponseEntity.ok(new MessageResponse("Project added successfully"));
    }
   @GetMapping("/getprojects")
    public List<ProjectAssign> getProjects(){
       return userService.getProjects();
   }
   @GetMapping("/getprojectid")
    public Projectdetails  getProjectId(@RequestParam @PathVariable String pro_name){
       return userService.getProjects(pro_name);
   }
      @GetMapping("/getskills")
    public List<SkillSet> getSkills(){ return userService.getSkills();
    }
    @GetMapping("/getskillsid")
    public SkillSet getSkillId(@RequestParam @PathVariable String skillName){
        return userService.getSkills(skillName);
    }
    @GetMapping("getempskill")
    public List<skilldetails> getempSkills(@RequestParam @PathVariable Long id){
       return userSkillsService.getempskill(id);
   }
//    @PutMapping("/updatetrainskills")
//    public ResponseEntity<?> updateTrainskill(@RequestParam @PathVariable Long id, @RequestBody skillId skillid){
//        userService.updatetrainSkill(id,skillid.getSkillIds());
//        return ResponseEntity.ok(new MessageResponse("Employee need to train skills updated successfully"));
//    }
    @GetMapping("/getProdetbyid")
    public ProjectAssign getProdetById(@RequestParam @PathVariable Long pro_id){
        return userService.getProjectById(pro_id);
    }
//    @PutMapping("/assign-skills")
//  public ResponseEntity<?> assignSkills(@RequestParam @PathVariable Long user_id, @RequestBody skillId skillIds){
//      userService.assignSkills(user_id, skillIds.getSkillIds());
//       return ResponseEntity.ok(new MessageResponse("Skills assigned successfully"));
//    }
//    @PutMapping("/updateempskills")
//    public User updateempskill(@RequestParam @PathVariable Long user_id,@RequestBody skillId skillid){
//       return userService.updateemployeeSkill(user_id,skillid.getSkillIds());
//    }
    @PutMapping("/updateempskills")
    public ResponseEntity<?> updateempskill(@RequestParam @PathVariable Long user_id,@RequestBody List<skilldetails> skilldetails){
       userService.updateUserskills(user_id,skilldetails);
       return ResponseEntity.ok(new MessageResponse("Employee skills updated successfully"));
   }
    @GetMapping("/getQuestions")
    public List<Quiz> getQues(){
       return quizService.getallQuestions();
    }
    @PostMapping("/createQuestion")
    public ResponseEntity<?> createQues(@RequestBody List<Quiz> quiz){
       quizService.createquizQuestion(quiz);
       return ResponseEntity.ok(new MessageResponse("Question added Successfully"));
    }
    @PostMapping("/assignquiz")
    public ResponseEntity<?> assignQuiz(@RequestBody skillId skills,@RequestParam @PathVariable Long manager_id){
       quizService.createquizQuestion(skills.getQuizzes());
       quizService.assignQuiz(skills.getSkill(),manager_id);
       return ResponseEntity.ok(new MessageResponse("Quiz assigned for all employees successfully"));
    }
    @GetMapping("/getQuiz")
    public List<Quiz> getQuiz(@RequestParam @PathVariable Long quiznum){
       return quizService.getQuiz(quiznum);
    }
    @PostMapping("/updateMarks")
    public ResponseEntity<?> updateMarks(@RequestBody UpdateMarksRequest updateMarksRequest){
       quizService.updateMarks(updateMarksRequest.getUser_id(),updateMarksRequest.getQuizNo(),updateMarksRequest.getMarks());
       return ResponseEntity.ok(new MessageResponse("Marks updated Successfully"));

    }
    @GetMapping("getempid")
    public User getempidbyname(@RequestParam @PathVariable String name){
        return userService.getempid(name);
    }
    @GetMapping("/getquiznos")
    public List<Long> getquiznos(@RequestParam @PathVariable Long userId){
       return quizService.getQuizNo(userId);
    }
    @PostMapping("/createQforskill")
    public ResponseEntity<?> createQues(@RequestBody QuizforSkill quizforSkill){
        quizService.createQuizforSkillques(quizforSkill);
        return ResponseEntity.ok(new MessageResponse("Quizforskill created successfully"));
    }
    @CrossOrigin
    @PostMapping("/getUrl")
    public ResponseEntity<?> getUrl(@RequestBody skillId quizforSkill){
        return  ResponseEntity.ok(new MessageResponse(quizService.getRandomQuestions(quizforSkill.getSkills1())));
    }
    @GetMapping("/getselectQuiz")
    public List<SelectedQuiz> getQuesss(@RequestParam @PathVariable String encrId){
        return quizService.getQuess(encrId);
    }
    @PutMapping("/updateSkillandSkilllev")
    public ResponseEntity<?> updSkiandSkilllev(@RequestParam @PathVariable Long id,@RequestBody List<skilldetails> skilldetails) throws IOException {
        userSkillsService.updateUserskills(id,skilldetails);
        return ResponseEntity.ok(new MessageResponse("Skills and Skill level updated successfully"));
    }
//    @GetMapping("/getempskills")
//    List<Object[]> skills(@RequestParam @PathVariable Long id){
//        return userService.getempskill(id);
//    }

    @PutMapping("/updatetrainskillandlevel")
    public ResponseEntity<?> updateskillandlevel(@RequestParam @PathVariable Long id,@RequestBody List<skilldetails> skilldetails){
        userSkillsService.updatetrskillandlevel(id,skilldetails);
        return ResponseEntity.ok(new MessageResponse("Employee training skills and its level is updated successfully"));
    }
    @GetMapping("/getUserwithskills")
    public List<Userdetails> userdetailsList(){
        return userService.getUserdetailswithSkills();
    }

    @GetMapping("/getProjectdetails")
    public List<Projectdetails> projectdetailsList(){
        return userService.getProjectdetailswithSkills();
    }
    @GetMapping("/getUsersunderManager")
    public List<User> getUsersunderMan(@RequestParam @PathVariable Long manager_id){
       return userService.getTheUsersUnderManager(manager_id);
    }
    @PutMapping("/updateProject")
    public ResponseEntity<?> updateProject(@RequestParam @PathVariable Long id,@RequestBody Projectdetails projectskilldetails){
        userService.updateProject(id,projectskilldetails);
        return ResponseEntity.ok(new MessageResponse("Project updated successfully"));
    }

    @DeleteMapping("/deleteProject")
    public ResponseEntity<?> deleteProject(@RequestParam @PathVariable Long id){
        userService.deleteProject(id);
        return ResponseEntity.ok(new MessageResponse("Project deleted successfully"));
    }

    @GetMapping("/getProjectsunderManager")
    public List<Projectdetails> getProjectsunderMan(@RequestParam @PathVariable Long id){
       return userService.getProjectdetailswithSkillsunderManager(id);
    }
    @GetMapping("/getprodettById")
    public Projectdetails getProdetttById(@RequestParam @PathVariable Long pro_id){
        return userService.findtheProinfbyId(pro_id);
    }
    @GetMapping("/getAlllackingusers")
    public List<Userdetails> getthelackusers(){
       return userSkillsService.getAllLackingUsers();
    }
    @PutMapping("/assignTrain")
    public ResponseEntity<?> assignTraining(@RequestParam @PathVariable Long user_id){
       userSkillsService.assignTraining(user_id);
       return ResponseEntity.ok(new MessageResponse("Employee is assigned to the Training Successfully!"));
    }
    @GetMapping("/dashboardDetails")
    public DashboardDetails getSamp(@RequestParam @PathVariable String gbl){
        return userService.getTheDashDetailsbyGbl(gbl);
    }
    @GetMapping("/getthegblprojects")
    public List<Projectdetails> getthegblproj(@RequestParam @PathVariable String gbl){
        return userService.getProjbyGbl(gbl);
    }
    private String getCellValueAsString(Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    // Handle numeric value (e.g., convert it to a string)
                    return String.valueOf(cell.getNumericCellValue());
                default:
                    // Handle other cell types if necessary
                    return "";
            }
        } else {
            return "";
        }
    }
    @PutMapping("/nomineeAck")
    public ResponseEntity<?> updateAck(@RequestBody NomineeReq nomineeReq){
        return trainingService.ackNominationsMan(nomineeReq.getUser_id(),nomineeReq.getSkill_name(),nomineeReq.isAcknowledgement());
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getProDocs(@PathVariable String filename) throws MalformedURLException {
        Path filepath= Paths.get("C:\\Users\\w136637\\OneDrive - Worldline SA\\Bureau\\worldline\\user-projects\\").resolve(filename).normalize();
        Resource resource=new UrlResource(filepath.toUri());
        if(!resource.exists()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(resource);
    }
    @GetMapping("/getPendingNominee")
    public List<Nominations> getNominees(){
        return trainingService.getPendingNomineesforManager();
    }
}
