package jp.crossabilitys.work.TrainingList.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 訓練時間表データ（日）保持クラス
 */
@Data
public class DailyTraining {
    private Long id;                    // ID
    private LocalDate training_date;    // 訓練日
    private int training_hours;         // 授業時間数
    private String backcolor;           // 背景色
}
