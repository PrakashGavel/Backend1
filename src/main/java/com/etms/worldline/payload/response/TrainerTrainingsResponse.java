package com.etms.worldline.payload.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TrainerTrainingsResponse {
    private Long id;

    private String training_name;

    private LocalDate training_date;

    private LocalDateTime slot_from;

    private LocalDateTime slot_to;

    private List<String> topics;

    private int participants;

    public TrainerTrainingsResponse(Long id, String trainingName, LocalDate trainingDate, LocalDateTime slotFrom, LocalDateTime slotTo) {
        this.id = id;
        this.training_name = trainingName;
        this.training_date = trainingDate;
        this.slot_from = slotFrom;
        this.slot_to = slotTo;
    }
}
