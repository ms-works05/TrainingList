package jp.crossabilitys.work.TrainingList.service;

import jp.crossabilitys.work.TrainingList.Entity.TeacherInfo;
import jp.crossabilitys.work.TrainingList.dto.TeacherRequest;
import jp.crossabilitys.work.TrainingList.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class TeacherService {
    /**
     *  講師情報 Repository
     */
    private final TeacherRepository teacherRepository;
    /**
     * 講師情報 全検索
     * @return 検索結果
     */
    public List<TeacherInfo> searchAll() {
        return teacherRepository.searchAll(false);
    }

    /**
     * 講師情報 ID検索
     * @param id ID
     * @return 検索結果
     */
    public TeacherInfo findById(Long id){
        Optional<TeacherInfo> eList = this.teacherRepository.findById(id);
        return eList.orElseThrow();
    }

    /**
     * 講師情報 登録
     * @param request
     */
    public void registTeacher(TeacherRequest request){
        TeacherInfo eList = new TeacherInfo();

        // 更新の場合
        if (request.getId()!=0){
            eList.setId(request.getId());
        }
        // Entityクラスに値セット
        eList.setName(request.getName());

        TeacherInfo out = teacherRepository.save(eList);
    }
    /**
     * 講師情報 削除(論理削除)
     * @param id ID
     */
    public void deleteTeacher(Long id){
        TeacherInfo eList = findById(id);
        // 削除フラグ設定
        eList.setDeleteflg(true);

        TeacherInfo out = teacherRepository.save(eList);
    }
}
