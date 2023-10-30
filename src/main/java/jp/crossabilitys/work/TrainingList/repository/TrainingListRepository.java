package jp.crossabilitys.work.TrainingList.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jp.crossabilitys.work.TrainingList.dto.TrainingList;

/**
 * 訓練情報 Repository
 * 講師未割当件数を取得するため
 */
public interface  TrainingListRepository extends JpaRepository<TrainingList,Long> {
    @Query(value="SELECT info.id, info.trainingname, info.start_date, info.end_date, " +
            "con.name2 AS consignorname, " +
            "IFNULL(days.count, 0) AS unassignedcount " +
            "from traininginfo AS info " +
            "LEFT JOIN consignor AS con ON info.consignor_id=con.id " +
            "LEFT JOIN  (" +
            "SELECT training_id,COUNT(*) AS count FROM trainingschedule " +
            "WHERE training_hours>0 AND teacher_id is NULL  GROUP BY training_id) AS days " +
            "ON info.id=days.training_id " +
            "WHERE info.deleteflg=:deleteflg", nativeQuery = true)
    List<TrainingList> searchAllwithAssign(boolean deleteflg);
}
