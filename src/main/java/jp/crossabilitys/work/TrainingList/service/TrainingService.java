package jp.crossabilitys.work.TrainingList.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import jp.crossabilitys.work.TrainingList.Entity.TrainingSchedule;
import jp.crossabilitys.work.TrainingList.dto.TrainingScheduleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.crossabilitys.work.TrainingList.Entity.TrainingList;
import jp.crossabilitys.work.TrainingList.dto.TrainingRequest;
import jp.crossabilitys.work.TrainingList.repository.TrainingRepository;

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
    public List<TrainingList> searchAll() {

        return trainingRepository.searchAll(false);
    }
    public TrainingList findById(Long id){
        Optional<TrainingList> exam = this.trainingRepository.findById(id);
        return exam.orElseThrow();
    }
    /**
     * 訓練情報 新規登録
     * @param request
     */
    public void registTraining(TrainingRequest request) {
        TrainingList entity = new TrainingList();

        // 値保持
        LocalDate startDate = request.getStart_date();      // 訓練期間
        LocalDate endDate = request.getEnd_date();
        int trainingHours = request.getTraining_hours();    // 訓練時間数


        // 更新の場合
        if (request.getId()!=0){
            entity.setId(request.getId());
            //訓練スケジュール削除
            entity.getTrainingSchedule().clear();
            trainingRepository.save(entity);
        }
        entity.setTrainingname(request.getTrainingname());              // 訓練名
        entity.setRecruit_start_date(request.getRecruit_start_date());	// 募集開始日
        entity.setRecruit_end_date(request.getRecruit_end_date());		// 募集終了日
        entity.setSelection_date(request.getSelection_date());			// 選考日
        entity.setAcceptance_date(request.getAcceptance_date());		// 合格発表日
        entity.setStart_date(startDate);					            // 訓練開始日
        entity.setEnd_date(endDate);						            // 訓練終了日
        entity.setStart_time(request.getStart_time());					// 授業開始時間
        entity.setEnd_time(request.getEnd_time());						// 授業終了時間
        entity.setTraining_hours(trainingHours);			            // 授業時間数
        entity.setTotaltraining_hours(request.getTotaltraining_hours());// 総授業時間数
        entity.setDeleteflg(request.isDeleteflg());                     // 削除フラグ

        // 訓練スケジュールデータ作成
        List<TrainingScheduleRequest> trainingDays = request.getTrainingDays();
        entity.getTrainingSchedule().clear();
        LocalDate trainingDate = LocalDate.now();

        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            TrainingSchedule oneDay = new TrainingSchedule();
            oneDay.setTrainingList(entity);
            oneDay.setTraining_date(date);
            oneDay.setMemo("");
            oneDay.setTeacher_id(0L);

            if (date.getDayOfWeek().getValue() > 5) {
                // 土日の場合、訓練時間数をゼロに設定
                oneDay.setTraining_hours(0);
            }else{
                oneDay.setTraining_hours(trainingHours);
            }

            entity.getTrainingSchedule().add(oneDay);
        }

        TrainingList out = trainingRepository.save(entity);

    }

    /**
     * 訓練情報 削除(論理削除)
     * @param id id
     */
    public void deleteTraining(Long id){
        TrainingList entity = findById(id);
        // 削除フラグ設定
        entity.setDeleteflg(true);

        TrainingList out = trainingRepository.save(entity);
    }
}
