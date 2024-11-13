package com.etms.worldline.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "pro_id")
    @JsonBackReference
    private ProjectAssign projectAssign;

    private boolean isActive=true;
    private String roleName;
    private Long man_id;

    public UserProject(User user, ProjectAssign projectAssign) {
        this.user=user;
        this.projectAssign=projectAssign;
    }
    public UserProject(User user, ProjectAssign projectAssign, Long man_id, String roleName) {
        this.user=user;
        this.projectAssign=projectAssign;
        this.man_id=man_id;
        this.roleName=roleName;
    }
}
