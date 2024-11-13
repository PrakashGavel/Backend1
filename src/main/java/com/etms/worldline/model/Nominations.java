package com.etms.worldline.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "trainer_nominations")
public class Nominations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JoinColumn(name="skill_id")
    @JsonManagedReference
    private SkillSet skillSet;

    @Column(length = 10,nullable = false)
    private String manager_approval="pending";

    @Column(length = 10,nullable = false)
    private String admin_approval="pending";
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @PrePersist
    protected void OnCreate(){
        createdAt= LocalDateTime.now();
    }

}
