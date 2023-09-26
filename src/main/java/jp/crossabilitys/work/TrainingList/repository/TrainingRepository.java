package jp.crossabilitys.work.TrainingList.repository;

import jp.crossabilitys.work.TrainingList.Entity.TrainingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainingRepository extends JpaRepository<TrainingList,Long>{
    @Query(value="SELECT * FROM TRAININGLIST where deleteflg=:deleteflg",nativeQuery = true)
    List<TrainingList> searchAll(boolean deleteflg);
}
