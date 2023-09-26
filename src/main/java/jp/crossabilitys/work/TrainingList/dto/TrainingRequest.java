package jp.crossabilitys.work.TrainingList.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

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

    @NotNull(message = "合格発表日を入力してください")
    private LocalDate acceptance_date;

    @NotNull(message = "訓練開始日を入力してください")
    private LocalDate start_date;

    @NotNull(message = "訓練終了日を入力してください")
    private LocalDate end_date;

    @NotNull(message = "授業開始時間を入力してください")
    private LocalTime start_time;

    @NotNull(message = "授業終了時間を入力してください")
    private LocalTime end_time;

    @NotNull(message = "授業時間数を入力してください")
    private int training_hours;

    private int totaltraining_hours;    // 総授業時間数

    private boolean deleteflg;          // 削除フラグ

}
