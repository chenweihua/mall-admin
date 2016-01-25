<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<style>

.firstCategory {
	border: 1px solid #ccc;
}

.firstCategory li{
	line-height:40px;
	height:40px;
	border-bottom: 1px dashed #ccc;
	padding-left:20px;
	cursor: pointer;
}

.firstCategory li:hover{
	background-color: #87CEFA;
}

.selected{
	background-color: #87CEFA;
}

h4{
	text-align: center;
}

.submit{
	margin:20px 0;
}

.submit button{
	margin-left:30px;
}

</style>

<%@ include file="../header.jsp"%>

<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-item"></i>库存管理->货品添加->属性选择
				</h2>
				<div class="box-icon">
					 <a href="#" class="btn btn-minimize btn-round btn-default">
					 	<i class="glyphicon glyphicon-chevron-up"></i>
					 </a> 
				</div>
			</div>
			<div class="box-content">
				<div class="row">
					<div id="firstCategory" class="col-md-2">
						<h4>一级类别</h4>
			           <ul class="nav firstCategory">
			              <c:forEach var="category" items="${firstCategoryList}">
			              	  <li categoryId="${category.propertyCategoryId}">${category.propertyCategoryName}</li>
			              </c:forEach>
			           </ul>
		       		</div>
					<div id="secondCategory" class="col-md-2" >
						<h4>二级类别</h4>
			           <ul class="nav firstCategory" >
			              <c:forEach var="category" items="${secondCategoryList}">
			              	  <li categoryId="${category.propertyCategoryId}">${category.propertyCategoryName}</li>
			              </c:forEach>
			           </ul>
		       		</div>
					<div id="thirdCategory" class="col-md-2" >
						<h4>三级类别</h4>
			           <ul class="nav firstCategory">
			              <c:forEach var="category" items="${thirdCategoryList}">
			              	  <li categoryId="${category.propertyCategoryId}">${category.propertyCategoryName}</li>
			              </c:forEach>
			           </ul>
		       		</div>
				</div>
				<div class="row">
					<div class=" submit col-md-8">
						<button type="button" class="btn btn-default" id="cancel">取消</button>
						<button type="button" class="btn btn-primary" id="nextButton">下一步</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="../footer.jsp"%>

<script type="text/javascript">
var storageId = ${storageId};
var firstCategoryId = -1;
var categoryId = -1;

$('#firstCategory').on('click','li',function(){
	$(this).parent().children().removeClass("selected");
	$(this).addClass("selected");
	var id = $(this).attr('categoryId');
	firstCategoryId = id;
	$('#thirdCategory ul').find("li").remove(); 
	categoryId = -1;
	$.post('/bgGoods/third/propertyCategory',{'propertyCategoryId':id},function(json){
		if(json.code == 0){
			var items = json.data;
			var item = null;
			var str = '';
			for(var i = 0,len = items.length;i < len; i++){
				item = items[i];
				if(item != null)
				str += '<li categoryId="'+item.propertyCategoryId+'">'+item.propertyCategoryName+'</li>';
			}
			$('#secondCategory ul').find("li").remove(); 
			$('#secondCategory ul').append(str);
		}else{
			alert(json.msg);
		}
	});
});   

$('#secondCategory').on('click','li',function(){
	$(this).parent().children().removeClass("selected");
	$(this).addClass("selected");
	
	var id = $(this).attr('categoryId');
	
	$.post('/bgGoods/third/propertyCategory',{'propertyCategoryId':id},function(json){
		if(json.code == 0){
			var items = json.data;
			var item = null;
			var str = '';
			for(var i = 0,len = items.length;i < len; i++){
				item = items[i];
				if(item != null)
				str += '<li categoryId="'+item.propertyCategoryId+'">'+item.propertyCategoryName+'</li>';
			}
			$('#thirdCategory ul').find("li").remove(); 
			categoryId = -1;
			$('#thirdCategory ul').append(str);
		}else{
			alert(json.msg);
		}
	});
});   

$('#thirdCategory').on('click','li',function(){
	$(this).parent().children().removeClass("selected");
	$(this).addClass("selected");
	categoryId = $(this).attr('categoryId');
}); 

$('#nextButton').click(function(){
	if(categoryId == -1){
		alert('请先选中第三极类别，再提交');
	}else{
		window.location.href = "/bgGoods/third/editPage?storageId="+storageId+"&categoryId="+categoryId+"&firstCategoryId="+firstCategoryId;
	}
});

$('#cancel').click(function(){
	window.location.href = "/bgGoods/third/list";
});

</script>