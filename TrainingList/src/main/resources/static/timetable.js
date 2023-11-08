window.onload = function() {
    element = document.getElementsByClassName("hover");
    for(i = 0; i < element.length; i++){
        element[i].addEventListener('mouseover', function(event){ //マウスオーバーイベントの追加
            mouseOver(event.target)
        });
        element[i].addEventListener('mouseout', function(event){ //マウスアウトイベントの追加
        mouseOut(event.target)
        });
    }
}

// コンテキストメニューの表示位置制御
function showContextMenu(e){
    document.getElementById('contextmenu').style.left=e.pageX+"px";
    document.getElementById('contextmenu').style.top=e.pageY+"px";
    document.getElementById('contextmenu').style.display="block";
}

// コンテキストメニューの表示制御
function showMenu(id) {
    if(0 < id){
        document.body.addEventListener('contextmenu',showContextMenu(event)); //右クリックイベントの追加
        var element = document.getElementById("editTeacherDialogButton") //コンテキストメニュー内講師の変更ボタンのonclick属性の変更
        element.setAttribute('onclick',"editTeacherDialog(" + id + ");")
        var element = document.getElementById("editTrainingDateDialogButton") //コンテキストメニュー内授業日の変更ボタンのonclick属性の変更
        element.setAttribute('onclick',"editTrainingDateDialog(" + id + ");")
        var element = document.getElementById("swapTrainingDateDialogButton") //コンテキストメニュー内授業日の入れ替えボタンのonclick属性の変更
        element.setAttribute('onclick',"swapTrainingDateDialog(" + id + ");")
        var element = document.getElementById("editMemoDialogButton") //コンテキストメニュー内メモの変更ボタンのonclick属性の変更
        element.setAttribute('onclick',"editMemoDialog(" + id + ");")
        var element = document.getElementById("editTrainingHoursDialogButton") //コンテキストメニュー内メモの変更ボタンのonclick属性の変更
        element.setAttribute('onclick',"editTrainingHoursDialog(" + id + ");")
    }
    document.addEventListener('click',function (e){ //メニューボックス以外をクリックでコンテキストメニューを閉じる
    document.getElementById('contextmenu').style.display="none";
    });
    document.body.removeEventListener('contextmenu',function(e){}) //右クリックイベントの削除（これを入れないとbody内全てでメニューが開けてしまうため）
}

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
    document.getElementById("updateTeacher_TrainingScheduleId").value = id;
    var map = getTrainingSchedule(id);//ajax実行functionを呼び出しTrainingSchedule関連Mapデータを取得
    document.getElementById("editTeacherDialogTitle").innerText = map.get("trainingDate") + map.get("weekday") + "の講師割り当て変更" //ダイアログ見出し文の変更
    var element = document.getElementById("update_teacher");
    for(i = 0; i < element.length ; i++){
        if(element.options[i].value == map.get("teacherId")){
            element.options[i].selected = true; //元々講師割り当てされている場合はその講師名をselected
        }
    }
}

// ツールチップの表示
//function mouseover(id){
//    if(0 < id){
//        const tooltip = document.getElementById(id);
//        var sethover = 0;
//        $('#'+id).hover(function() {
//		sethover = setTimeout(function(){showTooltip(tooltip,id)},1200);
//	    }, function() {
//		    clearTimeout(sethover);
//	    });
//    }
//}

// mouseOverしたらsetTimeoutを実行
function mouseOver(element){
  timeoutId = setTimeout(function(){showTooltip(element)}, 500)//0.5秒間マウスオーバーでツールチップ表示
}

// mouseOutしたらsetTimeoutをクリア
function mouseOut(element){
tooltip_back = document.getElementById("tooltip_background");
    tooltip_back.hidden = true; //表示してるツールチップの非表示
  clearTimeout(timeoutId) //マウスアウトで秒数カウントのリセット
}


function showTooltip(element){
    id = element.id
    if(0 < id){
        var map = getTrainingSchedule(id);//ajax実行functionを呼び出しTrainingSchedule関連Mapデータを取得
        var tooltipDate = "訓練日 : " + map.get("trainingDate") + map.get("weekday");
        var tooltipMemo = "メモ : " + map.get("memo");
        var tooltipTeacher = "講師名 : "　+ map.get("teacherName");
        tooltip = document.getElementById("tooltip");
        var clientRect = element.getBoundingClientRect();
        // 画面の左端から、要素の左端までの距離をもとにツールチップにx座標を決定
        var x = clientRect.left;
        // 画面の上端から、要素の上端までの距離をもとにツールチップにx座標を決定
        var y = clientRect.top - 100;
        tooltip.innerText = tooltipDate + "\n" + tooltipMemo + "\n" + tooltipTeacher;
        tooltip_back = document.getElementById("tooltip_background");
//        tooltip.style.left = x + 'px';
//        tooltip.style.top = y + 'px';
        tooltip_back.style.left = x + 'px';
        tooltip_back.style.top = y + 'px';
        tooltip_back.hidden = false;
    }
}

// 授業日変更ダイアログの表示
function editTrainingDateDialog(id){
    document.getElementById("updateDate_TrainingScheduleId").value = id;
    var map = getTrainingSchedule(id);//ajax実行functionを呼び出しTrainingSchedule関連Mapデータを取得
    document.getElementById("editTrainingDateDialogTitle").innerText = map.get("trainingDate") + map.get("weekday") + "の授業日変更" //ダイアログ見出し文の変更
    var element = document.getElementById("update_date");
    element.value = map.get("trainingDate");
}

// 授業日入れ替えダイアログの表示
function swapTrainingDateDialog(id){
    document.getElementById("swapDate_TrainingScheduleId").value = id;
    var map = getTrainingSchedule(id);//ajax実行functionを呼び出しTrainingSchedule関連Mapデータを取得
    document.getElementById("swapTrainingDateDialogTitle").innerText = map.get("trainingDate") + map.get("weekday") + "の授業日入れ替え" //ダイアログ見出し文の変更
    var element = document.getElementById("swap_date");
    element.value = map.get("trainingDate");
}

// 授業日入れ替えダイアログの表示
function editMemoDialog(id){
    document.getElementById("updateMemo_TrainingScheduleId").value = id;
    var map = getTrainingSchedule(id);//ajax実行functionを呼び出しTrainingSchedule関連Mapデータを取得
    document.getElementById("editMemoDialogTitle").innerText = map.get("trainingDate") + map.get("weekday") + "の授業日入れ替え" //ダイアログ見出し文の変更
    var element = document.getElementById("update_memo");
    element.value = map.get("memo");
}

// 講師変更ダイアログの表示
function editTrainingHoursDialog(id){
    document.getElementById("updateTrainingHours_TrainingScheduleId").value = id;
    var map = getTrainingSchedule(id);//ajax実行functionを呼び出しTrainingSchedule関連Mapデータを取得
    document.getElementById("editTrainingHoursDialogTitle").innerText = map.get("trainingDate") + map.get("weekday") + "の講師割り当て変更" //ダイアログ見出し文の変更
    var element = document.getElementById("update_trainingHours");
    element.value = map.get("trainingHours");
}

