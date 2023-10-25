package jp.crossabilitys.work.TrainingList.Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * 訓練情報 Entity
 */
@Entity
@Data
@Table(name = "TRAININGINFO")
public class TrainingInfo extends CommonEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;                // ID(主キー自動採番)

    @NotBlank
    @Size(max = 256)
    private String trainingname;            // 訓練名

    @Temporal(TemporalType.DATE)
    private LocalDate recruit_start_date;   // 募集開始日

    @Temporal(TemporalType.DATE)
    private LocalDate recruit_end_date;     // 募集終了日

    @Temporal(TemporalType.DATE)
    private LocalDate selection_date;       // 選考日

    @Temporal(TemporalType.DATE)
    private LocalDate acceptance_date;      // 合格発表日

    @Temporal(TemporalType.DATE)
    private LocalDate start_date;           // 訓練開始日

    @Temporal(TemporalType.DATE)
    private LocalDate end_date;             // 訓練終了日

    @Temporal(TemporalType.TIME)
    private LocalTime start_time;           // 授業開始時間

    @Temporal(TemporalType.TIME)
    private LocalTime end_time;             // 授業終了時間

    private int training_hours;             // 授業時間数

    private int totaltraining_hours;        // 総授業時間数

    private boolean deleteflg;              // 削除フラグ

    @OneToMany(targetEntity = jp.crossabilitys.work.TrainingList.Entity.TrainingSchedule.class,
            cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true, mappedBy = "trainingInfo")
    @OrderBy(value = "training_date asc")
    private List<TrainingSchedule> trainingSchedule = new ArrayList<>();

}
