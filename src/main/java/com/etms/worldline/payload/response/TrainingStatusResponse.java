package com.etms.worldline.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingStatusResponse {
    private int response_code;
    private String message;
    private List<TrainingStatus> training_status;
}
