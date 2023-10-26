package jp.crossabilitys.work.TrainingList.service;

import jp.crossabilitys.work.TrainingList.Entity.Consignor;
import jp.crossabilitys.work.TrainingList.repository.ConsignorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 委託元 Service
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ConsignorService {
    /**
     * 委託元 Repository
     */
    private final ConsignorRepository myRepojitory;

    /**
     * 委託元 全検索
     * @return 検索結果
     */
    public List<Consignor> searchAll(){
        return myRepojitory.findAll();
    }
}
