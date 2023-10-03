package jp.crossabilitys.work.TrainingList.controller;

import jp.crossabilitys.work.TrainingList.Entity.TrainingInfo;
import jp.crossabilitys.work.TrainingList.dto.TrainingRequest;
import jp.crossabilitys.work.TrainingList.service.TrainingService;

import java.util.List;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
        List<TrainingInfo> traininglist = trainingService.searchAll();
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
        TrainingInfo entity = trainingService.findById(id);
        TrainingRequest request = new TrainingRequest();

        request.setId(entity.getId());
        request.setTrainingname(entity.getTrainingname());
        request.setRecruit_start_date(entity.getRecruit_start_date());
        request.setRecruit_end_date(entity.getRecruit_end_date());
        request.setSelection_date(entity.getSelection_date());
        request.setAcceptance_date(entity.getAcceptance_date());
        request.setStart_date(entity.getStart_date());
        request.setEnd_date(entity.getEnd_date());
        request.setStart_time(entity.getStart_time());
        request.setEnd_time(entity.getEnd_time());
        request.setTraining_hours(entity.getTraining_hours());
        request.setTotaltraining_hours(entity.getTotaltraining_hours());
        request.setDeleteflg(entity.isDeleteflg());

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
    @GetMapping("/training/timetable/{trainingId}")
    public String displayTimetable(Model model, @PathVariable("trainingId") Long id){
        // 仮
        TrainingInfo timetable = trainingService.findById(id);
        model.addAttribute("timetable", timetable);

        return "training/timetable";
    }
}
