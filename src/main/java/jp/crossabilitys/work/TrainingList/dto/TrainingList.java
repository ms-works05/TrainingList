package jp.crossabilitys.work.TrainingList.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 訓練一覧データ保持クラス
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TrainingList {
    @Id
    private long id;                // ID
    private String trainingname;    // 訓練名
    private LocalDate start_date;   // 訓練開始日
    private LocalDate end_date;     // 訓練終了日
    private String consignorname;   // 委託元
    private Long unassignedcount;   // 未割当件数

}
