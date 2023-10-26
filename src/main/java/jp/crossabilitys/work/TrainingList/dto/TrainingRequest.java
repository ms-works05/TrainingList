package jp.crossabilitys.work.TrainingList.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * 訓練情報 リクエストデータ
 */
@Data
public class TrainingRequest {

    private long id;        // ID

    @NotEmpty(message = "訓練名を入力してください")
    private String trainingname;

    @NotNull(message = "募集開始日を入力してください")
    private LocalDate recruit_start_date;

    @NotNull(message = "募集終了日を入力してください")
    private LocalDate recruit_end_date;

    @NotNull(message = "選考日を入力してください")
    private LocalDate selection_date;

    private LocalDate acceptance_date;  // 合格発表日

    @NotNull(message = "訓練開始日を入力してください")
    private LocalDate start_date;

    @NotNull(message = "訓練終了日を入力してください")
    private LocalDate end_date;

    @NotNull(message = "授業開始時間を入力してください")
    private LocalTime start_time;

    @NotNull(message = "授業終了時間を入力してください")
    private LocalTime end_time;

    private int training_hours;         // 授業時間数(HTMLでvalidation)

    private int totaltraining_hours;    // 総授業時間数（未使用）

    private Long consignor_id;          // 委託元ID

    private boolean deleteflg;          // 削除フラグ

    private List<TrainingScheduleRequest> trainingDays;

    @AssertTrue(message="募集期間の開始日は、終了日以前を入力してください。")
    public boolean isDateValidRecruit() {
        if ((recruit_start_date == null) || (recruit_end_date == null)) return true;
        if (recruit_start_date.isBefore(recruit_end_date)) return true;
        return false;
    }
    @AssertTrue(message="訓練期間の開始日は、終了日以前を入力してください。")
    public boolean isDateValidTraining() {
        if ((start_date == null) || (end_date == null)) return true;
        if (start_date.isBefore(end_date)) return true;
        return false;
    }
    @AssertTrue(message="授業時間の開始時刻は、終了時刻以前を入力してください。")
    public boolean isTimeValidTraining() {
        if ((start_time == null) || (end_time == null)) return true;
        if (start_time.isBefore(end_time)) return true;
        return false;
    }

}
