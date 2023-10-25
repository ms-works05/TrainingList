package jp.crossabilitys.work.TrainingList.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
/**
 * 講師情報 Entity
 */
@Entity
@Data
@Table(name = "TEACHERINFO")
public class TeacherInfo extends CommonEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;                // ID(主キー自動採番)

    @NotBlank
    @Size(max = 256)
    private String name;            // 講師名

    private boolean deleteflg;      // 削除フラグ

}
