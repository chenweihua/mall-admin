<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/ztree/demo.css" type="text/css">
<link rel="stylesheet" href="/css/ztree/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="/css/ztree/xiaomaimall_ztree.css"
	type="text/css">

<script type="text/javascript" src="/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript"
	src="/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<div class="modal fade" id="treeEdit" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel3" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">范围设置</h4>
			</div>
			<div class="modal-body">
				<input type="hidden" id="storageId4Region">
				<div class="row">
					<div class="box col-md-12">
						<div class="box-content">
							<div id="treeWrap">
								<ul id="selectTree" class="ztree"></ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" id="treeSubmit">提交</button>
			</div>
		</div>
	</div>
</div>
<script>
var setting = {
		check: {
			enable: true,
			chkboxType: { "Y": "ps", "N": "ps" }
		},
		data: {
			simpleData: {
				enable: true
			}
		}
		
};	
</script>