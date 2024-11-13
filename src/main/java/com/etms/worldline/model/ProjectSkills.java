package com.etms.worldline.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project_skills")
public class ProjectSkills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pro_id")
    @JsonManagedReference
    private ProjectAssign project;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "skill_id")
    private SkillSet skillSet;

    private int projectskill_level=0;
    public ProjectSkills(ProjectAssign pro, SkillSet skillSet){
        this.project=pro;
        this.skillSet=skillSet;
    }

    public ProjectSkills(ProjectAssign pro, SkillSet skillSet, int skillLevel) {
        this.project=pro;
        this.skillSet=skillSet;
        this.projectskill_level=skillLevel;
    }
}
