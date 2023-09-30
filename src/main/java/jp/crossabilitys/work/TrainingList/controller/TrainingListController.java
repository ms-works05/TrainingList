package jp.crossabilitys.work.TrainingList.controller;

import jp.crossabilitys.work.TrainingList.Entity.TrainingList;
import jp.crossabilitys.work.TrainingList.dto.TrainingRequest;
import jp.crossabilitys.work.TrainingList.service.TrainingService;

import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TrainingListController {
    /**
     * 訓練情報 Service
     */
    @Autowired
    private TrainingService trainingService;

    /**
     * 訓練情報一覧画面表示
     * @param model Model
     * @return 訓練情報一覧画面
     */
    @GetMapping("/training/list")
    public String displayTrainingList(Model model) {
        List<TrainingList> traininglist = trainingService.searchAll();
        model.addAttribute("traininglist", traininglist);
        return "training/traininglist";
    }

    /**
     * 訓練情報画面表示（新規登録）
     * @param model Model
     * @return 訓練情報画面
     */
    @GetMapping("/training/add")
    public String displayTrainingInfo(Model model) {
        model.addAttribute("trainingRequest", new TrainingRequest());
        return "training/traininginfo";
    }

    /**
     * 訓練情報新規登録
     * @param model model
     * @param trainingRequest リクエストデータ
     * @param result 入力チェックエラー情報
     * @return 訓練一覧画面/訓練情報画面
     */
    @RequestMapping(value = "/training/create", method = RequestMethod.POST)
    public String registTrainingInfo(Model model, @Validated @ModelAttribute TrainingRequest trainingRequest, BindingResult result,
                                     RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            // 入力チェックエラーの場合
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.trainingRequest", result);
            redirectAttributes.addFlashAttribute("trainingRequest", trainingRequest);
            // 訓練情報画面表示
            return "training/traininginfo";
        }
        // 訓練情報の登録
        trainingService.registTraining(trainingRequest);
        // 訓練一覧画面表示
        return "redirect:/training/list";
    }
    /**
     * 訓練情報画面表示 [更新]
     * （訓練情報画面に遷移し、登録済データを表示）
     * @param model Model
     * @param id 表示する訓練ID
     * @return 訓練情報登録画面
     */
    @GetMapping("/training/update/{trainingId}")
    public String displayTrainingUpdate(Model model, @PathVariable("trainingId") Long id) {
        TrainingList entity = trainingService.findById(id);
        TrainingRequest request = new TrainingRequest();

        request.setId(entity.getId());                                  // ID
        request.setTrainingname(entity.getTrainingname());              // 訓練名
        request.setRecruit_start_date(entity.getRecruit_start_date());	// 募集開始日
        request.setRecruit_end_date(entity.getRecruit_end_date());		// 募集終了日
        request.setSelection_date(entity.getSelection_date());			// 選考日
        request.setAcceptance_date(entity.getAcceptance_date());		// 合格発表日
        request.setStart_date(entity.getStart_date());					// 訓練開始日
        request.setEnd_date(entity.getEnd_date());						// 訓練終了日
        request.setStart_time(entity.getStart_time());					// 授業開始時間
        request.setEnd_time(entity.getEnd_time());						// 授業終了時間
        request.setTraining_hours(entity.getTraining_hours());			// 授業時間数
        request.setTotaltraining_hours(entity.getTotaltraining_hours());// 総授業時間数
        request.setDeleteflg(entity.isDeleteflg());                     // 削除フラグ

        model.addAttribute("trainingRequest", request);

        return "training/traininginfo";
    }

    /**
     * 訓練情報削除
     * @param model Model
     * @param id 削除する訓練ID
     * @return 訓練情報一覧画面
     */
    @GetMapping("/training/delete/{trainingId}")
    public String deleteTrainingInfo(Model model, @PathVariable("trainingId") Long id){
        // 訓練情報の登録(削除フラグ=true)
        trainingService.deleteTraining(id);

        return "redirect:/training/list";
    }
}
