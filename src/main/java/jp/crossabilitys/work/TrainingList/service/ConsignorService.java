package jp.crossabilitys.work.TrainingList.service;

import jp.crossabilitys.work.TrainingList.Entity.Consignor;
import jp.crossabilitys.work.TrainingList.dto.ConsignorRequest;
import jp.crossabilitys.work.TrainingList.repository.ConsignorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 種別(委託元) Service
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ConsignorService {
    /**
     * 種別(委託元) Repository
     */
    private final ConsignorRepository myRepository;

    /**
     * 種別(委託元) 全検索
     * @return 検索結果
     */
    public List<Consignor> searchAll(){
        return myRepository.findAll();
    }
    /**
     * 種別(委託元) ID検索
     * @param id ID
     * @return 検索結果
     */
    public Consignor findById(Long id){
        Optional<Consignor> eList = myRepository.findById(id);
        return eList.orElseThrow();
    }
    /**
     * 種別(委託元) 登録
     * @param request
     */
    public void registConsignor(ConsignorRequest request){
        Consignor eList = new Consignor();

        // 更新の場合
        if (request.getId()!=0){
            eList.setId(request.getId());
        }
        // Entityクラスに値セット
        eList.setName1(request.getName1());
        eList.setName2(request.getName2());

        Consignor out = myRepository.save(eList);
    }
    /**
     * 種別(委託元) 削除
     * @param id ID
     */
    public void deleteConsignor(Long id){
        myRepository.deleteById(id);
    }
}
