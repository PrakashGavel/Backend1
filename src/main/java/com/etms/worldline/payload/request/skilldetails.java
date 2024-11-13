package com.etms.worldline.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class skilldetails {
    private String skillName;
    private int skill_level;
}
