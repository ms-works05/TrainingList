package jp.crossabilitys.work.TrainingList.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import one.cafebabe.businesscalendar4j.BusinessCalendar;

import jp.crossabilitys.work.TrainingList.Entity.TrainingInfo;
import jp.crossabilitys.work.TrainingList.Entity.TrainingSchedule;
import jp.crossabilitys.work.TrainingList.dto.TrainingRequest;
import jp.crossabilitys.work.TrainingList.dto.TrainingList;
import jp.crossabilitys.work.TrainingList.repository.TrainingRepository;
import jp.crossabilitys.work.TrainingList.repository.TrainingListRepository;

/**
 * 訓練情報 Service
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class TrainingService {
    /**
     * 訓練情報 Repository
     */
    private final TrainingRepository trainingRepository;
    /**
     * 訓練情報(一覧表示用) Repository
     */
    private final TrainingListRepository listRepository;
    /**
     * 訓練情報 全検索
     * 訓練マスタより全件取得
     * @return 検索結果
     */
    public List<TrainingInfo> searchAll() {
        return trainingRepository.searchAll(false);
    }
    /**
     * 訓練情報 全検索
     * 訓練マスタより全件取得
     * 併せて訓練スケジュールテーブルの講師未割当件数取得
     * @return 検索結果
     */
    public List<TrainingList> searchAllwithAssign(){
        return listRepository.searchAllwithAssign(false);
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
     * [新規]訓練マスタ、訓練スケジュールテーブル新規登録
     * [更新]訓練マスタ更新、訓練期間変更ありの場合、訓練スケジュールテーブル新規登録
     *                    訓練期間変更なしの場合、訓練スケジュールテーブル更新
     * @param request
     */
    public void registTraining(TrainingRequest request) {
        TrainingInfo eList = new TrainingInfo();
        boolean isAddSchedule = true;

        // 値保持
        LocalDate startDate = request.getStart_date();      // 訓練期間
        LocalDate endDate = request.getEnd_date();
        int trainingHours = request.getTraining_hours();    // 訓練時間数

        // 祝日
        BusinessCalendar holidays = BusinessCalendar.newBuilder().holiday(BusinessCalendar.JAPAN.PUBLIC_HOLIDAYS).build();

        /* ----- 登録前処理 ----- */
        // 更新の場合
        if (request.getId()!=0){
//            eList.setId(request.getId());
            // 更新前データ取得
            eList = findById(request.getId());
            if (eList.getStart_date().equals(startDate) && eList.getEnd_date().equals(endDate)) {
                // 訓練期間変更なしの場合
                isAddSchedule = false;
            } else{
                // 訓練期間変更ありの場合
                isAddSchedule = true;
                //訓練スケジュール削除(登録済スケジュールデータを全削除)
                eList.getTrainingSchedule().clear();
                trainingRepository.save(eList);
            }
        }
        /* ----- 訓練情報 登録 ----- */
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
        // 委託元選択の場合、nullをセット
        if (request.getConsignor_id()==0) {
            eList.setConsignor_id(null);
        }else {
            eList.setConsignor_id(request.getConsignor_id());
        }
        /* ----- 訓練スケジュール 登録 ----- */
        if (isAddSchedule == true){
            // 訓練スケジュールデータ新規作成
            eList.getTrainingSchedule().clear();
            for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
                TrainingSchedule oneDay = new TrainingSchedule();
                oneDay.setTrainingInfo(eList);
                oneDay.setTraining_date(date);

                if ((date.getDayOfWeek().getValue() > 5) || (holidays.isHoliday(oneDay.getTraining_date())) ) {
                    // 土日祝日の場合、訓練時間数をゼロに設定
                    oneDay.setTraining_hours(0);
                }else{
                    oneDay.setTraining_hours(trainingHours);
                    // 講師選択した場合
                    if (request.getTeacher_id() > 0) {
                        oneDay.setTeacher_id(request.getTeacher_id());
                    }
                }
                eList.getTrainingSchedule().add(oneDay);
            }
        } else {
            // 訓練スケジュールデータ更新
            for (TrainingSchedule oneDay : eList.getTrainingSchedule()){
                if (oneDay.getTraining_hours()>0) {
                    // 訓練時間
                    oneDay.setTraining_hours(trainingHours);
                    // 講師選択した場合
                    if (request.getTeacher_id() > 0) {
                        oneDay.setTeacher_id(request.getTeacher_id());
                    }
                }
            }
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
