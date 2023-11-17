package jp.crossabilitys.work.TrainingList.controller;

import jp.crossabilitys.work.TrainingList.Entity.Consignor;
import jp.crossabilitys.work.TrainingList.dto.ConsignorRequest;
import jp.crossabilitys.work.TrainingList.service.ConsignorService;
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
public class ConsignorController {
    /**
     * 種別(委託元) Service
     */
    private final ConsignorService consignorService;
    /**
     * 種別(委託元)一覧画面表示
     * @param model Model
     * @return 種別(委託元)一覧画面
     */
    @GetMapping("/consignor/list")
    public String displayConsignorList(Model model) {
        List<Consignor> consignorlist = consignorService.searchAll();
        model.addAttribute("consignorlist", consignorlist);
        return "consignor/consignorlist";
    }
    /**
     * 種別(委託元)画面表示（新規登録）
     * @param model Model
     * @return 種別(委託元)画面
     */
    @GetMapping("/consignor/add")
    public String displayConsignor(Model model) {
        model.addAttribute("consignorRequest", new ConsignorRequest());
        return "consignor/consignorinfo";
    }
    /**
     * 種別(委託元)新規登録
     * @param model model
     * @param consignorRequest リクエストデータ
     * @param result 入力チェックエラー情報
     * @return 種別(委託元)一覧画面/種別(委託元)登録画面
     */
    @RequestMapping(value = "/consignor/create", method = RequestMethod.POST)
    public String registConsignorInfo(Model model, @Validated @ModelAttribute ConsignorRequest consignorRequest, BindingResult result,
                                      RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            // 入力チェックエラーの場合
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.consignorRequest", result);
            redirectAttributes.addFlashAttribute("consignorRequest", consignorRequest);
            // 種別(委託元)画面表示
            return "consignor/consignorinfo";
        }
        // 種別(委託元)の登録
        consignorService.registConsignor(consignorRequest);
        // 種別(委託元)一覧画面表示
        return "redirect:/consignor/list";
    }
    /**
     * 種別(委託元)登録画面表示 [更新]
     * （種別(委託元)登録画面に遷移し、登録済データを表示）
     * @param model Model
     * @param id 表示する種別(委託元)ID
     * @return 種別(委託元)登録画面
     */
    @GetMapping("/consignor/update/{consignorId}")
    public String displayConsignorUpdate(Model model, @PathVariable("consignorId") Long id) {
        Consignor entity = consignorService.findById(id);
        ConsignorRequest request = new ConsignorRequest();

        request.setId(entity.getId());
        request.setName1(entity.getName1());
        request.setName2(entity.getName2());

        model.addAttribute("consignorRequest", request);

        return "consignor/consignorinfo";
    }
    /**
     * 種別(委託元)削除
     * @param model Model
     * @param id 削除する種別(委託元)ID
     * @return 種別(委託元)一覧画面
     */
    @GetMapping("/consignor/delete/{consignorId}")
    public String deleteConsignor(Model model, @PathVariable("consignorId") Long id){
        // 種別(委託元)の削除
        consignorService.deleteConsignor(id);

        return "redirect:/consignor/list";
    }
}
