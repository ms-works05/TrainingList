package jp.crossabilitys.work.TrainingList.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jp.crossabilitys.work.TrainingList.Entity.TrainingSchedule;

import one.cafebabe.businesscalendar4j.BusinessCalendarBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.crossabilitys.work.TrainingList.Entity.TrainingInfo;
import jp.crossabilitys.work.TrainingList.dto.TrainingRequest;
import jp.crossabilitys.work.TrainingList.repository.TrainingRepository;

import one.cafebabe.businesscalendar4j.BusinessCalendar;

/**
 * 訓練情報 Service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TrainingService {
    /**
     *  訓練情報 Repository
     */
    @Autowired
    private TrainingRepository trainingRepository;
    /**
     * 訓練情報 全検索
     * @return 検索結果
     */
    public List<TrainingInfo> searchAll() {
        return trainingRepository.searchAll(false);

    }

    /**
     * 訓練情報 ID検索
     * @param id ID
     * @return 検索結果
     */
    public TrainingInfo findById(Long id){
        Optional<TrainingInfo> eList = this.trainingRepository.findById(id);
        return eList.orElseThrow();
    }
    /**
     * 訓練情報 登録
     * @param request
     */
    public void registTraining(TrainingRequest request) {
        TrainingInfo eList = new TrainingInfo();

        // 値保持
        LocalDate startDate = request.getStart_date();      // 訓練期間
        LocalDate endDate = request.getEnd_date();
        int trainingHours = request.getTraining_hours();    // 訓練時間数

        // 祝日
        BusinessCalendar holidays = BusinessCalendar.newBuilder().holiday(BusinessCalendar.JAPAN.PUBLIC_HOLIDAYS).build();

        // 更新の場合
        if (request.getId()!=0){
            eList.setId(request.getId());
            //訓練スケジュール削除
            eList.getTrainingSchedule().clear();
            trainingRepository.save(eList);
        }
        // Entityクラスに値セット
        eList.setTrainingname(request.getTrainingname());
        eList.setRecruit_start_date(request.getRecruit_start_date());
        eList.setRecruit_end_date(request.getRecruit_end_date());
        eList.setSelection_date(request.getSelection_date());
        eList.setAcceptance_date(request.getAcceptance_date());
        eList.setStart_date(startDate);
        eList.setEnd_date(endDate);
        eList.setStart_time(request.getStart_time());
        eList.setEnd_time(request.getEnd_time());
        eList.setTraining_hours(trainingHours);
        eList.setTotaltraining_hours(request.getTotaltraining_hours());
        eList.setDeleteflg(request.isDeleteflg());

        // 訓練スケジュールデータ作成
        eList.getTrainingSchedule().clear();
        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            TrainingSchedule oneDay = new TrainingSchedule();
            oneDay.setTrainingInfo(eList);
            oneDay.setTraining_date(date);
            oneDay.setMemo("");
            oneDay.setTeacher_id(0L);

            if ((date.getDayOfWeek().getValue() > 5) || (holidays.isHoliday(oneDay.getTraining_date())) ) {
                // 土日祝日の場合、訓練時間数をゼロに設定
                oneDay.setTraining_hours(0);
            }else{
                oneDay.setTraining_hours(trainingHours);
            }
            eList.getTrainingSchedule().add(oneDay);
        }
        TrainingInfo out = trainingRepository.save(eList);
    }

    /**
     * 訓練情報 削除(論理削除)
     * @param id ID
     */
    public void deleteTraining(Long id){
        TrainingInfo eList = findById(id);
        // 削除フラグ設定
        eList.setDeleteflg(true);

        TrainingInfo out = trainingRepository.save(eList);
    }
}
