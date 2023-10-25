package jp.crossabilitys.work.TrainingList.service;

import jp.crossabilitys.work.TrainingList.Entity.TrainingSchedule;
import jp.crossabilitys.work.TrainingList.dto.ScheduleData;
import jp.crossabilitys.work.TrainingList.dto.TrainingScheduleRequest;
import jp.crossabilitys.work.TrainingList.repository.TrainingScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 訓練スケジュール Service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TrainingScheduleService {
    /**
     * 訓練スケジュール Repository
      */
    @Autowired
    private TrainingScheduleRepository myRepojitory;

    /**
     * 訓練スケジュール 検索
     * 指定された訓練IDの訓練スケジュールを取得する
     * @param training_id 訓練ID
     * @return ScheduleData 検索結果
     */
    public ScheduleData searchSchedule(long training_id){
        // 訓練スケジュール取得
        List<TrainingSchedule> scheduleList = myRepojitory.findByTraining_id(training_id);
        ScheduleData scheduleData = new ScheduleData();
        List<TrainingScheduleRequest> list = new ArrayList<>();

        // Entityクラスを表示用データに値セット
        for(TrainingSchedule data : scheduleList){
            TrainingScheduleRequest row = new TrainingScheduleRequest();
            row.setId(data.getId());
            row.setTraining_date(data.getTraining_date());
            row.setTraining_hours(data.getTraining_hours());
            row.setTeacher_id(data.getTeacher_id());
            row.setTraining_id(data.getTrainingInfo().getId());
            row.setMemo(data.getMemo());
            list.add(row);
        }
        scheduleData.setTraining_id(training_id);// 追加
        scheduleData.setScheduleDataList(list);
        return scheduleData;
    }

    /**
     * 訓練スケジュール 更新
     * @param scheduleData
     */
    public void updateAll(ScheduleData scheduleData){
        List<TrainingSchedule> list = new ArrayList<>();

        // 画面パラメータをEntityクラスに値セット
        for (TrainingScheduleRequest data : scheduleData.getScheduleDataList()){
            TrainingSchedule row = myRepojitory.findById(data.getId()).get();
            row.setTraining_date(data.getTraining_date());
            row.setTraining_hours(data.getTraining_hours());
            row.setMemo(data.getMemo());
            // 講師未選択の場合、nullをセット
            if (data.getTeacher_id()==0) {
                row.setTeacher_id(null);
            }else {
                row.setTeacher_id(data.getTeacher_id());
            }
            list.add(row);
        }
        myRepojitory.saveAll(list);
    }

    /**
     * timetableのコンテキストメニューから講師の変更
     * @param trainingScheduleId ID
     * @param teacherId teacher_id
     */
    public void updateTeacherInTimetable(Long trainingScheduleId, Long teacherId){
        // 画面パラメータをEntityクラスに値セット
            TrainingSchedule row = myRepojitory.findById(trainingScheduleId).get();
//            row.setTraining_date(data.getTraining_date());
//            row.setTraining_hours(data.getTraining_hours());
//            row.setMemo(data.getMemo());
            // 講師未選択の場合、nullをセット
            if (teacherId==0) {
                row.setTeacher_id(null);
            }else {
                row.setTeacher_id(teacherId);
            }
        myRepojitory.save(row);
    }
}
