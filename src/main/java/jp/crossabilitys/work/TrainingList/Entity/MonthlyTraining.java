package jp.crossabilitys.work.TrainingList.Entity;

import lombok.Data;

import java.util.List;

@Data
public class MonthlyTraining {

    private int month;
    private int totalTraining_hours;        // 訓練時間
    private int TotalTraining_days;         // 訓練日数
    List<DailyTraining> trainings;
}
