package jp.crossabilitys.work.TrainingList.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 委託元 Entity
 */
@Entity
@Data
@Table(name = "CONSIGNOR")
public class Consignor extends CommonEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;                // ID(主キー自動採番)

    @NotBlank
    @Size(max = 50)
    private String name1;            // 名称1

    @NotBlank
    @Size(max = 50)
    private String name2;            // 名称2

    @NotBlank
    private int consigntype;        // タイプ

}
