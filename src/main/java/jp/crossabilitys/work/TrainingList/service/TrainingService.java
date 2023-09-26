package jp.crossabilitys.work.TrainingList.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        // 更新の場合
        if (request.getId()!=0){
            entity.setId(request.getId());
        }
        entity.setTrainingname(request.getTrainingname());              // 訓練名
        entity.setRecruit_start_date(request.getRecruit_start_date());	// 募集開始日
        entity.setRecruit_end_date(request.getRecruit_end_date());		// 募集終了日
        entity.setSelection_date(request.getSelection_date());			// 選考日
        entity.setAcceptance_date(request.getAcceptance_date());		// 合格発表日
        entity.setStart_date(request.getStart_date());					// 訓練開始日
        entity.setEnd_date(request.getEnd_date());						// 訓練終了日
        entity.setStart_time(request.getStart_time());					// 授業開始時間
        entity.setEnd_time(request.getEnd_time());						// 授業終了時間
        entity.setTraining_hours(request.getTraining_hours());			// 授業時間数
        entity.setTotaltraining_hours(request.getTotaltraining_hours());// 総授業時間数
        entity.setDeleteflg(request.isDeleteflg());                     // 削除フラグ													// 削除フラグ

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
