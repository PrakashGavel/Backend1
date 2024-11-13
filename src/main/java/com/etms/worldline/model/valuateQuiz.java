package com.etms.worldline.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class valuateQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vid;
    private String encId;
    private String email;
    private Integer marks;
}
