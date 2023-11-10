package jp.crossabilitys.work.TrainingList.repository;

import jp.crossabilitys.work.TrainingList.Entity.Consignor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 種別(委託元) Repository
 */
public interface ConsignorRepository extends JpaRepository<Consignor, Long> {
}
