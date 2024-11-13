package com.etms.worldline.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TrainingsResponse {
    private Long id;

    private String training_name;

    private LocalDate training_date;

    private LocalDateTime slot_from;

    private LocalDateTime slot_to;

    private String trainer;

    private List<String> topics;



    public TrainingsResponse(Long id, String trainingName, LocalDate trainingDate, LocalDateTime slotFrom, LocalDateTime slotTo, String trainer) {
        this.id = id;
        this.training_name = trainingName;
        this.training_date = trainingDate;
        this.slot_from = slotFrom;
        this.slot_to = slotTo;
        this.trainer = trainer;
    }
}
