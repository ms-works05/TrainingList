package jp.crossabilitys.work.TrainingList.service;

import jp.crossabilitys.work.TrainingList.Entity.TrainingSchedule;
import jp.crossabilitys.work.TrainingList.dto.ScheduleData;
import jp.crossabilitys.work.TrainingList.dto.TrainingScheduleRequest;
import jp.crossabilitys.work.TrainingList.repository.TrainingScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 訓練スケジュール Service
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class TrainingScheduleService {
    /**
     * 訓練スケジュール Repository
      */
    private final TrainingScheduleRepository myRepojitory;

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
            // 講師未選択の場合、nullをセット
            if (teacherId == 0) {
                row.setTeacher_id(null);
            }else {
                row.setTeacher_id(teacherId);
            }
        myRepojitory.save(row);
    }

    public void updateDateInTimetable(Long trainingScheduleId, LocalDate date){
        // 画面パラメータをEntityクラスに値セット
        TrainingSchedule row = myRepojitory.findById(trainingScheduleId).get();
        Long trainingId = row.getTrainingInfo().getId();
        //　変更する授業日から本来の授業を特定
        TrainingSchedule updateRow = myRepojitory.findByTraining_date(trainingId,date);
        // rowのデータをupdateに移植
        updateRow.setTraining_hours(row.getTraining_hours());
        updateRow.setMemo(row.getMemo());
        updateRow.setTeacher_id(row.getTeacher_id());

        // rowにupdateRowIdを入れid,training_date以外を初期値に
        row.setTraining_hours(0);
        row.setTeacher_id(null);
        row.setMemo("");

        myRepojitory.save(updateRow);
        myRepojitory.save(row);
    }

    public void swapDateInTimetable(Long trainingScheduleId, LocalDate date){
        // 画面パラメータをEntityクラスに値セット
        TrainingSchedule row = myRepojitory.findById(trainingScheduleId).get();
        Long trainingId = row.getTrainingInfo().getId();
        //　変更する授業日から本来の授業を特定
        TrainingSchedule updateRow = myRepojitory.findByTraining_date(trainingId,date);
        // rowの日付データをupdateRowに移植
        LocalDate localdate = updateRow.getTraining_date();
        updateRow.setTraining_date(row.getTraining_date());
        // updateRowの日付データをrowに移植
        row.setTraining_date(localdate);

        myRepojitory.save(updateRow);
        myRepojitory.save(row);
    }

    public void updateMemoInTimetable(Long trainingScheduleId, String memo){
        // 画面パラメータをEntityクラスに値セット
        TrainingSchedule row = myRepojitory.findById(trainingScheduleId).get();
        row.setMemo(memo);
        myRepojitory.save(row);
    }

    public void updateTrainingHoursInTimetable(Long trainingScheduleId, String trainingHours){
        // 画面パラメータをEntityクラスに値セット
        TrainingSchedule row = myRepojitory.findById(trainingScheduleId).get();
        row.setTraining_hours(Integer.parseInt(trainingHours));
        myRepojitory.save(row);
    }

    public Optional<TrainingSchedule> searchOneDaySchedule(Long scheduleid){
        Optional<TrainingSchedule> trainingScheduleRequest;
        trainingScheduleRequest = myRepojitory.findById(scheduleid);
        return trainingScheduleRequest;
    }

    /**
     * 訓練スケジュール 検索
     * 指定された訓練IDの最初と最後の訓練日を取得する
     * @param training_id 訓練ID
     * @return List<LocalDate></> 検索結果
     */
    public List<LocalDate> findFirstAndLastTrainingDate(long training_id){
        // 訓練スケジュール取得
        List<TrainingSchedule> scheduleList = myRepojitory.findByTraining_id(training_id);
        ScheduleData scheduleData = new ScheduleData();
        List<LocalDate> list = new ArrayList<>();

        // Entityクラスを表示用データに値セット
        LocalDate firstDay = LocalDate.parse("9999-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate lastDay = LocalDate.parse("1970-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        for(TrainingSchedule data : scheduleList){
            firstDay = firstDay.isAfter(data.getTraining_date()) ? data.getTraining_date() : firstDay;
            lastDay = lastDay.isBefore(data.getTraining_date()) ? data.getTraining_date() : lastDay;
        }
        list.add(firstDay);
        list.add(lastDay);
        return list;
    }
}
