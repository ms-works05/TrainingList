package jp.crossabilitys.work.TrainingList.Entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 共通 Entity
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class CommonEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime create_date;  // 登録日時

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private LocalDateTime update_date;  // 更新日時
}
