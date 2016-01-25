<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="/js/copy/zclip.js"></script>
<script type="text/javascript" src="/js/qrcode/jquery.qrcode.min.js"></script>

<div class="modal fade" id="modalQr" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel3" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="qrLabel">二维码链接</h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="box col-md-12">
						<div class="box-content">
							

								<div class="form-group" style="margin-bottom:10px;">
									<label for="collegeidlabel" class="col-sm-3 control-label">二维码链接</label>
									<div class="col-sm-3" style="width:70%;">
										<input type="text" class="form-control" id="qrCodeUrl" readonly="readonly" /> 
									</div>
								</div>

								<div class="form-group" style="margin-left:200px;">
									<input type="button" id="copyBtn" value="复制到剪贴板" style="margin-top:10px;" />
								</div>

						</div>
						
						<div id="qrCodeShowArea" style="height:200px;width:220px;margin:auto;"></div>
					</div>
					<!--/span-->

				</div>
			</div>
			
		</div>
	</div>
</div>

<script type="text/javascript">

function registerCopy() {
	$('#copyBtn').unbind("zClip_afterCopy");
	$('#copyBtn').zclip({
        path: '/js/copy/ZeroClipboard10.swf',
        copy: function(){
        	return $("#qrCodeUrl").val();
        },
        afterCopy: function () { alert("复制成功");}
    });
}

function setQrUrl(goodsid,goodstype,sourcetype,collegeId,collegeName) {
	var qrCodeUrl = "xiaomaiapp://platformapi/startapp/?target=goodsdetail&goodsid=" + goodsid + "&goodstype=" + goodstype + "&sourcetype=" + sourcetype
			+ "&collegeId=" + collegeId + "&collegeName=" + encodeURIComponent(collegeName);
	$("#qrCodeUrl").val(qrCodeUrl);
	$("#qrCodeShowArea").empty();
	$("#qrCodeShowArea").qrcode({
		width : 200,
		height : 200,
		text : qrCodeUrl
		
	});
}

$(document).ready(function(){
	
	/**
	* 注册显示、关闭触发事件  详见：http://www.hello-code.com/blog/Bootstrap/201507/5233.html
	*	http://www.hipony.com/post-938.html
	*/
	
	//当模态框对用户可见时触发（将等待 CSS 过渡效果完成）
	$('#modalQr').on('shown.bs.modal', function () {
		registerCopy();
	})
	
	//当模态框完全对用户隐藏时触发
	$('#modalQr').on('hidden.bs.modal', function () {
		$('#copyBtn').zclip("remove"); 
	})
	
	
	
});


</script>
