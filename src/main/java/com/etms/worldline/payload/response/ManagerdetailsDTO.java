package com.etms.worldline.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ManagerdetailsDTO {
    private String name;
    private String email;
    private String das_id;
    private String entity;
    private String gbl;
    private String location;
    private String manager;
    private Long manager_id;
    private String manager_fcm_token;
}
