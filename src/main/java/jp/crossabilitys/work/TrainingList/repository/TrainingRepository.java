package jp.crossabilitys.work.TrainingList.repository;

import jp.crossabilitys.work.TrainingList.Entity.TrainingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainingRepository extends JpaRepository<TrainingInfo,Long>{
    @Query(value="SELECT * FROM TRAININGINFO where deleteflg=:deleteflg",nativeQuery = true)
    List<TrainingInfo> searchAll(boolean deleteflg);
}
