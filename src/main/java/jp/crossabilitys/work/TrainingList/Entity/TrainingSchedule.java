package jp.crossabilitys.work.TrainingList.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "TRAININGSCHEDULE")
public class TrainingSchedule extends CommonEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;                    // ID(主キー)

    @Temporal(TemporalType.DATE)
    private LocalDate training_date;    // 訓練日

    private int training_hours;         // 授業時間数

    @Size(max = 256)
    private String memo;                // memo

    private Long teacher_id;            // 講師ID

    @OneToOne
    @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
    private TeacherInfo teacherInfo;        // 講師情報

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private TrainingInfo trainingInfo;

}
