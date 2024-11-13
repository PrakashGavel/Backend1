package com.etms.worldline.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
public class TrainingRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "training_id")
    @JsonManagedReference
    private Trainings trainings;
    @Column(length=10)
    private String status="approved";

    private LocalDateTime request_date;

    private LocalDateTime modifiedAt;

    @PrePersist
    protected void OnCreate(){
        request_date= LocalDateTime.now();
    }
}
