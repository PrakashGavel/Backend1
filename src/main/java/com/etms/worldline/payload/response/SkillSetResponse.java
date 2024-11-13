package com.etms.worldline.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SkillSetResponse {
    private int response_code;
    private String message;
    private List<String> skills;
}
