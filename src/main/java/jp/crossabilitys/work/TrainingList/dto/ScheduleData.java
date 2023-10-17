package jp.crossabilitys.work.TrainingList.dto;

import lombok.Data;

import java.util.List;

/**
 * スケジュールデータ保持クラス
 */
@Data
public class ScheduleData {
    private List<TrainingScheduleRequest> scheduleDataList;
}
