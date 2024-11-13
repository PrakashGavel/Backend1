package com.etms.worldline.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class SkillLevelwithUrls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int level;

    @ElementCollection
    @CollectionTable(name = "skill_level_urls", joinColumns = @JoinColumn(name = "skill_level_id"))
    @Column(name = "url")
    @OrderColumn
    private List<String> urls;
}
