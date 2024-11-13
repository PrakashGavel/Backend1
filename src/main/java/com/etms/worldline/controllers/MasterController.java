package com.etms.worldline.controllers;

import com.etms.worldline.Repository.EntityMasterRepository;
import com.etms.worldline.Repository.ProjectRepository;
import com.etms.worldline.Repository.ProjectSkillsRepository;
import com.etms.worldline.Repository.SkillRepository;
import com.etms.worldline.Service.EntityMasterService;
import com.etms.worldline.Service.UserService;
import com.etms.worldline.model.*;
import com.etms.worldline.payload.request.projectsRequest;
import com.etms.worldline.payload.request.projectskilldetails;
import com.etms.worldline.payload.response.DashboardDetails;
import com.etms.worldline.payload.response.MessageResponse;
import com.etms.worldline.payload.response.Projectdetails;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
@RestController
@CrossOrigin
@RequestMapping("/api/master")
public class MasterController {
    @Autowired
    private UserService userService;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private ProjectSkillsRepository projectSkillsRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EntityMasterService entityMasterService;
    @Autowired
    private EntityMasterRepository entityMasterRepository;
    @GetMapping("/sample")
    public String getSample(){
        return "Hello";
    }
    @GetMapping("/allemployees")
    public List<User> getEmployees(){
        System.out.println("Hello");
        return userService.getEmployees();
    }
    @CrossOrigin
    @PutMapping("/verify")
    public ResponseEntity<?> updateUser(@RequestParam @PathVariable Long id){
        try{
            userService.updateVerification(id, true);
            return ResponseEntity.ok(new MessageResponse("Approval Successful"));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Something is wrong, Try again Later"));
        }
    }
    @DeleteMapping("/cancel")
    public ResponseEntity<?> deleteUser(@RequestParam @PathVariable Long id){
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new MessageResponse("Approval Cancelation Successful"));
        }
        catch (Exception e){
            return  ResponseEntity.ok(new MessageResponse("Something is wrong, try again later"));
        }
    }
    @GetMapping("/getskills")
    public List<SkillSet> getSkills(){ return userService.getSkills();
    }
    @GetMapping("/getskillsid")
    public SkillSet getSkillId(@RequestParam @PathVariable String skillName){
        return userService.getSkills(skillName);
    }
    @PostMapping("/saveskill")
    public ResponseEntity<?> saveSkill(@RequestBody SkillSet skill){
        if(skill==null){
            return ResponseEntity.ok().body(new MessageResponse("There is no skill is selected"));
        }
        try {
            userService.saveSkills(skill);
            return ResponseEntity.ok(new MessageResponse(("Skill added successfully")));
        }
       catch (Exception e){
        return  ResponseEntity.badRequest().body(new MessageResponse("Something is wrong, try again later"));
        }
    }
   @PutMapping("/updateSkill")
    public ResponseEntity<?> updateSkill(@RequestParam @PathVariable Long id,@RequestBody SkillSet skill){
       if(skill==null){
           return ResponseEntity.ok().body(new MessageResponse("There is no skill is selected"));
       }
       try {
           userService.updateSkills(id, skill);
           return ResponseEntity.ok(new MessageResponse(("Skill updated successfully")));
       }
    catch (Exception e){
       return ResponseEntity.badRequest().body(new MessageResponse("Something is wrong, try again later"));}
   }
   @DeleteMapping("/deleteSkill")
    public ResponseEntity<?> deleteSkill(@RequestParam @PathVariable Long id){
        try{
        userService.deleteSkill(id);
       return ResponseEntity.ok(new MessageResponse(("Skill deleted successfully")));
   }catch (Exception e){
            return ResponseEntity.ok(new MessageResponse("Something is wrong, try again later!"));
        }
    }

    @DeleteMapping("/deleteProject")
    public ResponseEntity<?> deleteProject(@RequestParam @PathVariable Long id){
        try {
            userService.deleteProject(id);
            return ResponseEntity.ok(new MessageResponse("Project deleted successfully"));
        }catch (Exception e){
            return ResponseEntity.ok(new MessageResponse("Something is wrong, try again later!"));
        }
    }
   @PutMapping("/updateProject")
    public ResponseEntity<?> updateProject(@RequestParam @PathVariable Long id,@RequestBody Projectdetails projectskilldetails){
        if(projectskilldetails==null){
            return ResponseEntity.badRequest().body(new MessageResponse("There is no project details is given"));
        }
        try {
            userService.updateProject(id, projectskilldetails);
            return ResponseEntity.ok(new MessageResponse("Project updated successfully"));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Something is wrong, try again later"));
        }
    }

    @GetMapping("/getprojects")
    public List<ProjectAssign> getProjects(){
        return userService.getProjects();
    }
    @GetMapping("/getprojectid")
    public Projectdetails  getProjectId(@RequestParam @PathVariable String pro_name){
        return userService.getProjects(pro_name);
    }
    @GetMapping("/getProdetbyid")
    public ProjectAssign getProdddetById(@RequestParam @PathVariable Long pro_id){
        return userService.getProjectById(pro_id);
    }
    @GetMapping("/getProjectdetails")
    public List<Projectdetails> projectdetailsList(){
        return userService.getProjectdetailswithSkills();
    }
//    @GetMapping("/getUserSKills")
//    public Set<UserSkills> getSkill(@RequestParam @PathVariable Long id){
//        return userService.updateempskilllevel(id);
//    }
//@PostMapping(value = "/saveProject", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//public ResponseEntity<?> saveProject(@RequestPart("project") projectsRequest pro,@RequestPart("files") List<MultipartFile> files) throws IOException {    // Handle project saving
//    ProjectAssign saveePro = new ProjectAssign(pro.getProName(), pro.getGbl());    List<String> docsUrls = cloudinaryService.saveDocUrls(files, pro.getProName());    saveePro.setDocumentUrls(docsUrls);    ProjectAssign project = userService.saveProject(saveePro);   for (projectskilldetails ps : pro.getProjectskilldetails()) {        SkillSet skillSet = skillRepository.findBySkillName(ps.getSkill_name()).get();        ProjectSkills projectSkills = new ProjectSkills(project, skillSet, ps.getSkillLevel());        projectSkillsRepository.save(projectSkills);    }    return ResponseEntity.ok(new MessageResponse("Project added successfully"));}

    @GetMapping("/getprodettById")
    public Projectdetails getProdetById(@RequestParam @PathVariable Long pro_id){
        return userService.findtheProinfbyId(pro_id);
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
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getProDocs(@PathVariable String filename) throws MalformedURLException {
        Path filepath= Paths.get("C:\\Users\\w136637\\OneDrive - Worldline SA\\Bureau\\worldline\\user-projects\\").resolve(filename).normalize();
        Resource resource=new UrlResource(filepath.toUri());
        if(!resource.exists()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(resource);
    }
    @GetMapping("/getAllEntitites")
    public List<EntityMaster> getAlltheentityMaster(){
        return entityMasterService.getAllentities();
    }
    @PostMapping("/createEntity")
    public EntityMaster createtheEntity(@RequestBody EntityMaster entityMaster){
        return entityMasterRepository.save(entityMaster);
    }
    @PutMapping("/updateEntity")
    public EntityMaster updateTheEntity(@RequestParam @PathVariable Long en_id,@RequestBody EntityMaster entityMaster){
        return entityMasterService.updateEntityMaster(en_id,entityMaster);
    }
    @DeleteMapping("/deleteEntity")
    public ResponseEntity<?> deleteTheEntity(@RequestParam @PathVariable Long en_id){
        entityMasterService.deleteEntityMaster(en_id);
        return ResponseEntity.ok(new MessageResponse("Entity is deleted successfully"));
    }
    @PostMapping("/skillMasterUpload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("Please select a file"));
        }

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Skip the header row if it exists
            List<SkillSet> skillSetList=new ArrayList<>();
            if (rowIterator.hasNext()) {
                rowIterator.next(); // Skip the header row
            }
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
//                String username = row.getCell(0)!=null?row.getCell(0).getStringCellValue():"";
//                String last_name = row.getCell(1)!=null?row.getCell(1).getStringCellValue():"";
//                String gender = row.getCell(2)!=null?row.getCell(2).getStringCellValue():"";
//                String dob1 = row.getCell(3)!=null?row.getCell(3).getStringCellValue():"";
//                String role_name = row.getCell(4)!=null?row.getCell(4).getStringCellValue():"";
//                String email = row.getCell(5)!=null?row.getCell(5).getStringCellValue():"";
//                String emp_group = row.getCell(6)!=null?row.getCell(6).getStringCellValue():"";
//                String college_name = row.getCell(7)!=null?row.getCell(7).getStringCellValue():"";
//                String assign_project = row.getCell(8)!=null?row.getCell(8).getStringCellValue():"";
//                String college_location=row.getCell(10)!=null?row.getCell(10).getStringCellValue():"";
                String skillName = getCellValueAsString(row.getCell(0));
                SkillSet skillSet=new SkillSet(skillName);
                // Parse skills and associate with the user

                skillSetList.add(skillSet);

//                users.add(user);
            }

            // Save users to the database
            skillRepository.saveAll(skillSetList);

            return ResponseEntity.ok(new MessageResponse("File uploaded successfully."));
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Error processing the file."));
        }

    }
    @PostMapping("/entityMasterUpload")
    public ResponseEntity<?> handleFileUploadinEntity(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("Please select a file"));
        }

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Skip the header row if it exists
            List<EntityMaster> entityMastersList=new ArrayList<>();
            if (rowIterator.hasNext()) {
                rowIterator.next(); // Skip the header row
            }
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
//                String username = row.getCell(0)!=null?row.getCell(0).getStringCellValue():"";
//                String last_name = row.getCell(1)!=null?row.getCell(1).getStringCellValue():"";
//                String gender = row.getCell(2)!=null?row.getCell(2).getStringCellValue():"";
//                String dob1 = row.getCell(3)!=null?row.getCell(3).getStringCellValue():"";
//                String role_name = row.getCell(4)!=null?row.getCell(4).getStringCellValue():"";
//                String email = row.getCell(5)!=null?row.getCell(5).getStringCellValue():"";
//                String emp_group = row.getCell(6)!=null?row.getCell(6).getStringCellValue():"";
//                String college_name = row.getCell(7)!=null?row.getCell(7).getStringCellValue():"";
//                String assign_project = row.getCell(8)!=null?row.getCell(8).getStringCellValue():"";
//                String college_location=row.getCell(10)!=null?row.getCell(10).getStringCellValue():"";
                String entityName = getCellValueAsString(row.getCell(0));
                EntityMaster entityMaster=new EntityMaster(entityName);
                // Parse skills and associate with the user

                entityMastersList.add(entityMaster);

//                users.add(user);
            }

            // Save users to the database
            entityMasterRepository.saveAll(entityMastersList);

            return ResponseEntity.ok(new MessageResponse("File uploaded successfully."));
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Error processing the file."));
        }

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
    @PostMapping("/uploadprojectMaster")
    public ResponseEntity<?> handleFileUploadProject(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("Please select a file"));
        }
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            List<User> users = new ArrayList<>();

            // Skip the header row if it exists

            if (rowIterator.hasNext()) {
                rowIterator.next(); // Skip the header row
            }
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
//                String username = row.getCell(0)!=null?row.getCell(0).getStringCellValue():"";
//                String last_name = row.getCell(1)!=null?row.getCell(1).getStringCellValue():"";
//                String gender = row.getCell(2)!=null?row.getCell(2).getStringCellValue():"";
//                String dob1 = row.getCell(3)!=null?row.getCell(3).getStringCellValue():"";
//                String role_name = row.getCell(4)!=null?row.getCell(4).getStringCellValue():"";
//                String email = row.getCell(5)!=null?row.getCell(5).getStringCellValue():"";
//                String emp_group = row.getCell(6)!=null?row.getCell(6).getStringCellValue():"";
//                String college_name = row.getCell(7)!=null?row.getCell(7).getStringCellValue():"";
//                String assign_project = row.getCell(8)!=null?row.getCell(8).getStringCellValue():"";
//                String college_location=row.getCell(10)!=null?row.getCell(10).getStringCellValue():"";
                String proname = getCellValueAsString(row.getCell(0));
                String gbl = getCellValueAsString(row.getCell(1));
                String proDesc = getCellValueAsString(row.getCell(2));

                // Parse skills and associate with the user
                String skillsString = row.getCell(3).getStringCellValue();
                ProjectAssign pro=new ProjectAssign(proname,gbl,proDesc);
                ProjectAssign savePro=projectRepository.save(pro);
                for (String skillName : skillsString.split(",")) {
                    String[] skillNames=skillName.trim().split("-");
                    String SkillNamegiven=skillNames[0];
                    int skillLevel=skillNames.length>1?Integer.parseInt(skillNames[1]):0;
                    Optional<SkillSet> optionalSkill = skillRepository.findBySkillName(SkillNamegiven.trim());
                    SkillSet skill = optionalSkill.orElseGet(() -> new SkillSet(SkillNamegiven.trim()));
                    ProjectSkills proSkills = new ProjectSkills(savePro, skill,skillLevel);
                    projectSkillsRepository.save(proSkills);
                }


//                users.add(user);
            }

            // Save users to the database
//            userRepository.saveAll(users);

            return ResponseEntity.ok(new MessageResponse("File uploaded successfully."));
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Error processing the file."));
        }

    }
    @GetMapping("/dashboardDetails")
    public DashboardDetails getSamp(){
        return userService.getTheDashDetails();
    }
}
