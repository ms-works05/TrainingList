package jp.crossabilitys.work.TrainingList.service;

import java.util.List;
import jp.crossabilitys.work.TrainingList.Entity.TrainingList;
import jp.crossabilitys.work.TrainingList.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        return trainingRepository.findAll();
    }

}
