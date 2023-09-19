package jp.crossabilitys.work.TrainingList.controller;

import jp.crossabilitys.work.TrainingList.Entity.TrainingList;
import jp.crossabilitys.work.TrainingList.service.TrainingService;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TrainingListController {
    /**
     * 訓練情報 Service
     */
    @Autowired
    private TrainingService trainingService;

    /**
     * 訓練情報一覧画面を表示
     * @param model Model
     * @return 訓練情報一覧画面
     */
    @GetMapping("/training/list")
    public String getAllTrainingList(Model model) {
        List<TrainingList> traininglist = trainingService.searchAll();
        model.addAttribute("traininglist", traininglist);
        return "training/traininglist";
    }

    /**
     * 訓練情報追加
     * （訓練情報画面を表示し、登録処理を行う）
     * @param model
     * @return 訓練情報登録画面
     */
    @GetMapping("/training/add")
    public String trainingAdd(Model model) {
        return "training/traininginfo";
    }
    /**
     * 訓練情報更新
     * （訓練情報画面を表示し、更新処理を行う）
     * @param model Model
     * @param id 表示する訓練ID
     * @return 訓練情報登録画面
     */
    @GetMapping("/training/update/{id}")
    public String trainingUpdate(Model model, @PathVariable Long id) {
        // 仮
        return "training/traininginfo";
    }

    /**
     * 訓練情報削除
     * @param model Model
     * @param id 削除する訓練ID
     * @return 訓練情報一覧画面
     */
    @GetMapping("/training/delete/{id}")
    public String trainingDelete(Model model, @PathVariable Long id){
        // 仮
        return "training/traininglist";
    }
}
