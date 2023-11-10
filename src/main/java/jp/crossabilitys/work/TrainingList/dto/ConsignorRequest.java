package jp.crossabilitys.work.TrainingList.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 種別(委託元) リクエストデータ
 */
@Data
public class ConsignorRequest {
    private long id;        // ID

    @NotEmpty(message = "名称1を入力してください")
    private String name1;

    @NotEmpty(message = "名称2を入力してください")
    private String name2;
}
