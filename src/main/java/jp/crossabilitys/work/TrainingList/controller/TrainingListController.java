package jp.crossabilitys.work.TrainingList.controller;

import jp.crossabilitys.work.TrainingList.Entity.TeacherInfo;
import jp.crossabilitys.work.TrainingList.dto.*;
import jp.crossabilitys.work.TrainingList.Entity.TrainingInfo;
import jp.crossabilitys.work.TrainingList.Entity.TrainingSchedule;
import jp.crossabilitys.work.TrainingList.service.TeacherService;
import jp.crossabilitys.work.TrainingList.service.TrainingScheduleService;
import jp.crossabilitys.work.TrainingList.service.TrainingService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import one.cafebabe.businesscalendar4j.BusinessCalendar;
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
     * 訓練スケジュール Service
     */
    @Autowired
    private TrainingScheduleService scheduleService;
    /**
     * 講師情報 Service
     */
    @Autowired
    private TeacherService teacherService;

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
        // 対象の訓練情報取得
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

    /**
     * 訓練時間表画面表示
     * @param model Model
     * @param id 表示する訓練ID
     * @return 訓練時間表画面
     */
    @GetMapping("/training/timetable/{trainingId}")
    public String displayTimetable(Model model, @PathVariable("trainingId") Long id){

        // 対象の訓練情報取得
        TrainingInfo targetData = trainingService.findById(id);
        // 講師情報取得
        List<TeacherInfo> teacherlist = teacherService.searchAll();
        //最初の訓練日と最後の訓練日を取得
        List<LocalDate> firstAndLastDate = scheduleService.findFirstAndLastTrainingDate(id);



        // 表示用データ設定
        model.addAttribute("id", id);
        model.addAttribute("trainingname", targetData.getTrainingname());
        model.addAttribute("timetable", setTimeTable(targetData));
        model.addAttribute("timetable_firstDay",firstAndLastDate.get(0));
        model.addAttribute("timetable_lastDay",firstAndLastDate.get(1));
        model.addAttribute("teacherlist",teacherlist);
        return "training/timetable";
    }
    /**
     * 訓練時間表画面表示
     * @param model Model
     * @param id 表示する訓練ID
     * @return 訓練スケジュール編集画面
     */
    @GetMapping("/training/editschedule/{trainingId}")
    public String displayEditSchedule(Model model, @PathVariable("trainingId") Long id){

        // 対象の訓練情報取得
        TrainingInfo targetData = trainingService.findById(id);
        ScheduleData scheduleList = scheduleService.searchSchedule(id);

        // 講師情報取得
        List<TeacherInfo> teacherlist = teacherService.searchAll();

        // 表示用データ設定
//        model.addAttribute("training_id", id);
        model.addAttribute("trainingname", targetData.getTrainingname());
        model.addAttribute("schedulelist", scheduleList);
        model.addAttribute("teacherlist",teacherlist);

        return "training/editschedule";
    }

    /**
     * スケジュール登録
     * @param model Model
     * @param Request
     * @return 訓練時間表画面
     */
    @RequestMapping(value = "/schedule/edit", method = RequestMethod.POST)
    public String editSchedule(Model model, @ModelAttribute ScheduleData Request){

        // 訓練スケジュール更新
        scheduleService.updateAll(Request);

        return  "redirect:/training/timetable/"+ Request.getTraining_id().toString();
    }

    /**
     * timetableから講師の変更
     * @param trainingScheduleId 日毎の訓練情報のID
     * @param teacherId teacherId 講師のID
     * @return 訓練時間表画面
     */
    @RequestMapping(value = "/training/timetable/{trainingId}/edit_teacher",method = RequestMethod.POST)
    public String editTeacher(@PathVariable("trainingId") Long id,
                              @RequestParam("trainingScheduleId")Long trainingScheduleId,
                              @RequestParam("teacherId") Long teacherId) {

        scheduleService.updateTeacherInTimetable(trainingScheduleId,teacherId);

        return "redirect:/training/timetable/" + id;
    }

    /**
     * timetableから授業日の変更
     * @param id 訓練のID
     * @param trainingScheduleId 日毎の訓練情報のID
     * @param trainingDate 授業日
     * @return 訓練時間表画面
     */
    @RequestMapping(value = "/training/timetable/{trainingId}/edit_date",method = RequestMethod.POST)
    public String editDate(@PathVariable("trainingId") Long id,
                              @RequestParam("trainingScheduleId")Long trainingScheduleId,
                              @RequestParam(value="trainingDate", defaultValue = "1970-01-01") LocalDate trainingDate) {

        scheduleService.updateDateInTimetable(trainingScheduleId,trainingDate);

        return "redirect:/training/timetable/" + id;
    }

    /**
     * timetableから授業日の入れ替え
     * @param id 訓練のID
     * @param trainingScheduleId 日毎の訓練情報のID
     * @param trainingDate 授業日
     * @return 訓練時間表画面
     */
    @RequestMapping(value = "/training/timetable/{trainingId}/swap_date",method = RequestMethod.POST)
    public String swapDate(@PathVariable("trainingId") Long id,
                              @RequestParam("trainingScheduleId")Long trainingScheduleId,
                              @RequestParam(value="trainingDate", defaultValue = "1970-01-01") LocalDate trainingDate) {

        scheduleService.swapDateInTimetable(trainingScheduleId,trainingDate);

        return "redirect:/training/timetable/" + id;
    }

    /**
     * timetableからメモの変更
     * @param id 訓練のID
     * @param trainingScheduleId 日毎の訓練情報のID
     * @param memo メモ
     * @return 訓練時間表画面
     */
    @RequestMapping(value = "/training/timetable/{trainingId}/edit_memo",method = RequestMethod.POST)
    public String editTeacher(@PathVariable("trainingId") Long id,
                              @RequestParam("trainingScheduleId")Long trainingScheduleId,
                              @RequestParam("memo") String memo) {

        scheduleService.updateMemoInTimetable(trainingScheduleId, memo);

        return "redirect:/training/timetable/" + id;
    }

    /**
     * timetableから授業時間の変更
     * @param id 訓練のID
     * @param trainingScheduleId 日毎の訓練情報のID
     * @param trainingHours 授業時間
     * @return 訓練時間表画面
     */
    @RequestMapping(value = "/training/timetable/{trainingId}/edit_training_hours",method = RequestMethod.POST)
    public String editTrainingHours(@PathVariable("trainingId") Long id,
                              @RequestParam("trainingScheduleId")Long trainingScheduleId,
                              @RequestParam("trainingHours") String trainingHours) {

        scheduleService.updateTrainingHoursInTimetable(trainingScheduleId, trainingHours);

        return "redirect:/training/timetable/" + id;
    }

    /**
     * 訓練時間表表示用データ設定
     * @param targetData 訓練スケジュール
     * @return 訓練時間表表示用データ
     */
    private List<MonthlyTraining> setTimeTable(TrainingInfo targetData){
        final List<MonthlyTraining> timeTable = new ArrayList<>();
        List<DailyTraining> dailyTrainings = new ArrayList<>();
        MonthlyTraining monthlyTraining = new MonthlyTraining();
        int currentMonth = 0;
        int totalDays = 0;
        int totalHours = 0;

        // 祝日
        BusinessCalendar holidays = BusinessCalendar.newBuilder().holiday(BusinessCalendar.JAPAN.PUBLIC_HOLIDAYS).build();

        // 訓練スケジュール取得
        List<TrainingSchedule> trainingSchedule = targetData.getTrainingSchedule();
        // 訓練期間ループ
        for(int i = 0; i < trainingSchedule.size(); ++i){
            // 1日の訓練データ取得
            TrainingSchedule oneDay = trainingSchedule.get(i);
            // 作業月取得
            int month = oneDay.getTraining_date().getMonth().getValue();
            // 訓練初日の場合
            if (i==0){
                /* --- 月初から訓練開始日までのデータ作成 --- */
                setDummyDataDaily(dailyTrainings, oneDay.getTraining_date().getDayOfMonth()-1);
/* 月初から訓練開始日までのデータで、曜日を入れたい場合はココのコメントを復活
                // 月初日から訓練開始日前日までループ
                for (LocalDate date = oneDay.getTraining_date().withDayOfMonth(1); date.isBefore(oneDay.getTraining_date()); date = date.plusDays(1)){
                    // 1日の訓練データ作成
                    final DailyTraining dailyTraining = new DailyTraining();
                    dailyTraining.setTraining_date(oneDay.getTraining_date());
                    dailyTraining.setTraining_hours(0);
                    dailyTraining.setBackcolor(9);

                    dailyTrainings.add(dailyTraining);
                }
*/
                currentMonth = month;   // 作業月設定
            }
            // 月が変わった場合
            if (currentMonth != month){
                /* --- 月末から31日までのデータ作成 --- */
                int endDay = oneDay.getTraining_date().minusDays(1).getDayOfMonth();
                if (endDay < 31){
                    setDummyDataDaily(dailyTrainings, (31 - endDay));
                }
                /* --- ひと月分のデータ作成 --- */
                monthlyTraining = new MonthlyTraining();
                monthlyTraining.setMonth(currentMonth);
                monthlyTraining.setTotalTraining_days(totalDays);
                monthlyTraining.setTotalTraining_hours(totalHours);
                monthlyTraining.setTrainings(dailyTrainings);
                timeTable.add(monthlyTraining);
                dailyTrainings = new ArrayList<>();
                currentMonth = month;
                totalDays = 0;
                totalHours = 0;
            }
            // 1日の訓練データ作成
            final DailyTraining dailyTraining = new DailyTraining();
            dailyTraining.setId(oneDay.getId());
            dailyTraining.setTraining_date(oneDay.getTraining_date());
            dailyTraining.setTraining_hours(oneDay.getTraining_hours());
            if (oneDay.getTraining_hours() > 0 && oneDay.getTeacher_id() == null) {
                // 訓練日で講師割当がない場合、背景色「講師登録なし」設定
                dailyTraining.setBackcolor(setBackColorCell(9));
            } else if(holidays.isHoliday(oneDay.getTraining_date())){
                // 祝日
                dailyTraining.setBackcolor(setBackColorCell(8));
            } else {
                dailyTraining.setBackcolor(setBackColorCell(oneDay.getTraining_date().getDayOfWeek().getValue()));
                if (oneDay.getTraining_hours() == 0 && oneDay.getTraining_date().getDayOfWeek().getValue() <= 5) {
                    dailyTraining.setBackcolor(setBackColorCell(10));
                }
            }
            dailyTrainings.add(dailyTraining);

            // 訓練日の場合、訓練日数・訓練時間を加算
            if (oneDay.getTraining_hours() > 0){
                totalDays += 1;
                totalHours += oneDay.getTraining_hours();
            }

            // 訓練最終日の場合
            if ( i>=trainingSchedule.size()-1){
                /* --- 訓練修了日から月末(31日分)までのデータ作成 --- */
                int endDay = oneDay.getTraining_date().getDayOfMonth();
                if (endDay < 31){
                    setDummyDataDaily(dailyTrainings, (31 - endDay));
                }
                /* --- ひと月分のデータ作成 --- */
                monthlyTraining = new MonthlyTraining();
                monthlyTraining.setMonth(currentMonth);
                monthlyTraining.setTotalTraining_days(totalDays);
                monthlyTraining.setTotalTraining_hours(totalHours);
                monthlyTraining.setTrainings(dailyTrainings);
                timeTable.add(monthlyTraining);
            }
        }
        return timeTable;
    }
    /**
     * 訓練スケジュールダミーデータ作成
     * 1日分の訓練スケジュールデータをデータ数分作成する
     * @param dailyTrainings 日別訓練スケジュールリスト
     * @param dataCount データ数
     */
    private void setDummyDataDaily(List<DailyTraining> dailyTrainings, int dataCount){

        for (int i = 0; i < dataCount; i++) {
            final DailyTraining dailyTraining = new DailyTraining();
            dailyTraining.setTraining_date(null);
            dailyTraining.setTraining_hours(0);
            dailyTraining.setBackcolor(setBackColorCell(0));

            dailyTrainings.add(dailyTraining);
        }
    }

    /**
     * 背景色設定
     * 訓練時間表画面のセル背景色を設定
     * @param index 1～7:曜日のint値, 0:対象外, 8:祝日, 9:講師登録なし
     * @return
     */
    private String setBackColorCell(int index){
        switch (index){
            case 0:     // 対象外
                return "outofdata";
            case 6,7:   // 土日
                return "holiday";
            case 8:     // 祝日
                return "nholiday";
            case 9:     // 講師登録なし
                return "nodata";
            case 10:     // 平日かつ授業時間0
                return "sHoliday";
            default:
                return "";
        }
    }
}
