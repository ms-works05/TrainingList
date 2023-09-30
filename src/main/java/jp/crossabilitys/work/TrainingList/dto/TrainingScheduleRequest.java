package jp.crossabilitys.work.TrainingList.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TrainingScheduleRequest {
    private LocalDate training_date;    // 訓練日

    private int training_hours;         // 授業時間数

    private String memo;                // memo

    private Long teacher_id;            // 講師ID
}
