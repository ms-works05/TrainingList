package jp.crossabilitys.work.TrainingList.service;

import jp.crossabilitys.work.TrainingList.Entity.Consignor;
import jp.crossabilitys.work.TrainingList.repository.ConsignorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 委託元 Service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ConsignorService {
    /**
     * 委託元 Repository
     */
    @Autowired
    private ConsignorRepository myRepojitory;

    /**
     * 委託元 全検索
     * @return 検索結果
     */
    public List<Consignor> searchAll(){
        return myRepojitory.findAll();
    }
}
