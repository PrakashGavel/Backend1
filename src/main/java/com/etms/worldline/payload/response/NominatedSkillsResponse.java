package com.etms.worldline.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NominatedSkillsResponse {
       private int response_code;
       private String message;
       private List<NominatedSkillsStatusResponse> nominatedSkillsStatus;
}
