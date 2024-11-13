package com.etms.worldline.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "skill_hub_requests")
public class SkillHub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private SkillSet skillSet;


    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
    @Column(name = "manager_approval", length=10,nullable = false)
    private String manager_approval="approved";

    @Column(name = "admin_approval", length=10,nullable = false)
    private String admin_approval="approved";

    @PrePersist
    protected void OnCreate(){
        createdAt= LocalDateTime.now();
    }
}
