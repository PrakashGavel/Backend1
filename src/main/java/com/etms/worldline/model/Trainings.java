package com.etms.worldline.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class Trainings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "training_name", length = 150, nullable = false)
    private String training_name;

    @Column(name = "training_date", nullable = false)
    private LocalDate training_date;

    @Column(name = "slot_from", nullable = false)
    private LocalDateTime slot_from;

    @Column(name = "slot_to", nullable = false)
    private LocalDateTime slot_to;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    @JsonManagedReference
    private Trainer trainer;
    @OneToMany(mappedBy = "trainings", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<TrainingRequests> TraininginUserRequests = new ArrayList<>();
    @OneToMany(mappedBy = "trainings", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<TrainingParticipants> trainingsinParticipants = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "training_topics", joinColumns = @JoinColumn(name = "training_id"))
    @Column(name = "topic",length=50,nullable = false)
    private List<String> topics;


    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @PrePersist
    protected void OnCreate(){
        createdAt= LocalDateTime.now();
    }


    public Trainings(String trainingName, LocalDate trainingDate, LocalDateTime slotFrom, LocalDateTime slotTo, List<String> topics) {
        this.training_name=trainingName;
        this.training_date=trainingDate;
        this.slot_from=slotFrom;
        this.slot_to=slotTo;
        this.topics=topics;
    }
}
