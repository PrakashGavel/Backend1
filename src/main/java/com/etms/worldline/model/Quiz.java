package com.etms.worldline.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@EqualsAndHashCode(exclude = "nameAttributeInThisClassWithOneToMany")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quesId;

    private String question;
    private Long quizNo;
    @ElementCollection
    private List<String> choices;
    private int ansIndex;
    private Boolean assignedstatus=false;
    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SELECT)
    @JsonBackReference
    private Set<UserQuizAssignment> userAssignments=new HashSet<>();
}
