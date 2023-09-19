package jp.crossabilitys.work.TrainingList.Entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * 訓練情報 Entity
 */
@Entity
@Data
@Table(name = "TRAININGLIST")
public class TrainingList extends CommonEntity {
    @Id
    @GeneratedValue
    private Long id;                // ID(主キー自動採番)

    @NotBlank
    @Size(max = 256)
    private String trainingname;    // 訓練名

    private LocalDate start_date;    // 訓練開始日

    private LocalDate end_date;      // 訓練終了日

    @Size(max = 1)
    private int deleteflg;          // 削除フラグ
}
