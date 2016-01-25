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

	<div class="box-content">
		<div id="treeWrap">
			<ul id="selectTree" class="ztree"></ul>
		</div>
	</div>
	<div id="resultData">
	
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
 /*初始化树数据格式*/
/* var zNodes =[
		{ id:1, pId:0, name:"RDC", open:true,icon:"/img/ztree/rdc.png"},
		{ id:11, pId:1, name:"RDC所有仓库",open:true,icon:"/img/ztree/storage.png"},
		{ id:111, pId:11, name:"北京仓",icon:"/img/ztree/storage.png"},
		{ id:112, pId:11, name:"武汉仓",icon:"/img/ztree/storage.png"},
		{ id:1111, pId:111, name:"北京大学",icon:"/img/ztree/college.png"},
		{ id:1112, pId:111, name:"清华大学",icon:"/img/ztree/college.png"},
		{ id:2, pId:0, name:"LDC", checked:true, open:true,icon:"/img/ztree/ldc.png"},
		{ id:21, pId:2, name:"所有城市",open:true,icon:"/img/ztree/storage.png"},
		{ id:221, pId:21, name:"北京市", checked:true,icon:"/img/ztree/storage.png"},
		{ id:222, pId:21, name:"上海市",icon:"/img/ztree/storage.png"},
		{ id:2221, pId:222, name:"小麦店1",icon:"/img/ztree/storage.png"},
		{ id:2222, pId:222, name:"小麦店2",icon:"/img/ztree/storage.png"},
		{ id:23, pId:2222, name:"北京大学",icon:"/img/ztree/college.png"}
	]; */
</script>
