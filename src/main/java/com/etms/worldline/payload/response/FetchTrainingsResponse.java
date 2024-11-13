package com.etms.worldline.payload.response;

import com.etms.worldline.model.Trainings;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Data
@RequiredArgsConstructor
public class FetchTrainingsResponse {
    private int response_code;
    private String message;
    private Page<TrainingsResponse> trainings;
    public FetchTrainingsResponse(int response_code,String message){
        this.response_code=response_code;
        this.message=message;
    }
}
