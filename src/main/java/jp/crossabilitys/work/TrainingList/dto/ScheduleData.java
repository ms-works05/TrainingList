package jp.crossabilitys.work.TrainingList.dto;

import lombok.Data;

import java.util.List;

/**
 * スケジュールデータ保持クラス
 */
@Data
public class ScheduleData {
    private Long training_id;           // 訓練ID
    private List<TrainingScheduleRequest> scheduleDataList;
}
