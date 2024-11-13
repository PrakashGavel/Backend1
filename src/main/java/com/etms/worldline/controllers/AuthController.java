package com.etms.worldline.controllers;

import com.etms.worldline.Repository.*;
import com.etms.worldline.Service.*;
import com.etms.worldline.model.*;
import com.etms.worldline.payload.request.*;
import com.etms.worldline.payload.response.*;
import com.etms.worldline.security.CustomUserDetailsService;
import com.etms.worldline.security.JwtTokenProvider;
import com.etms.worldline.security.UserPrincipal;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.coyote.Response;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.tomcat.util.modeler.OperationInfo;
import org.apache.xml.serializer.EncodingInfo;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import com.etms.worldline.payload.response.LoginSuccessfullResponse;

import static java.sql.Types.NUMERIC;
import static javax.management.openmbean.SimpleType.STRING;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@Component
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private ExcelHandlingService excelHandlingService;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSkillsService userSkillsService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private QuizService quizService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserSkillsRepository userSkillsRepository;
    @Autowired
    private QuizforSkillRepository quizforSkillRepository;
    @Autowired
    private ProjectSkillsRepository projectSkillsRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EntityMasterService entityMasterService;
    @Autowired
    private EntityMasterRepository entityMasterRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UserProjectRepository userProjectRepository;
@PostMapping(value="/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> registerUser(@RequestPart("signupRequest") SignupRequest signupRequest,
                                      @RequestParam(value = "profilepic",required = false) MultipartFile file) {
    return userService.registerUser(signupRequest, file);
}

@PostMapping("/login")
public ResponseEntity<?> authUser(@RequestBody LoginRequest user) throws MalformedJwtException {
   return userService.login(user);
}



    @PostMapping("/manager-register")
    public ResponseEntity<?> registerManager(@RequestBody SignupRequest signupRequest) {
        return userService.registerManager(signupRequest);
}

    @PostMapping("/admin-register")
    public ResponseEntity<?> registerAdmin(@RequestBody SignupRequest signupRequest) {
        return userService.registerAdmin(signupRequest);
    }

    @PostMapping("/master-register")
    public ResponseEntity<?> registerMasterAdmin(@RequestBody SignupRequest signupRequest) {
       return userService.registerMaster(signupRequest);
    }

    @GetMapping("/getskills")
    public List<SkillSet> getSkills() {
        return userService.getSkills();
    }

    @GetMapping("/getskillsid")
    public SkillSet getSkillId(@RequestParam @PathVariable String skillName) {
        return userService.getSkills(skillName);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
       return excelHandlingService.uploadExcel(file);

    }
    @GetMapping("/getAllEntitites")
    public List<EntityMaster> getAlltheentityMaster(){
        return entityMasterService.getAllentities();
    }

    @PostMapping("/entityMasterUpload")
    public ResponseEntity<?> handleFileUploadinEntity(@RequestParam("file") MultipartFile file) {
       return excelHandlingService.entityExcelUpload(file);
    }


    @PutMapping("/updatetrainskillandlevel")
    public ResponseEntity<?> updateskillandlevel(@RequestParam @PathVariable Long id,@RequestBody List<skilldetails> skilldetails){
           return userSkillsService.updatetrskillandlevel(id,skilldetails);
    }
@GetMapping("/getUserwithskills")
public List<Userdetails> userdetailsList(){
    return userService.getUserdetailswithSkills();
}

    @GetMapping("/getProjectdetails")
    public List<Projectdetails> projectdetailsList(){
        return userService.getProjectdetailswithSkills();
    }

    @GetMapping("/gblCount")
    public List<Integer> gblCount(@RequestParam @PathVariable String gbl){
        return userService.findthgblinfo(gbl);
    }

    @GetMapping("/getthegblmanagers")
    public List<User> getthegblmanager(@RequestParam @PathVariable String gbl){
        return userService.gettheempdetman(gbl);
    }
    @GetMapping("/getthegblprojects")
    public List<Projectdetails> getthegblproj(@RequestParam @PathVariable String gbl){
        return userService.getProjbyGbl(gbl);
    }

    @GetMapping("/getAlllackingusers")
    public List<Userdetails> getthelackusers(){
        return userSkillsService.getAllLackingUsers();
    }
   @GetMapping("/profile/{filename}")
   public ResponseEntity<Resource> getProfilepic(@PathVariable String filename) throws MalformedURLException {
        Path filepath= Paths.get("C:\\Users\\w136637\\OneDrive - Worldline SA\\Bureau\\Backend-Main\\wgs_internalapp\\user-photos").resolve(filename).normalize();
        Resource resource=new UrlResource(filepath.toUri());
        if(!resource.exists()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
   }

}


