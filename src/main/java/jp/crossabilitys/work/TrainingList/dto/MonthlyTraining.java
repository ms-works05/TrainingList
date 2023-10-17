package jp.crossabilitys.work.TrainingList.dto;

import lombok.Data;

import java.util.List;
/**
 * 訓練時間表データ（月）保持クラス
 */
@Data
public class MonthlyTraining {

    private int month;
    private int totalTraining_hours;        // 訓練時間
    private int TotalTraining_days;         // 訓練日数
    List<DailyTraining> trainings;
}
