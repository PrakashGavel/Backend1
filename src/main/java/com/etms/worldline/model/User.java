package com.etms.worldline.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "employee")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long user_id;

    @Column(length = 50,nullable = false)
    private String username;

    @Column(length = 50,nullable = false)
    private String last_name;

    private String gender;

    private Date dob;



    @Column(length = 10,nullable = false)
    private String das_id;

    @Column(length = 50)
    private String manager_name;

    @Column(length = 50,nullable = false)
    private String entity;

    private String roleName;
    @Column(length=50)
    private String email;

    @Column(length = 256)
    private String user_fcm_token;




    private String propicPath;

    @Column(length = 50,nullable = false)
    private String gbl;


    private String assign_project="No";

//    private String assign_status="No";

    private boolean verify=false;

    @Column(length = 20,nullable = false)
    private String location;

    private LocalDateTime lastLogin;

    private LocalDateTime joiningDate;
    private String quiz_status="Not_Assigned";

    private String password;

    @Column(name = "is_training", nullable = false, columnDefinition = "boolean default false")
    private boolean isTraining=false;

    private Long manager_id;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<UserQuizAssignment> quizAssignments=new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles=new HashSet<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<UserSkills> userSkills=new HashSet<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Nominations> userNominations=new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<UserProject> userProjects = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<TrainingRequests> userTrainingRequests = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<TrainingParticipants> trainingParticipants = new ArrayList<>();
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    private Trainer trainer;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    private List<SkillHub> skillHubs;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "employee_skills",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "skill_id"))
//    private Set<SkillSet> employeeskills = new HashSet<>();
//    @ManyToMany
//    @JoinTable(name = "employeetraining_skills",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "skill_id"))
//    private Set<SkillSet> employeetrainingskills = new HashSet<>();
//    @ManyToMany
//    @JoinTable(name = "user_skills",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "skill_id"))
//    private Set<SkillSet> skills = new HashSet<>();
    public User(String username, String email, String password, String last_name, String location, Date dob, String entity,String das_id, String gender,String gbl) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.last_name = last_name;
        this.gender = gender;
        this.dob = dob;
        this.location = location;
        this.das_id=das_id;
        this.entity=entity;
        this.gbl=gbl;

    }
    public User(String username, String email, String password, String last_name,String location,Date dob,String entity,String da_id, String gender,String gbl,boolean verify,String role_name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.last_name = last_name;
        this.gender = gender;
        this.dob = dob;
        this.location = location;
        this.entity=entity;
        this.das_id=da_id;
        this.gbl=gbl;
        this.verify=verify;
        this.roleName=role_name;
    }
    public User(){}
    public User(String username,String email,String password,String department){
        this.username=username;
        this.email=email;
        this.password=password;
        this.gbl=department;
    }
    public User(String username,String password,String email){
        this.username=username;
        this.password=password;
        this.email=email;
    }
    public String getUser_fcm_token() {
        return user_fcm_token;
    }

    public void setUser_fcm_token(String user_fcm_token) {
        this.user_fcm_token = user_fcm_token;
    }
    @PrePersist
    protected void OnCreate(){
        joiningDate= LocalDateTime.now();
    }
}
