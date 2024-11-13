package com.etms.worldline.payload.request;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class SignupRequest {

  private String username;



  private String email;

  private Set<String> role;



  private String password;
  private String last_name;
  private String gender;

  private String group;

  private String gbl;

  private Set<Long> empskillids;



  private Date dob;

  private String location;

  private String entity;

  private String das_id;

  private String allocated_project;
  private String allocated_manager;


}
