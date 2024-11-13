package com.etms.worldline.payload.response;

import com.etms.worldline.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Manager;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Data
@RequiredArgsConstructor
public class LoginSuccessfullResponse {
    private int response_code;
    private String message;
    private String token;
    private Long user_id;
    private ManagerdetailsDTO profile;
    private List<String> role;

    public LoginSuccessfullResponse(int responseCode,String message, String token,Long user_id, List<String> role, ManagerdetailsDTO manager){
        this.response_code=responseCode;
        this.message=message;
        this.token=token;
        this.user_id=user_id;
        this.role=role;
        this.profile=manager;
    }
    // Getters and Setters}
// UserProfile.javapublic class UserProfile {
}
