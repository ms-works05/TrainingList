package jp.crossabilitys.work.TrainingList.repository;

import jp.crossabilitys.work.TrainingList.Entity.TrainingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 訓練スケジュール Repository
 */
public interface TrainingScheduleRepository extends JpaRepository<TrainingSchedule,Long> {
    @Query(value="SELECT * FROM TRAININGSCHEDULE where training_id=:id",nativeQuery = true)
    List<TrainingSchedule> findByTraining_id(Long id);
}
