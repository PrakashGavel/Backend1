package com.etms.worldline.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class QuizforSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qid;
    private String skill;
    private String ques;
    @ElementCollection
    private List<String> choice;
    private int ansIndex;
    public QuizforSkill(){}

}
