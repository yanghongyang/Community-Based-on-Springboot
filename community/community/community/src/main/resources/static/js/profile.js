$(function(){
	$(".follow-btn").click(follow);
});

function follow() {
	var btn = this;
	if($(btn).hasClass("btn-info")) {
		// 关注TA
		$.post(
		    CONTEXT_PATH + "/follow",
		    {"entityType" : 3 , "entityId" : $(btn).prev().val()},
		    function(data) {
		        data = $.parseJSON(data);
		        if(data.code == 0) {
		            // 成功的话，就修改样式
		            window.location.reload();
		        } else {
                    // 否则返回提示信息
                    alert(data.msg);
		        }
		    }
		);
		//$(btn).text("已关注").removeClass("btn-info").addClass("btn-secondary");
	} else {
		// 取消关注
		$.post(
            CONTEXT_PATH + "/unfollow",
            {"entityType" : 3 , "entityId" : $(btn).prev().val()},
            function(data) {
                data = $.parseJSON(data);
                if(data.code == 0) {
                    // 成功的话，就修改样式
                    window.location.reload();
                } else {
                    // 否则返回提示信息
                    alert(data.msg);
                }
            }
        );
		//$(btn).text("关注TA").removeClass("btn-secondary").addClass("btn-info");
	}
}