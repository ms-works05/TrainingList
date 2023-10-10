package jp.crossabilitys.work.TrainingList.Entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyTraining {
    private LocalDate training_date;    // 訓練日
    private int training_hours;         // 授業時間数
    private String backcolor;           // 背景色
}
