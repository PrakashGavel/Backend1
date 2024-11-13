package com.etms.worldline.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "user_skills")
public class UserSkills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "skill_id")
    private SkillSet skillSet;

    private int skill_level=0;
    @Column(name = "is_training", nullable = false, columnDefinition = "boolean default false")
    private boolean isTraining=false;
    @Column(name = "needskill_level",nullable = false, columnDefinition = "int4 default 0")
    private int needskill_level=0;
    public UserSkills(){
    }

    public UserSkills(User reguser, SkillSet skill) {
        this.user=reguser;
        this.skillSet=skill;
    }
    public UserSkills(User user, SkillSet skillSet, int skill_level){
        this.user=user;
        this.skillSet=skillSet;
        this.skill_level=skill_level;
    }
    public UserSkills(User user,SkillSet skillSet,int needskill_level,boolean isTraining){
        this.user=user;
        this.skillSet=skillSet;
        this.needskill_level=needskill_level;
        this.isTraining=true;
    }
    public Long getUserId() {
        return user != null ? user.getUser_id() : null;
    }
}
