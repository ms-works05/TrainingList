package jp.crossabilitys.work.TrainingList.repository;

import jp.crossabilitys.work.TrainingList.Entity.TeacherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<TeacherInfo,Long> {
    @Query(value="SELECT * FROM TEACHERINFO where deleteflg=:deleteflg",nativeQuery = true)
    List<TeacherInfo> searchAll(boolean deleteflg);
}
