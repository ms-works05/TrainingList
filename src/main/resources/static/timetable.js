//ページ読み込み時の処理
window.onload = function() {
    document.querySelectorAll(".hover").forEach(function(e) {
        e.addEventListener('mouseover', function(e){ //マウスオーバーイベントの追加
            mouseOver(e.target)
        });
        e.addEventListener('mouseout', function(e){ //マウスアウトイベントの追加
            mouseOut(e.target)
        });
    });
    document.querySelectorAll(".menu-target").forEach(function(e) {
        e.addEventListener('contextmenu',showContextMenu); //右クリックイベントの追加
    });
    document.addEventListener('click',function (){ //メニューボックス以外をクリックでコンテキストメニューを閉じるイベントの追加
        document.getElementById('contextmenu').style.display="none";
    });
}

// コンテキストメニューの表示位置制御
function showContextMenu(e){
    contextmenu.style.left=e.clientX+"px";
    contextmenu.style.top=e.clientY+"px";
    contextmenu.style.display="block";
}

// コンテキストメニューの表示制御
function showMenu(id) {
    if(0 < id){
        //コンテキストメニュー内onclick属性の変更
        editTeacherDialogButton.setAttribute('onclick',"editTeacherDialog(" + id + ");");
        editTrainingDateDialogButton.setAttribute('onclick',"editTrainingDateDialog(" + id + ");");
        swapTrainingDateDialogButton.setAttribute('onclick',"swapTrainingDateDialog(" + id + ");");
        editMemoDialogButton.setAttribute('onclick',"editMemoDialog(" + id + ");");
        editTrainingHoursDialogButton.setAttribute('onclick',"editTrainingHoursDialog(" + id + ");");
    }
}

//ajaxでtrainingScheduleを取得し必要なデータをMap型で返す
function getTrainingSchedule(id){
if(0 < id){
        var map = new Map();
        $.ajax({
          type: "POST",
          url: "/get_training_schedule",
          async : false,
          data: { scheduleId:id }
        }).done(function( msg ) {
            var str = JSON.stringify(msg);
            var scheduleId = msg[0];
            var trainingHours= msg[1]
            var trainingDate = msg[2];
            var teacherId = msg[3];
            var memo = msg[4];
            var teacherName = msg[5];

            const days = ["(日)", "(月)", "(火)", "(水)", "(木)", "(金)", "(土)"];
            const date = new Date(trainingDate);
            const weekday = days[date.getDay()];　//曜日の取得
            map.set("scheduleId",scheduleId);
            map.set("trainingHours",trainingHours);
            map.set("trainingDate",trainingDate);
            map.set("teacherId",teacherId);
            map.set("memo",memo);
            map.set("date",date);
            map.set("weekday",weekday);
            map.set("teacherName",teacherName);
        });
        return map;
    }
}

// 講師変更ダイアログの表示
function editTeacherDialog(id){
    updateTeacher_TrainingScheduleId.value = id;
    var map = getTrainingSchedule(id);//ajax実行functionを呼び出しTrainingSchedule関連Mapデータを取得
    editTeacherDialogTitle.innerText = map.get("trainingDate") + map.get("weekday") + "の講師割り当て変更" //ダイアログ見出し文の変更
    var element = document.getElementById("update_teacher");
    for(i = 0; i < element.length ; i++){
        if(element.options[i].value == map.get("teacherId")){
            element.options[i].selected = true; //元々講師割り当てされている場合はその講師名をselected
        }
    }
}


// mouseOverしたらsetTimeoutを実行
function mouseOver(e){
  timeoutId = setTimeout(function(){showTooltip(e)}, 500)//0.5秒間マウスオーバーでツールチップ表示
}

// mouseOutしたらsetTimeoutをクリア
function mouseOut(e){
    tooltip_background.hidden = true; //表示してるツールチップの非表示
    clearTimeout(timeoutId) //マウスアウトで秒数カウントのリセット
}

//timetable上でマウスオーバーした時に表示されるツールチップを制御
function showTooltip(element){
    id = element.id
    if(0 < id){
        var map = getTrainingSchedule(id);//ajax実行functionを呼び出しTrainingSchedule関連Mapデータを取得
        var tooltipDate = "訓練日 : " + map.get("trainingDate") + map.get("weekday");
        var tooltipMemo = "メモ : " + map.get("memo");
        var tooltipTeacher = "講師名 : "　+ map.get("teacherName");
        var clientRect = element.getBoundingClientRect();
        // 画面の左端から、要素の左端までの距離をもとにツールチップにx座標を決定
        var x = clientRect.left;
        // 画面の上端から、要素の上端までの距離をもとにツールチップにx座標を決定
        var y = clientRect.top - 100;
        tooltip.innerText = tooltipDate + "\n" + tooltipMemo + "\n" + tooltipTeacher;
        tooltip_background.style.left = x + 'px';
        tooltip_background.style.top = y + 'px';
        tooltip_background.hidden = false;
    }
}

// 授業日変更ダイアログの表示
function editTrainingDateDialog(id){
    updateDate_TrainingScheduleId.value = id;
    var map = getTrainingSchedule(id);//ajax実行functionを呼び出しTrainingSchedule関連Mapデータを取得
    editTrainingDateDialogTitle.innerText = map.get("trainingDate") + map.get("weekday") + "の授業日変更" //ダイアログ見出し文の変更
    update_date.value = map.get("trainingDate");
}

// 授業日入れ替えダイアログの表示
function swapTrainingDateDialog(id){
    swapDate_TrainingScheduleId.value = id;
    var map = getTrainingSchedule(id);//ajax実行functionを呼び出しTrainingSchedule関連Mapデータを取得
    swapTrainingDateDialogTitle.innerText = map.get("trainingDate") + map.get("weekday") + "の授業日入れ替え" //ダイアログ見出し文の変更
    var element = document.getElementById("swap_date");
    element.value = map.get("trainingDate");
}

// メモ変更ダイアログの表示
function editMemoDialog(id){
    updateMemo_TrainingScheduleId.value = id;
    var map = getTrainingSchedule(id);//ajax実行functionを呼び出しTrainingSchedule関連Mapデータを取得
    editMemoDialogTitle.innerText = map.get("trainingDate") + map.get("weekday") + "のメモ変更" //ダイアログ見出し文の変更
    var element = document.getElementById("update_memo");
    element.value = map.get("memo");
}

// 講師変更ダイアログの表示
function editTrainingHoursDialog(id){
    updateTrainingHours_TrainingScheduleId.value = id;
    var map = getTrainingSchedule(id);//ajax実行functionを呼び出しTrainingSchedule関連Mapデータを取得
    editTrainingHoursDialogTitle.innerText = map.get("trainingDate") + map.get("weekday") + "の講師割り当て変更" //ダイアログ見出し文の変更
    update_trainingHours.value = map.get("trainingHours");
}

