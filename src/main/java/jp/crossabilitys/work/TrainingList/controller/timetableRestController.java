package jp.crossabilitys.work.TrainingList.controller;

import jp.crossabilitys.work.TrainingList.Entity.TeacherInfo;
import jp.crossabilitys.work.TrainingList.Entity.TrainingSchedule;
import jp.crossabilitys.work.TrainingList.service.TeacherService;
import jp.crossabilitys.work.TrainingList.service.TrainingScheduleService;
import jp.crossabilitys.work.TrainingList.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class timetableRestController {
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
     * 日毎の訓練情報取得
     * @param model Model
     * @param scheduleId 訓練情報のID
     * @return 訓練情報
     */
    @RequestMapping(value = "/get_training_schedule",method = RequestMethod.POST)
    public List<Object> editTeacherDialog(Model model,
                                  @RequestParam("scheduleId") long scheduleId
                                    ){
        TrainingSchedule trainingSchedule;
        trainingSchedule = scheduleService.searchOneDaySchedule(scheduleId).get();
        Long teacherId = trainingSchedule.getTeacher_id();
        TeacherInfo teacherName = new TeacherInfo();
        if (teacherId != null) {
            teacherName = teacherService.findById(teacherId);
        }
        List<Object> list = new ArrayList<>();
        list.add(trainingSchedule.getId());
        list.add(trainingSchedule.getTraining_hours());
        list.add(trainingSchedule.getTraining_date());
        list.add(trainingSchedule.getTeacher_id());
        list.add(trainingSchedule.getMemo());
        list.add(teacherName.getName());

        return list;
    }
}
