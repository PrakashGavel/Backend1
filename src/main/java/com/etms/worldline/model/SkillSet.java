package com.etms.worldline.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
public class SkillSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length=50)
    private String skillName;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "skill_set_id")
    @OrderColumn
    private List<SkillLevelwithUrls> skillLevels;
    public SkillSet(){}
    public SkillSet(String skillName) {
        this.skillName = skillName;
    }

}
