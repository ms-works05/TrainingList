package jp.crossabilitys.work.TrainingList.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jp.crossabilitys.work.TrainingList.Entity.TrainingInfo;

/**
 * 訓練情報 Repository
 */
public interface TrainingRepository extends JpaRepository<TrainingInfo,Long>{
    @Query(value="SELECT * FROM TRAININGINFO where deleteflg=:deleteflg",nativeQuery = true)
    List<TrainingInfo> searchAll(boolean deleteflg);

}
