$(document).ready(function(){
	$(".function").click(function(){
		for (var i=1;i<=5;i++){
			var page=".page"+i;
			$(page).hide();
		}
		var page=".page"+this.id
		if (this.id==3){
			getFileList(1)
		}
		$(page).show()
	});

});

function getFileList(page) {
	$.ajax({
		"type" : 'post',
		"url" : "getfilelist",
		"dataType" : "json",
		"data" : {
			"page":page
		},
		"success" : function(resp) {

			var filename;
			var filepath;
			var time;
			var id;
			var h="";
			for (var i=0;i<resp.length;i++){
				id=resp[i]["id"]
				filename=resp[i]["filename"]
				filepath=resp[i]["filepath"]
				time=resp[i]["time"]
			}

		},
		"error":function(emsg){
			//返回失败信息-----》emsg
		}
	});
}