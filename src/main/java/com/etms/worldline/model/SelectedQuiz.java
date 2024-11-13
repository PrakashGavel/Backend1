package com.etms.worldline.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SelectedQuiz {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String encrId;

    private String questions;
    @ElementCollection
    private List<String> selecChoices;

    private int ansIndex;
}
