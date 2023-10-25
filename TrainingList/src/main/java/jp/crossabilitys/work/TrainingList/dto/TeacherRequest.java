package jp.crossabilitys.work.TrainingList.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
/**
 * 講師情報 リクエストデータ
 */
public class TeacherRequest {
    private long id;        // ID

    @NotEmpty(message = "講師名を入力してください")
    private String name;

}
