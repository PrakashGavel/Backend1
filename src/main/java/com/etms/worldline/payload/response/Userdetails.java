package com.etms.worldline.payload.response;

import com.etms.worldline.model.User;
import com.etms.worldline.payload.request.skilldetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data

public class Userdetails {
    private Long user_id;
    private String username;
    private String gender;
    private String email;
    private String gbl;
    private String roleName;
    private boolean verify;
    private String propicPath;
    private List<skilldetails> skilldetails;
    private LocalDateTime joiningDate;
    private List<lackingskilldetails> lackingskilldetails;
    public Userdetails(Long user_id, String Username, String gender, String email, String gbl, boolean verify) {
        this.user_id = user_id;
        this.username = Username;
        this.email=email;
        this.gender = gender;
        this.gbl=gbl;
        this.verify=verify;
    }
    public Userdetails(Long user_id, String Username, String gender, String email, String gbl, boolean verify,String propicPath, LocalDateTime joiningDate) {
        this.user_id = user_id;
        this.username = Username;
        this.email=email;
        this.gender = gender;
        this.gbl=gbl;
        this.verify=verify;
        this.propicPath=propicPath;
        this.joiningDate=joiningDate;
    }
    public Userdetails(Long user_id, String Username, String gender, String email, String gbl,String roleName) {
        this.user_id = user_id;
        this.username = Username;
        this.email=email;
        this.gender = gender;
        this.gbl=gbl;
        this.roleName=roleName;
    }
    public Userdetails(Long user_id, String username, String email) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
    }
}
