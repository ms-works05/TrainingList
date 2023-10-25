//コンテキストメニューの表示制御
function showContextMenu(e){
    document.getElementById('contextmenu').style.left=e.pageX+"px";
    document.getElementById('contextmenu').style.top=e.pageY+"px";
    document.getElementById('contextmenu').style.display="block";
}

function showMenu(id) {
    if(0 < id){
        let url = location.pathname
        document.getElementById("editTeacher").action = url +'/editteacher';
        document.getElementById("update_TrainingScheduleId").value = id;
        document.body.addEventListener('contextmenu',showContextMenu(event));//右クリックイベントの追加
//        var element = document.getElementById("update_teacher")
//        for(i = 0; i < element.length; i++){
//            if(element.options[i].value == id){
//                element.options[i].selected;
//            }
//        }
    }
    document.addEventListener('click',function (e){ //メニューボックス以外をクリックでコンテキストメニューを閉じる
    document.getElementById('contextmenu').style.display="none";
    });
    document.body.removeEventListener('contextmenu',function(e){}) //右クリックイベントの削除（これを入れないとbody内全てでメニューが開けてしまうため）
}