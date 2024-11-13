package com.etms.worldline.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class lackingskilldetails {
    private String skillName;
    private int skill_level;
    private int needskill_level;
  private List<String> urls;
    public lackingskilldetails(String skillName,int skill_level,int needskill_level){
        this.skillName=skillName;
        this.skill_level=skill_level;
        this.needskill_level=needskill_level;
    }
}
