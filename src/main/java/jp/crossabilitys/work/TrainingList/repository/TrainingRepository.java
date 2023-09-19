package jp.crossabilitys.work.TrainingList.repository;

import jp.crossabilitys.work.TrainingList.Entity.TrainingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<TrainingList,Long>{
}
