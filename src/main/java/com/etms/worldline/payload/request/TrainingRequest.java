package com.etms.worldline.payload.request;

import com.etms.worldline.model.Trainer;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class TrainingRequest {

    private Long id;

    private String training_name;

    private LocalDate training_date;

    private LocalDateTime slot_from;

    private LocalDateTime slot_to;

    private Long trainer_id;

    private List<String> topics;
}
