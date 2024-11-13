package com.etms.worldline.payload.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@RequiredArgsConstructor
public class FetchTrainerTrainingResponse {
    private int response_code;
    private String message;
    private Page<TrainerTrainingsResponse> trainings;
    public FetchTrainerTrainingResponse(int response_code,String message){
        this.response_code=response_code;
        this.message=message;
    }
}
