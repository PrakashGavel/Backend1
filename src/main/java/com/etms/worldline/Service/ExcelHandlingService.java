package com.etms.worldline.Service;

import com.etms.worldline.Repository.*;
import com.etms.worldline.model.*;
import com.etms.worldline.payload.response.MessageResponse;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExcelHandlingService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSkillsRepository userSkillsRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private UserProjectRepository userProjectRepository;
    @Autowired
    private EntityMasterRepository entityMasterRepository;

    public ResponseEntity<?> uploadExcel(MultipartFile file){
        if (file.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("Please select a file"));
        }
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();



            if (rowIterator.hasNext()) {
                rowIterator.next();
            }
            StringBuilder failedUsernames=new StringBuilder();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                boolean need_to_allocate=false;
                String username = getCellValueAsString(row.getCell(0));
                if(userService.existsByUsername(username)){
                    System.out.println("exist");
                    failedUsernames.append(username).append(", ");
                    continue;
                }
                String last_name = getCellValueAsString(row.getCell(1));
                String gender = getCellValueAsString(row.getCell(2));
                String dob1 = getExcelDateAsString(row.getCell(3));
                String role_name = getCellValueAsString(row.getCell(4));
                String email = getCellValueAsString(row.getCell(5));
                String gbl = getCellValueAsString(row.getCell(6));
                String entity = getCellValueAsString(row.getCell(7));
                String assign_project = getCellValueAsString(row.getCell(8));

                String location = getCellValueAsString(row.getCell(10));
                String das_id=getCellValueAsString(row.getCell(11));
                String project_allocation=getCellValueAsString(row.getCell(12));
                String report_manager=getCellValueAsString(row.getCell(13));
                if(project_allocation!=null){
                    need_to_allocate=true;
                }
                Set<String> strRoles = null;
                Set<Role> roles = new HashSet<>();

                if (role_name.equals("USER")) {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
                if(role_name.equals("MANAGER")){
                    Role userRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
                Date dob = null;
                try {
                    dob = dob1.isEmpty() ? null : new SimpleDateFormat("yyyy-MM-dd").parse(dob1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String newP=dob1.replace("-","");
                System.out.println(newP);
                User user = new User(username, email,encoder.encode(newP), last_name, location, dob, entity,das_id, gender,gbl,true,role_name);
                user.setAssign_project(assign_project);
                user.setRoles(roles);
                User saveUser=userRepository.save(user);
                String skillsString;
                try {
                    skillsString = row.getCell(9).getStringCellValue();
                }catch (NullPointerException e){
                    System.out.println("existsk");
                    failedUsernames.append(username).append(", ");
                    continue;
                }

                Set<UserSkills> skills = new HashSet<>();

                for (String skillName : skillsString.split(",")) {
                    System.out.println(skillName);
                    Optional<SkillSet> optionalSkill = skillRepository.findBySkillName(skillName.trim());
                    SkillSet skill = optionalSkill.orElseGet(() -> new SkillSet(skillName.trim()));
                    UserSkills userSkills = new UserSkills(saveUser, skill);
                    userSkillsRepository.save(userSkills);
                }
                if(need_to_allocate) {
                    try {
                        ProjectAssign projectAssign = projectRepository.findByProName(project_allocation).get();
                        Long man_id =role_name.equals("USER")?userRepository.findByUsername(report_manager).get().getUser_id():0;
                        UserProject userProject = new UserProject(saveUser,projectAssign,man_id,role_name);
                        userProjectRepository.save(userProject);
                    }
                    catch(Exception e){
                        System.out.println("existpk");
                        failedUsernames.append(username).append(',');
                        continue;
                    }

                }

            }

            if(failedUsernames.length()>0){
                System.out.println(failedUsernames);
                failedUsernames.setLength(failedUsernames.length()-2);
                return ResponseEntity.badRequest().body(new MessageResponse("The following users are not registered Successfully "+failedUsernames));
            }
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
                    return String.valueOf(cell.getNumericCellValue());
                default:
                    return "";
            }
        } else {
            return "";
        }
    }

    private String getExcelDateAsString(Cell cell) {
        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                return new SimpleDateFormat("yyyy-MM-dd").format(date);
            } else {
                // If it's not a date, handle as needed
                return String.valueOf(cell.getNumericCellValue());
            }
        } else {
            return "";
        }
    }
    public ResponseEntity<?> entityExcelUpload(MultipartFile file){
        if (file.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("Please select a file"));
        }

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            List<EntityMaster> entityMastersList=new ArrayList<>();
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String entityName = getCellValueAsString(row.getCell(0));
                EntityMaster entityMaster=new EntityMaster(entityName);
                entityMastersList.add(entityMaster);

            }

            entityMasterRepository.saveAll(entityMastersList);

            return ResponseEntity.ok(new MessageResponse("File uploaded successfully."));
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Error processing the file."));
        }
    }
}
