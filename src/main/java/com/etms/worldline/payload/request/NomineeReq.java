package com.etms.worldline.payload.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class NomineeReq {
    private Long user_id;
    private Long skill_id;
    private String skill_name;
    private Long training_id;
    private boolean acknowledgement;
}
