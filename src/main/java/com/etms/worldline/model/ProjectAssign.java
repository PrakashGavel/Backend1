package com.etms.worldline.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Table(name = "projects_assign")
public class ProjectAssign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pro_id;

    private String proName;

    private String gbl;

    private String proDesc;

    private boolean pro_status;

    private LocalDateTime creationDate;
    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JsonBackReference
    private Set<ProjectSkills> projectSkills=new HashSet<>();

//    @ManyToMany
//    @JoinTable(name = "projectskills",
//            joinColumns = @JoinColumn(name = "pro_id"),
//            inverseJoinColumns = @JoinColumn(name = "skill_id"))
//    private Set<SkillSet> projectskillsentity = new HashSet<>();
     @ElementCollection
     private List<String> documentUrls;
     private String prodocPath;

     private Long man_id;
    @OneToMany(mappedBy = "projectAssign", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<UserProject> userProjects = new ArrayList<>();
    public ProjectAssign(String proName, String gbl){
        this.proName=proName;
        this.gbl=gbl;
    }
    public ProjectAssign(){

    }
    public ProjectAssign(String proName, String gbl, String proDesc){
        this.proName=proName;
        this.gbl=gbl;
        this.proDesc=proDesc;
    }

    public ProjectAssign(String proName, String gbl, String proDesc,Long man_id) {
        this.proName=proName;
        this.gbl=gbl;
        this.proDesc=proDesc;
        this.man_id=man_id;
    }
    public ProjectAssign(String proName, String gbl, String proDesc,Long man_id, List<String> prodocPath) {
        this.proName=proName;
        this.gbl=gbl;
        this.proDesc=proDesc;
        this.man_id=man_id;
        this.documentUrls=prodocPath;
    }
    @PrePersist
    protected void onCreate(){
        creationDate=LocalDateTime.now();
    }
}
