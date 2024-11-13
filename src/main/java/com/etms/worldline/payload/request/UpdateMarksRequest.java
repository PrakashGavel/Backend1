package com.etms.worldline.payload.request;

import lombok.Data;

@Data
public class UpdateMarksRequest {
    private Long user_id;
    private Long quizNo;
    private Integer marks;
}
