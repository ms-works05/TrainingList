package jp.crossabilitys.work.TrainingList.controller;

import jp.crossabilitys.work.TrainingList.Entity.TeacherInfo;
import jp.crossabilitys.work.TrainingList.dto.TeacherRequest;
import jp.crossabilitys.work.TrainingList.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TeacherController {
    /**
     * 講師情報 Service
     */
    private final TeacherService teacherService;
    /**
     * 講師情報一覧画面表示
     * @param model Model
     * @return 講師情報一覧画面
     */
    @GetMapping("/teacher/list")
    public String displayTeacherList(Model model) {
        List<TeacherInfo> teacherlist = teacherService.searchAll();
        model.addAttribute("teacherlist", teacherlist);
        return "teacher/teacherlist";
    }
    /**
     * 講師情報画面表示（新規登録）
     * @param model Model
     * @return 講師情報画面
     */
    @GetMapping("/teacher/add")
    public String displayTrainingInfo(Model model) {
        model.addAttribute("teacherRequest", new TeacherRequest());
        return "teacher/teacherinfo";
    }
    /**
     * 講師情報新規登録
     * @param model model
     * @param teacherRequest リクエストデータ
     * @param result 入力チェックエラー情報
     * @return 講師一覧画面/講師情報画面
     */
    @RequestMapping(value = "/teacher/create", method = RequestMethod.POST)
    public String registTeacherInfo(Model model, @Validated @ModelAttribute TeacherRequest teacherRequest, BindingResult result,
                                     RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            // 入力チェックエラーの場合
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.teacherRequest", result);
            redirectAttributes.addFlashAttribute("teacherRequest", teacherRequest);
            // 講師情報画面表示
            return "teacher/teacherinfo";
        }
        // 講師情報の登録
        teacherService.registTeacher(teacherRequest);
        // 講師一覧画面表示
        return "redirect:/teacher/list";
    }
    /**
     * 講師情報画面表示 [更新]
     * （講師情報画面に遷移し、登録済データを表示）
     * @param model Model
     * @param id 表示する講師ID
     * @return 講師情報登録画面
     */
    @GetMapping("/teacher/update/{teacherId}")
    public String displayTeacherUpdate(Model model, @PathVariable("teacherId") Long id) {
        TeacherInfo entity = teacherService.findById(id);
        TeacherRequest request = new TeacherRequest();

        request.setId(entity.getId());
        request.setName(entity.getName());

        model.addAttribute("teacherRequest", request);

        return "teacher/teacherinfo";
    }
    /**
     * 講師情報削除
     * @param model Model
     * @param id 削除する講師ID
     * @return 講師情報一覧画面
     */
    @GetMapping("/teacher/delete/{teacherId}")
    public String deleteTeacherInfo(Model model, @PathVariable("teacherId") Long id){
        // 講師情報の登録(削除フラグ=true)
        teacherService.deleteTeacher(id);

        return "redirect:/teacher/list";
    }

}
