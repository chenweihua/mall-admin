<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ include file="../header.jsp"%>
<link href="/bower_components/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<style>
<!--
.control-label {
	width : 15%;
	text-align : right;
	margin-right : 10px;
	font-weight: bold;
}
.skuPriceTable,.skuPriceTable tr,.skuPriceTable tr td,.skuPriceTable tr th{
	
	border-color: grey;
    border-width: 1px;
    border-style: solid;
	
}
-->
</style>
<script type="text/javascript" src="/js/utils/dateFormat.js"></script>
<script type="text/javascript" src="/js/utils/previewImage.js"></script>
<script type="text/javascript" src="/js/activity/activity.js"></script>
<div class="row">
	<div class="box col-md-12">
		<div class="box-inner">
			<div class="box-header well" data-original-title="">
				<h2>
					<i class="glyphicon glyphicon-couponRule"></i>专题列表
				</h2>

				<div class="box-icon">
					<a href="#" class="btn btn-setting btn-round btn-default"><i
						class="glyphicon glyphicon-cog"></i></a> <a href="#"
						class="btn btn-minimize btn-round btn-default"><i
						class="glyphicon glyphicon-chevron-up"></i></a> <a href="#"
						class="btn btn-close btn-round btn-default"><i
						class="glyphicon glyphicon-remove"></i></a>
				</div>
			</div>
			<div class="box-content">
		           <div class="row">
			          <div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">开始时间</span> <input type="text"
			                id="search_start_time" class="form-control" value="${startDate}">
			            </div>
			          </div>
			          <div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">结束时间</span> <input type="text"
			                id="search_end_time" class="form-control" value="${endDate}">
			            </div>
			          </div>
			          
			          <div class="col-lg-3">
			            <div class="input-group">
			              <span class="input-group-addon">活动名称</span> <input type="text"
			                id="activityName" class="form-control"  >
			            </div>
			          </div>
		          </div>
		          
		          <div class="row">
		          <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">活动类型</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="btnActivityTypeText">全部</span>&nbsp;<span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu">
		                  	<li><a href="javascript:void(0);" class="btnType"
		                        data-val="-1">全部</a></li>  
		                   <c:forEach items="${ActivityType}" var="obj">
 								<li><a href="javascript:void(0);" class="btnType"
		                       		 data-val="${obj.code}">${obj.msg}</a></li>
				      	    </c:forEach>
		                </ul>
		              </div>
		            </div>
		          </div>
		
		          <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">活动平台</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="btnActivityPlatformText">全部</span>&nbsp;<span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu">
		                  	<li><a href="javascript:void(0);" class="btnPlatformType"
		                        data-val="-1">全部</a></li>
		                     <c:forEach items="${ActivityPlatform}" var="obj">
 								<li><a href="javascript:void(0);" class="btnPlatformType"
		                       		 data-val="${obj.code}">${obj.msg}</a></li>
				      	     </c:forEach>
		                </ul>
		              </div>
		            </div>
		          </div>
		          <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">活动状态</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="btnStatusText">全部</span>&nbsp;<span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu">
		                  	<li><a href="javascript:void(0);" class="btnStatus"
		                        data-val="-1">全部</a></li>
		                        <li><a href="javascript:void(0);" class="btnStatus"
		                        data-val="1">开启</a></li>
		                    <li><a href="javascript:void(0);" class="btnStatus"
		                        data-val="0">关闭</a></li>
		                </ul>
		              </div>
		            </div>
		          </div>
		          </div>
		          
		          <div class="row">
		          <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">活动城市</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="btnCityText">全部</span> <span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu" id="ccityList">
		                  	<li><a href="javascript:void(0);" class="btnCity"
		                        data-val="-1" onclick="changeCity(this)">全部</a></li>
		                </ul>
		              </div>
		            </div>
		          </div>
		          
		          <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">活动学校</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="btnCollegeText">全部</span>&nbsp;<span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu" id="CollegeList">
		                	<li><a href="javascript:void(0);" class="btnCollege"
		                        data-val="-1" onclick="changeCollege(this)">全部</a></li>
		                  	<!-- <li><a href="javascript:void(0);" class="btnCollege"
		                        data-val="0">北大</a></li>
		                    <li><a href="javascript:void(0);" class="btnCollege"
		                        data-val="1">清华</a></li>
		                    <li><a href="javascript:void(0);" class="btnCollege"
		                        data-val="2">北邮</a></li> -->
		                </ul>
		              </div>
		            </div>
		          </div>
		          
		          <div class="col-lg-3">
		            <div class="input-group">
		              <span class="input-group-addon">活动位置</span>
		              <div class="btn-group">
		                <button type="button" class="btn btn-default dropdown-toggle"
		                  data-toggle="dropdown">
		                  <span id="btnActivityPositionText">全部</span>&nbsp;<span
		                    class="caret"></span>
		                </button>
		                <ul class="dropdown-menu" role="menu">
		                  	<li><a href="javascript:void(0);" class="btnPositionType"
		                        data-val="-1">全部</a></li>
		                        <li><a href="javascript:void(0);" class="btnPositionType"
		                        data-val="1">导航栏</a></li>
		                    <li><a href="javascript:void(0);" class="btnPositionType"
		                        data-val="0">首页</a></li>
		                </ul>
		              </div>
		            </div>
		          </div>
		          
		          <div class="col-lg-1">
		            <div class="input-group">
		              <button type="button" id="btnSubmit" class="btn btn-default">搜索</button>
		            </div>
		          </div>
		          
		        </div>
		        <br>
				<br>
		<table id="couponRuleList"
					class="table table-striped table-bordered bootstrap-datatable datatable responsive">
					<thead>
						<tr>
							<th>活动ID</th>
							<th>活动名称</th>
							<th>活动类型</th>
							<th>活动平台</th>
							<th>前端显示</th>
							<th>H5图片</th>
							<th>App图片</th>
							<th>活动时间</th>
							<th>是否显示</th>
							<th>权重</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					
				</table>
		        </div>
				
				
			</div>
		</div>
	</div>
	<!--/span-->
<button type="button" class="btn btn-primary btn-sm" id="addButton">添加</button>&nbsp;&nbsp;
<button type="button" class="btn btn-primary btn-sm" id="activityTemplateBtn">活动模板</button>
</div>
<!--/row-->


<div class="modal fade" id="modalEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel"></h4>
      </div>
      <div class="modal-body">
		        
		<div class="row">
			<div class="box col-md-12">
					<div class="box-content">
						<form role="form" id="form1">
							<input type="hidden" name="activityId" id="activityId" />
							<input type="hidden" name="addType" id="addType" value="0" />
							<input type="hidden" name="addClickType" id="addClickType" value="0" />
							<input type="hidden" name="addPlatformType" id="addPlatformType" value="0" />
							<input type="hidden" name="addProgramType" id="addProgramType" value="0" />
							<input type="hidden" name="addOpenType" id="addOpenType" value="0" />
							<input type="hidden" name="isOpen" id="addIsOpen" value="0" />
							<input type="hidden" name="addShowType" id="addShowType" value="0" />
							
				            <div class="form-group">
				              <label for="exampleInputEmail1">活动类型</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle"
				                  data-toggle="dropdown" id="btnTypeSelect">
				                  <span id="btnActivityTypeText-add"></span>&nbsp;<span
				                    class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                   <c:forEach items="${ActivityType}" var="obj">
		 								<li><a href="javascript:void(0);" class="btnType-add"
				                       		 data-val="${obj.code}" onclick="selectActivityDivShow(${obj.code})">${obj.msg}</a></li>
						      	    </c:forEach>
				                </ul>
				              </div>
				            </div>
				            <div class="form-group">
				              <label for="exampleInputEmail1">点击类型</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle"
				                  data-toggle="dropdown">
				                  <span id="btnClickTypeText-add"></span>&nbsp;<span
				                    class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                  <li><a href="javascript:void(0);" class="btnClickType-add"
				                      data-val="0">新页面</a></li>
<!-- 				                  <li><a href="javascript:void(0);" class="btnClickType-add"
				                      data-val="1">hash change</a></li> -->
				                  <li><a href="javascript:void(0);" class="btnClickType-add"
				                      data-val="2">浮层</a></li>
				                </ul>
				              </div>
				            </div>
							
							<div class="form-group">
				              <label for="exampleInputEmail1">活动平台</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle"
				                  data-toggle="dropdown">
				                  <span id="btnActivityPlatformText-add"></span>&nbsp;<span
				                    class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                    <c:forEach items="${ActivityPlatform}" var="obj">
		 								<li><a href="javascript:void(0);" class="btnPlatformType-add"
				                       		 data-val="${obj.code}" onclick="changeActivityPlatFrom(${obj.code})">${obj.msg}</a></li>
						      	    </c:forEach>
				                </ul>
				              </div>
				            </div>
				            
							<div class="form-group">
				              <label for="exampleInputEmail1">是否显示</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle"
				                  data-toggle="dropdown">
				                  <span id="btnShowTypeText-add">是</span>&nbsp;<span
				                    class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                  <li><a href="javascript:void(0);" class="btnShowType-add"
				                      data-val="1">是</a></li>
				                  <li><a href="javascript:void(0);" class="btnShowType-add"
				                      data-val="0">否</a></li>
				                </ul>
				              </div>
				            </div>
							
							<div class="form-group">
								<div id="selectActivityDiv">
									<label for="exampleInputPassword1">优惠批次号</label> 
									<input class="form-control" name="batchIds" id="batchIds" placeholder="多个批次号用英文逗号分割">	
								</div>
								<div id="selectActivityReductionDiv">
									<label for="exampleInputPassword1">首减优惠金额</label> 
									<input class="form-control" name="value" id="value" placeholder="首单用户减去优惠金额，单位元">	
								</div>
							</div>
							<div class="form-group" id="displayTypeId">
								<label for="exampleInputPassword1">展示方式</label>
								<input type="radio" name="displayType" id="displayTypeId1" value="1" checked="checked" onclick="displayTypeF(1);"/>导航栏 &nbsp;&nbsp;&nbsp;
                 				<input type="radio"  name="displayType" id="displayTypeId0" value="0" onclick="displayTypeF(0);"/>首页
							</div>
							<div class="form-group" id="labelTypeId">
								<label for="exampleInputPassword1">选择标签</label> 
								<input  type="radio"  name="labelType" id="labelTypeId1" value="1" />HOT&nbsp;&nbsp;&nbsp;
								<input  type="radio"  name="labelType" id="labelTypeId2" value="2" />NEW&nbsp;&nbsp;&nbsp;
								<input  type="radio"  name="labelType" id="labelTypeId3" value="3" />推荐&nbsp;&nbsp;&nbsp;
								<input  type="radio"  name="labelType" id="labelTypeId0" value="0" checked="checked"/>空
							</div>
							<div class="form-group" id="isLinkRadioId" style="display:none;">
								<label for="exampleInputPassword1">是否支持链接</label> 
								<input  type="radio"  name="isLinkUrl" id="isLinkUrlId1" value="1" onclick="displayLinkUrlAddress(1);"/>是&nbsp;&nbsp;&nbsp;
								<input  type="radio"  name="isLinkUrl" id="isLinkUrlId0" value="0" checked="checked" onclick="displayLinkUrlAddress(0);"/>否&nbsp;&nbsp;&nbsp;
							</div>
							<div class="form-group" id="isLinkAddressId" style="display:none;">
								<label for="exampleInputPassword1">链接地址</label> 
								<input class="form-control" name="linkUrl" id="linkUrlId" />
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">活动名称</label> 
								<input class="form-control" name="bgName" id="bgName">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1"><span id="typeNameId"></span>前端显示活动名称</label> 
								<input class="form-control" name="name" id="name">
							</div>
							<!-- <div class="form-group">
								<label for="exampleInputEmail1">活动图片</label> <input type="text"
									class="form-control" name="img" id="img"
									placeholder="活动图片">
									<a href="" target="_blank" id="editImgUrl" style="display: none;">查看</a>
									 <img id="uploadPreview2" src="" style="width:300px" alt="未激活图片"/><br />
									<input id="uploadImage2" type="file" name="p1" onchange="PreviewImage(2);" />
									<input type="hidden" id="imageUrl2" name="imageUrl2" value="123123">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">权重</label> <input
									class="form-control" name="weight" id="weight"
									placeholder="权重">
							</div> -->
							<div class="form-group">
				              <label for="exampleInputEmail1">活动时间</label>
				              <div class="btn-group">
				                <button type="button" class="btn btn-default dropdown-toggle"
				                  data-toggle="dropdown">
				                  <span id="btnActivityTimeText-add"></span>&nbsp;<span
				                    class="caret"></span>
				                </button>
				                <ul class="dropdown-menu" role="menu">
				                  <li><a href="javascript:void(0);" class="btnOpenType-add"
				                      data-val="0" onclick="selectTimeDivShow(0)">手动控制</a></li>
				                  <li><a href="javascript:void(0);" class="btnOpenType-add"
				                      data-val="1" onclick="selectTimeDivShow(1)">自动控制</a></li>
				                </ul>
				              </div>
				            </div>
							<div class="form-group">
								<div id="selectTimeDiv">
									<div class="input-group">
						              <span class="input-group-addon">开始时间</span> <input type="text" style="width: 180px;"
						                id="startDate" name="startDate" class="form-control" placeholder="开始时间">
						            </div>
						            
						            <div class="input-group">
						              <span class="input-group-addon">结束时间</span> <input type="text" style="width: 180px;"
						                id="endDate" name="endDate" class="form-control" placeholder="结束时间">
						            </div>
<!-- 								开始：<input type="text" style="width: 180px;"
									class="form-control" id="startDate" name="startDate"
									placeholder="开始时间">
							 	结束：<input type="text" style="width: 180px;"
									class="form-control" id="endDate" name="endDate"
									placeholder="结束时间"> -->
								</div>
							</div>
							<div class="form-group" id="wapImgUrlId">
								<label for="exampleInputImage1">H5活动图片</label> <!--<input type="text"
									class="form-control" name="img" id="img"
									placeholder="活动图片"> -->
									<a href="" target="_blank" id="wapEditImgUrl" style="display: none;">查看</a>
									<img id="uploadPreview3" src="" style="width:300px" alt="未激活图片"/><br />
									<input id="uploadImage3" type="file" name="p3" onchange="PreviewImage(3);" />
									<input type="hidden" id="imageUrl3" name="wapImageUrl" value="">
							</div>
							<div class="form-group" id="appImgUrlId">
								<label for="exampleInputImage2">App活动图片</label> <!--<input type="text"
									class="form-control" name="img" id="img"
									placeholder="活动图片"> -->
									<a href="" target="_blank" id="editImgUrl" style="display: none;">查看</a>
									<img id="uploadPreview2" src="" style="width:300px" alt="未激活图片"/><br />
									<input id="uploadImage2" type="file" name="p1" onchange="PreviewImage(2);" />
									<input type="hidden" id="imageUrl2" name="imageUrl2" value="123123">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">权重</label> <input
									class="form-control" name="weight" id="weight"
									placeholder="权重">
							</div>
						</form>
		
					</div>
			</div>
			<!--/span-->
		
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" id="btnSubmitEdit">提交</button>
      </div>
    </div>
  </div>
</div>


<div class="modal fade" id="addGoodsMoal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel3" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content" style="height:600px;overflow-y:auto;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel"></h4>
      </div>
      <div class="modal-body">
		        
		<div class="row">
			<div class="box col-md-12">
					<div class="box-content">
						<form role="form" id="addPopularForm">
							<input type="hidden" name="popularActivityId" id="popularActivityId" />
							<input type="hidden" name="popularActivityGoodsId" id="popularActivityGoodsId" />
							<input type="hidden" name="bgGoodsInfo" id="bgGoodsInfo" />
							<div class="form-group">
								<label for="exampleInputPassword1" class="control-label">商品:</label> 
								<a href="javascript:chooseGoods()">选择商品</a>&nbsp;&nbsp;<span id="choosedGoodsName"></span>
							</div>
						
				      		<div class="form-group" id="originPriceDiv">
								<label for="exampleInputPassword1" class="control-label">活动价:</label> 
								<input class="form-control" name="originPrice" id="originPrice" readonly="readonly" style="width:30%;display:inline;">
							</div>
							
							<div class="form-group" id="nowPriceDiv">
								<label for="exampleInputPassword1" class="control-label">原价:</label> 
								<input class="form-control" name="nowPrice" id="nowPrice" readonly="readonly" style="width:30%;display:inline;">
							</div>
							
							<div class="form-group" id="skuPriceShowDiv" style="display:none;">
								<label for="exampleInputPassword1" class="control-label">SKU价格:</label> 
								<table class="skuPriceTable" style=" display: inline-table">
									<thead><tr><th>商品名称</th><th>单位</th><th>描述</th><th>活动价</th><th>原价</th></tr></thead>
									<tbody id="skuPriceShowTableBody"></tbody>
								</table>
							</div>
							
							<div class="form-group">
								<label for="exampleInputPassword1" class="control-label">商品类型:</label> 
								<input type="hidden" id="storageType" name="storageType" />
								<select class="form-control" id="storageTypeShow" name="storageTypeShow" disabled="disabled" style="width:150px; display: inline;">
										<option value=""></option>
					                	<option value="0">RDC商品</option>
					                	<option value="1" >LDC商品</option>
					                	<option value="2" >第三方商品</option>
					            </select>
							</div>
							
							<div class="form-group">
								<label for="exampleInputPassword1" class="control-label">开始时间:</label> 
								<input type="text" style="width: 180px; display: inline;" id="beginTime" name="beginTime"  class="form-control">
							</div>
							
							
							<div class="form-group">
								<label for="exampleInputPassword1" class="control-label">结束时间:</label> 
								<input type="text" style="width: 180px; display: inline;" id="endTime" name="endTime" class="form-control">
							</div>
								
							<div class="form-group">
								<label for="headerImageUrl" class="control-label">头部图片:</label>
  								<input id="uploadImage4" type="file" name="p2" onchange="PreviewMutiImage('uploadImage4','uploadPreview','headerImageUrl',10, 'imageUrlBoxerShow1');" style="display:inline;" />
								<div id="imageUrlBoxerShow1" style="margin-left:92px;">
									<span id="uploadPreview1">
										<img src="" style="width:200px" alt="描述图片"/>
										<input type="hidden" name="headerImageUrl" value="">
									</span><br />
								</div>
							</div>
							
							<div class="form-group">
								<label for="imageUrl" class="control-label">描述图片:</label>
								<input id="uploadImage5" type="file" name="p2" onchange="PreviewMutiImage('uploadImage5','uploadDetailPreview','imageUrl',10, 'imageUrlBoxerShow2');" style="display:inline;" />
								<div id="imageUrlBoxerShow2" style="margin-left:92px;">
									<span id="uploadDetailPreview1">
										<img src="" style="width:200px" alt="描述图片"/>
										<input type="hidden" name="imageUrl" value="">
									</span><br />
								</div>
							</div>
							
							<div class="form-group">
								<label for="exampleInputPassword1" class="control-label">活动规则:</label> 
								<textarea rows="5" cols="50" id="activityRule" name="activityRule"></textarea>
							</div>
							
						</form>
		
					</div>
			</div>
			<!--/span-->
		
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" id="btnSubmitAddGoods">提交</button>
      </div>
    </div>
  </div>
</div>

<%@ include file="goodsChooser.jsp"%>
<%@ include file="../footer.jsp"%>
<%@ include file="../region_tree.jsp"%>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/bower_components/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>



$('#beginTime').datetimepicker({
      language:  'zh-CN',
      weekStart: 1,
      todayBtn:  1,
      autoclose: 1,
      todayHighlight: 1,
      startView: 2,
      forceParse: 0,
      showMeridian: 1,
      format: 'yyyy-mm-dd hh:ii:00'
  });

$('#endTime').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1,
    format: 'yyyy-mm-dd hh:ii:00'
});



//爆品活动上单
function addPopularGoods(activityId) {
	
	clearPopularActivityForm();
	
	$("#popularActivityId").val(activityId);
	
	$.getJSON("../activitygoods/queryPopularActivity?popularActivityId=" + activityId, function (ret) {
		var templateActivity = ret.data.templateActivity;
		var bgGoodsForActivity = ret.data.bgGoodsForActivity;
		var bgSkuForActivitys = ret.data.bgSkuForActivitys;
		if(templateActivity != null && bgGoodsForActivity != null && bgSkuForActivitys != null) {
			$("#popularActivityGoodsId").val(bgGoodsForActivity.bgGoodsId);
			$("#choosedGoodsName").text(bgGoodsForActivity.bgGoodsName);
			
			if(bgSkuForActivitys && bgSkuForActivitys.length > 1) {
				
				$("#skuPriceShowDiv").show();
				$("#originPriceDiv").hide();
				$("#nowPriceDiv").hide();
				$("#skuPriceShowTableBody").empty();
				for(var i = 0; i < bgSkuForActivitys.length; i++) {
					$("#skuPriceShowTableBody").append("<tr><td>" + bgSkuForActivitys[i].bgGoodsName + "</td><td>" + bgSkuForActivitys[i].unit + "</td><td>" + bgSkuForActivitys[i].describe + "</td><td>" + (bgSkuForActivitys[i].activityPrice / 100.0) + "</td><td>" + (bgSkuForActivitys[i].originPrice / 100.0) + "</td></tr>");
				}
				
			} else {
				$("#originPrice").val(bgSkuForActivitys[0].originPrice / 100.0);
				$("#nowPrice").val(bgSkuForActivitys[0].activityPrice / 100.0);
			}
			
			var bgGoodsInfo = {'bgGoodsId':bgGoodsForActivity.bgGoodsId,'activityPrice':bgSkuForActivitys[0].activityPrice / 100.0,'originPrice':bgSkuForActivitys[0].originPrice / 100.0};
			$("#bgGoodsInfo").val(JSON.stringify(bgGoodsInfo));
			
			$("#storageTypeShow option[value=" + bgGoodsForActivity.storageType + "]").attr("selected","selected");
			$("#storageType").val(bgGoodsForActivity.storageType);
			$("#activityRule").val(templateActivity.activityRule);
			$("#beginTime").val(new Date(bgGoodsForActivity.beginTime).Format("yyyy-MM-dd hh:mm:ss"));
			$("#endTime").val(new Date(bgGoodsForActivity.endTime).Format("yyyy-MM-dd hh:mm:ss"));
			
			//$("#uploadPreview4").attr("src",templateActivity.headImageUrl);
			//$("#imageUrl4").val(templateActivity.headImageUrl);
			
			$("#imageUrlBoxerShow1").empty();
			var headImageUrls = templateActivity.headImageUrl;
			var headImageUrlArr = headImageUrls.split(",");
			var descImage1 = '';
			for(var i = 0; i < headImageUrlArr.length; i++) {
				descImage1 += ('<span id="uploadPreview' + i + '">'
								+ '<img src="' + headImageUrlArr[i] + '" style="width:200px" alt="描述图片"/>'
						 		+ '<a href="javascript:void(0);" onclick="removeElement(\'uploadPreview' + i + '\');">删除</a><br />'
						 		+ '<input type="hidden" name="headerImageUrl" value="' + headImageUrlArr[i] + '">'
						 	+ '</span>'); 
			}
			$("#imageUrlBoxerShow1").append(descImage1);
			
			$("#imageUrlBoxerShow2").empty();
			var imageUrls = templateActivity.imageUrl;
			var imageUrlArr = imageUrls.split(",");
			var descImage2 = '';
			for(var i = 0; i < imageUrlArr.length; i++) {
				descImage2 += ('<span id="uploadDetailPreview' + i + '">'
								+ '<img src="' + imageUrlArr[i] + '" style="width:200px" alt="描述图片"/>'
						 		+ '<a href="javascript:void(0);" onclick="removeElement(\'uploadDetailPreview' + i + '\');">删除</a><br />'
						 		+ '<input type="hidden" name="imageUrl" value="' + imageUrlArr[i] + '">'
						 	+ '</span>'); 
			}
			$("#imageUrlBoxerShow2").append(descImage2);
		}
		
    });
	
	
	$("#addGoodsMoal").modal('show');
}

//品牌活动上单
function addBrandGoods(activityId) {
	window.location.href = "../activitygoods/showBrandActivity?brandActivityId=" + activityId;
}

function chooseGoods() {
	
	clearGoodsChooser();
	$("#goodsChooserModal").modal('show');
}

function setParentPageField(bgGoodsInfo,storageTypeEdit,choosedGoodsName,imageUrl,goodsType) {
	
	if(goodsType == "聚合品") {
		alert("爆品活动暂不支持聚合品");
		return;
		/*
		$("#skuPriceShowDiv").show();
		$("#originPriceDiv").hide();
		$("#nowPriceDiv").hide();
		$("#skuPriceShowTableBody").empty();
		for(var i = 0; i < bgGoodsInfo.skuListBean.length; i++) {
			$("#skuPriceShowTableBody").append("<tr><td>" + bgGoodsInfo.skuListBean[i].bgGoodsName + "</td><td>" + bgGoodsInfo.skuListBean[i].unit + "</td><td>" + bgGoodsInfo.skuListBean[i].desc + "</td><td>" + bgGoodsInfo.skuListBean[i].activityPrice + "</td><td>" + bgGoodsInfo.skuListBean[i].originPrice + "</td></tr>");
		}
		*/
		
	} else {
		$("#skuPriceShowDiv").hide();
		$("#originPriceDiv").show();
		$("#nowPriceDiv").show();
		$("#originPrice").val(bgGoodsInfo.activityPrice);
		$("#nowPrice").val(bgGoodsInfo.originPrice);
	}
	$("#choosedGoodsName").text(choosedGoodsName);
	
	$("#bgGoodsInfo").val(JSON.stringify(bgGoodsInfo));	
	$("#popularActivityGoodsId").val(bgGoodsInfo.bgGoodsId);
	$("#storageTypeShow option[value=" + storageTypeEdit + "]").attr("selected","selected");
	$("#storageType").val(storageTypeEdit);
}

$('#search_start_time').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1,
    format: 'yyyy-mm-dd hh:ii:ss'
});

$('#search_end_time').datetimepicker({
      language:  'zh-CN',
      weekStart: 1,
      todayBtn:  1,
      autoclose: 1,
      todayHighlight: 1,
      startView: 2,
      forceParse: 0,
      showMeridian: 1,
      format: 'yyyy-mm-dd hh:ii:ss'
  });

$('#startDate').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1,
    format: 'yyyy-mm-dd hh:ii:ss'
});

$('#endDate').datetimepicker({
      language:  'zh-CN',
      weekStart: 1,
      todayBtn:  1,
      autoclose: 1,
      todayHighlight: 1,
      startView: 2,
      forceParse: 0,
      showMeridian: 1,
      format: 'yyyy-mm-dd hh:ii:ss'
  });

var urlPrefix = "";
  
var typeMap = {
		0:"",
		1:"",
		2:""
}

var activityTypeMap = {
		'-1':"全部",
		<c:forEach items="${ActivityType}" var="obj">
			${obj.code}:"${obj.msg}",
  	    </c:forEach>
	};
	
var activityProgramTypeMap = {
		'-1':"全部",   
		<c:forEach items="${ActivityPlan}" var="obj">
			${obj.code}:"${obj.msg}",
  	    </c:forEach>
	};

var activityPlatformTypeMap = {
		'-1':"全部",   
		<c:forEach items="${ActivityPlatform}" var="obj">
			${obj.code}:"${obj.msg}",
  	    </c:forEach>
	};


var activityTimeMap = {
		'-1':"全部",
		0: "手动控制",
		1: "自动控制"
	};
	
var activityStatusMap = {
		'-1':"全部",
		0: "关闭",
		1: "开启"
	};
	
var displayTypeMap = {
		'-1':"全部",
		0: "首页",
		1: "导航栏"
	};

var activityClickTypeMap = {
		0: "新页面",
/* 		1: "hash change", */
		2: "浮层"
}

function selectTimeDivShow(type) {
	if(type == 0) {
		$("#selectTimeDiv").hide();	
	} else {
		$("#selectTimeDiv").show();
	}
	
}

function selectActivityDivShow(type) {

	if(type == 0 || type == 1 || type == 5 || type == 6 || type == 8 || type == 9) {
		$("#selectActivityDiv").hide();	
		$("#selectActivityReductionDiv").hide();	
	} else {
		if(type == 4){
			$("#selectActivityReductionDiv").show();	
			$("#selectActivityDiv").hide();		
		}else{
			$("#selectActivityReductionDiv").hide();	
			$("#selectActivityDiv").show();
		}
	}
}





var cityList ;
var collegeMap ;
var addType = 0;
var addBatchIds = '-1';
//for search select change
var type = -1;
var platformType = -1;
var status = -1;
var city = -1;
var college = -1;
var programType  = -1;
var activityDisplayType = -1;


$('.btnType').click(function(){
    type = $(this).data("val");
    $('#btnActivityTypeText').html(activityTypeMap[type]);
});

$('.btnPlatformType').click(function(){
	platformType = $(this).data("val");
    $('#btnActivityPlatformText').html(activityPlatformTypeMap[platformType]);
});


$('.btnStatus').click(function(){
	status = $(this).data("val");
    $('#btnStatusText').html(activityStatusMap[status]);
});

$('.btnPositionType').click(function(){
	activityDisplayType = $(this).data("val");
    $('#btnActivityPositionText').html(displayTypeMap[activityDisplayType]);
});

function changeCity(obj){
	city = $(obj).data("val");
    $('#btnCityText').html($(obj).html());
    
    college = -1;//$(obj).data("val");
    $('#btnCollegeText').html("全部");
    
    $("#CollegeList").html("");
	$("#CollegeList").append('<li><a href="javascript:void(0);" class="btnCollege" data-val="-1" onclick="changeCollege(this)">全部</a></li>');
    
    if(collegeMap[city]) {
    	//console.log(collegeMap[city]);
    	for (var i=0, len = collegeMap[city].length; i<len; i++) {
			$("#CollegeList").append('<li><a href="javascript:void(0);" class="btnCollege" data-val="'+collegeMap[city][i].collegeId+'" onclick="changeCollege(this)">'+collegeMap[city][i].collegeName+'</a></li>');
		}
    }
};

function changeCollege(obj){
	college = $(obj).data("val");
    $('#btnCollegeText').html($(obj).html());
};

// for add select change
$('.btnType-add').click(function(){
	 var nowActivityType = $(this).data("val");
	 if(nowActivityType == '8' || nowActivityType == '9') {
		$("#displayTypeId1").attr("disabled","disabled");
		$("#displayTypeId0").prop("checked",true);
		$("#labelTypeId").hide();
		
		$("#isLinkUrlId1").attr("disabled","disabled");
   	 	$("#isLinkUrlId0").prop("checked",true);
     } else {
    	$("#displayTypeId1").removeAttr("disabled");
    	$("#displayTypeId0").prop("checked",false);
    	$("#labelTypeId").show();
    	$("#isLinkUrlId1").removeAttr("disabled");
     }
	 $("#addType").val($(this).data("val"));
     $('#btnActivityTypeText-add').html(activityTypeMap[$(this).data("val")]);
     changeActivityPlatFrom($("#addPlatformType").val());
     
});

$('.btnPlatformType-add').click(function(){
	$("#addPlatformType").val($(this).data("val"));
    $('#btnActivityPlatformText-add').html(activityPlatformTypeMap[$(this).data("val")]);
});


$('.btnOpenType-add').click(function(){
	$("#addOpenType").val($(this).data("val"));
    $('#btnActivityTimeText-add').html(activityTimeMap[$(this).data("val")]);
});
$(".btnClickType-add").click(function() {
	$("#addClickType").val($(this).data("val"));
    $('#btnClickTypeText-add').html(activityClickTypeMap[$(this).data("val")]);
});
$(".btnShowType-add").click(function() {
	$("#addShowType").val($(this).data("val"));
    $('#btnShowTypeText-add').html($(this).html());
});


var table = null;

var typeEdit;
//edit
var operation = "add";
var activityList ;

$("#activityTemplateBtn").click(function(){
	
	window.location.href = "/activitytemplate/list";
	
});

$('#addButton').click(function () {
	operation = "add";
    
	$("#btnTypeSelect").attr("data-toggle", "dropdown");
	
	$("#editImgUrl").hide();
	$("#wapEditImgUrl").hide();
	$("#imageUrl3").val("");
	$("#imageUrl2").val("");
	$("#uploadPreview3").prop("src","");
	$("#uploadPreview2").prop("src","");
	
    /* $("#btnActivityTypeText-add").html(activityTypeMap[0]);
    $("#btnActivityPlatformText-add").html(activityPlatformTypeMap[0]);
    
    $("#btnActivityTimeText-add").html(activityTimeMap[0]);
    
    $("#btnClickTypeText-add").html(activityClickTypeMap[0]); */
    
    $("#activityId").val(-1);
	
	$("#addType").val(0);
    $('#btnActivityTypeText-add').html(activityTypeMap[0]);
    
    
    $("#displayTypeId1").removeAttr("disabled");

	$("#addPlatformType").val(0);
    $('#btnActivityPlatformText-add').html(activityPlatformTypeMap[0]);
    
	$("#addOpenType").val(0);
    $('#btnActivityTimeText-add').html(activityTimeMap[0]);
    
	$("#addClickType").val(0);
    $('#btnClickTypeText-add').html(activityClickTypeMap[0]);
    
	$("#addShowType").val(1);
	
	selectTimeDivShow(0);
	selectActivityDivShow(0);
	
    $('#btnShowTypeText-add').html("是");
    
	$("#bgName").val("");
	//$("#value").val("");
	//$("#batchIds").val("");
	$("#name").val("");

	$("#weight").val("");
    
    $("#startDate").val("");
	$("#endDate").val("");	
	
	$("#isLinkUrlId0").prop("checked",true);
	$("#linkUrlId").val("");
	$("#isLinkRadioId").hide();
	$("#isLinkAddressId").hide();
	$("#displayTypeId1").prop("checked",true);
	$("#labelTypeId0").prop("checked",true);
	$("#labelTypeId").show();
	$("#appImgUrlId").hide();
	$("#wapImgUrlId").hide();
	
    //document.getElementById('money').value = '';
    //document.getElementById('subMoney').value = '';
	$('#modalEdit').modal();
});

function checkParam(){
	
	var numReg = /^\d+(?=\.{0,1}\d+$|$)/;
	
	
	var addType = $("#addType").val();
	var addClickType = $("#addClickType").val();
	var addPlatformType = $("#addPlatformType").val();
	var addProgramType = $("#addProgramType").val();
	
	var addOpenType = $("#addOpenType").val();
	var bgName = $("#bgName").val();
	//var value = $("#value").val("");
	//var batchIds = $("#batchIds").val("");
	var name = $("#name").val();
	var img = $("#img").val();
	var weight = $("#weight").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	
	/* var startTime = $('#startDate').val();
	var endTime = $('#endDate').val(); */
	
	//alert(startTime + ":" + $.trim(startTime).length);
	
	if($.trim(bgName).length < 1 || $.trim(bgName).length > 10) {
		return "活动名称不能为空或者已超过10个字符";
	}
	
	if($.trim(name).length < 1 || $.trim(name).length > 5) {
		return "前端显示活动名称为空或者已超过5个字符";
	}
	
	if($.trim(img).length < 1 || $.trim(img).length > 100) {
		//return "请先上传图片";
	}
	
	if(addType<0 || addType>9){
		return "活动类型错误";
	} 
	if(addProgramType == ''){
		return "请选择正确的活动方案，秒杀和普通活动方案请选择实物！";
	}
	
	if(addProgramType<0 || addProgramType>9){
		return "请选择正确的活动方案，秒杀和普通活动方案请选择实物！";
	}
	if(addClickType<0 || addClickType>9){
		return "点击类型错误";
	}
	
	if(addPlatformType<0 || addPlatformType>9){
		return "活动平台类型错误";
	}  
	if(addOpenType<0 || addOpenType>9){
		return "活动时间类型错误";
	}
	if(addOpenType==1) {
		if($.trim(startDate).length != 19 || $.trim(endDate).length != 19) {
			return "自动控制活动时间时-请添加活动开始-结束时间";
		}
		if(startDate >= endDate) {
			return "活动规则开始时间不得早于结束时间";
		}
	}
	if($("#addType").val() == 4 && $.trim($("#value").val()) == "") {
		return "首减活动首减优惠金额字段不能为空";
		
	}
	return null;
}

//爆品活动添加单个商品
$("#btnSubmitAddGoods").click(function(){
	//alert($("#addPopularForm").serialize());
	
	if(!checkParamPopularGoods()) {
		return;
	}
	
	$.post("../activitygoods/validateGoodsCollegeContainsActivityColleges", {
		"bgGoodsId":$("#popularActivityGoodsId").val(),
		"activityId":$("#popularActivityId").val(),
		"storageType":$("#storageTypeShow").val()
		}, 
		function(response){
			if (response.code == 0) {
				$.post("../activitygoods/addActivityPopularGoods", $("#addPopularForm").serialize(), function(response){
					if (response.code == 0) {
						alert("操作成功");
						$("#addPopularForm")[0].reset();
						clearPopularActivityForm();
				        table.ajax.reload(null, true);
				        $('#addGoodsMoal').modal('hide');
					} else {
						alert(response.msg);
					}
				});
			} else {
				alert(response.msg);
			}
		}
	);
	
	
	
	
	
	
});


function checkParamPopularGoods() {
	if($("#popularActivityId").val() == "") {
		alert("找不到活动ID，请刷新页面重新进入");
		return false;
	}
	
	if($("#popularActivityGoodsId").val() == "" || $("#bgGoodsInfo").val() == "") {
		alert("请选择商品");
		return false;
	}
	
	if($("#storageTypeShow").val() == "") {
		alert("请选择商品");
		return false;
	}
	
	if($("#beginTime").val() == "" || $("#endTime").val() == "") {
		alert("请填写开始时间和结束时间");
		return false;
	}
	
	if($("#beginTime").val() > $("#endTime").val()) {
		alert("开始时间须小于结束时间");
		return false;
	}
	
	var headerImageUrlPass = true;
	$("input[name=headerImageUrl]").each(function(){
		
		if($(this).val() == "") {
			headerImageUrlPass = false;
			return false;
		}
		
	});
	if(!headerImageUrlPass) {
		alert("头部图片不能为空");
		return false;
	}
	
	
	var imageUrllPass = true;
	$("input[name=imageUrl]").each(function(){
		
		if($(this).val() == "") {
			imageUrllPass = false;
			return false;
		}
		
	});
	if(!imageUrllPass) {
		alert("描述图片不能为空");
		return false;
	}
	
	return true;
	
}


//清空爆品活动添加项
function clearPopularActivityForm() {
	$("#popularActivityId").val("");
	$("#popularActivityGoodsId").val("");
	$("#choosedGoodsName").text("");
	$("#originPrice").val("");
	$("#nowPrice").val("");
	$("#storageTypeShow option:first").attr("selected","selected");
	$("#storageType").val("");
	$("#activityRule").val("");
	$("#beginTime").val("");
	$("#endTime").val("");
	
	//清空图片
	//$("#uploadPreview4").attr("src","");
	//$("#imageUrl4").val("");
	$("#uploadImage4").val("");
	$("#imageUrlBoxerShow1").empty();
	$("#imageUrlBoxerShow1").append('<span id="uploadPreview1"><img src="" style="width:200px" alt="描述图片"/><input type="hidden" name="headerImageUrl" value=""></span><br />');
	
	$("#uploadImage5").val("");
	$("#imageUrlBoxerShow2").empty();
	$("#imageUrlBoxerShow2").append('<span id="uploadDetailPreview1"><img src="" style="width:200px" alt="描述图片"/><input type="hidden" name="imageUrl" value=""></span><br />');
	
	
}


$('#btnSubmitEdit').click(function(){
	
	var msg = checkParam();
	if(msg != null){
		alert (msg)
		return ;
	}
	resetParamValue();
	
	$.post(urlPrefix+operation+"?type="+typeEdit, $("#form1").serialize(), function(response){
		if (response.status == "success") {
			alert("操作成功");
			$("#form1")[0].reset();
	        table.ajax.reload(null, false);
	        $('#modalEdit').modal('hide');
		} else {
			alert(response.status);
		}
	});
	
	return false;
});

function editCouponRule (index) {
	operation = "edit";
	
	//$("#editImgUrl").show();
	
	$("#btnTypeSelect").attr("data-toggle", "");
	
	var activityEdit = activityList[index];
	
	/* $("#addType").val(activityEdit.activityType);
	$("#addClickType").val(activityEdit.actionType);
	$("#addPlatformType").val(activityEdit.platformType);
	$("#addOpenType").val(activityEdit.openType);
	$("#addShowType").val(activityEdit.isShow); */
	console.log(activityEdit);
	
		$("#wapEditImgUrl").prop("href", activityEdit.wapImageUrl);
		$("#uploadPreview3").prop("src", activityEdit.wapImageUrl);
		$("#imageUrl3").val(activityEdit.wapImageUrl);
	
		$("#editImgUrl").prop("href", activityEdit.imageUrl);
		$("#uploadPreview2").prop("src", activityEdit.imageUrl);
		$("#imageUrl2").val(activityEdit.imageUrl);
	
		$("#activityId").val(activityEdit.activityId);
	
		$("#addType").val(activityEdit.activityType);
	    $('#btnActivityTypeText-add').html(activityTypeMap[activityEdit.activityType]);
	    
	    if(activityEdit.activityType == '8' || activityEdit.activityType == '9') {
			$("#displayTypeId1").attr("disabled","disabled");
			$("#displayTypeId0").prop("checked",true);
			$("#labelTypeId").hide();
			$("#isLinkUrlId1").attr("disabled","disabled");
	   	 	$("#isLinkUrlId0").prop("checked",true);
	     } else {
	    	$("#displayTypeId1").removeAttr("disabled");
	    	$("#displayTypeId0").prop("checked",false);
	    	$("#labelTypeId").show();
	    	$("#isLinkUrlId1").removeAttr("disabled");
	     }

		$("#addPlatformType").val(activityEdit.platformType);
	    $('#btnActivityPlatformText-add').html(activityPlatformTypeMap[activityEdit.platformType]);
	    
		$("#addOpenType").val(activityEdit.openType);
	    $('#btnActivityTimeText-add').html(activityTimeMap[activityEdit.openType]);
	    
		$("#addClickType").val(activityEdit.actionType);
	    $('#btnClickTypeText-add').html(activityClickTypeMap[activityEdit.actionType]);
	    
		$("#addShowType").val(activityEdit.isShow);
		$("#addIsOpen").val(activityEdit.isOpen);
		
		$("#img").val(activityEdit.imageUrl);
		
		selectTimeDivShow(activityEdit.openType);
		selectActivityDivShow(activityEdit.activityType);
		
		var showMsg = "";
		if(activityEdit.isShow == 0) {
			showMsg = "否";
		} else {
			showMsg = "是";
		}
	    $('#btnShowTypeText-add').html(showMsg);
	    
	$("#bgName").val(activityEdit.activityName);
	if(activityEdit.value == -1){
		$("#value").val();
	}else{
		$("#value").val(activityEdit.value);
	}
	$("#batchIds").val(activityEdit.batchIds);	
	$("#name").val(activityEdit.activityShowName);
	
	$("#weight").val(activityEdit.weight);
	
	if(activityEdit.openType == 1) {
		var startDate = new Date();
		var endDate = new Date();
		startDate.setTime(activityEdit.startDate);
		endDate.setTime(activityEdit.endDate);
		$("#startDate").val(startDate.Format("yyyy-MM-dd hh:mm:ss"));
		$("#endDate").val(endDate.Format("yyyy-MM-dd hh:mm:ss"));		
	}
	$('#displayTypeId' + activityEdit.displayType).prop("checked",true);
	$('#labelTypeId' + activityEdit.labelType).prop("checked",true);
	$('#isLinkUrlId' + activityEdit.isLinkUrl).prop("checked",true);
	$('#linkUrlId').val(activityEdit.linkUrl);
	changeActivityPlatFrom(activityEdit.platformType)
	
	/* typeEdit = couponRuleEdit.couponType;
	var start = new Date();
	start.setTime(couponRuleEdit.beginTime);
	var end = new Date();
	end.setTime(couponRuleEdit.endTime);
    $('#btnTypeEditText').html(typeMap[typeEdit]);
    $("#title").val(couponRuleEdit.couponName);
    $("#desc").val(couponRuleEdit.description);
    document.getElementById('money').value = couponRuleEdit.totalMoney/100.0;
    document.getElementById('subMoney').value = couponRuleEdit.subMoney/100.0;
    document.getElementById('couponRuleId').value = couponRuleEdit.couponId;
    
    $("#startDate").val(start.Format("yyyy-MM-dd hh:mm:ss"));
    $("#endDate").val(end.Format("yyyy-MM-dd hh:mm:ss")); */
    
	$('#modalEdit').modal();
}


function delCouponRule (couponRuleId) {	
    if (confirm("确定删除此优惠活动")) {

        $.getJSON(urlPrefix+"del?activityId="+couponRuleId, function (ret) {
            if (ret.code != null && ret.code != "success") {
                alert(ret.msg);
            }else {
            	alert("删除成功");
                table.ajax.reload();
            }
        });
    }
}


function openActivity(activityId, status) {
	
	var msg = "";
	if(status == 0) {
		msg = "开启";
	} else {
		msg = "关闭";
	}
	
    if (confirm("确定"+msg+"此活动")) {

        $.getJSON(urlPrefix+"open?activityId="+activityId + "&status=" + status, function (ret) {
            if (ret.code != null && ret.code != "success") {
                alert(ret.msg);
            }else {
            	alert(msg+"成功");
                table.ajax.reload();
            }
        });
    }
}


//提交搜索条件
$('#btnSubmit').click(function(){
	table.ajax.reload();
});

$(document).ready(function() {
	//datatable
    //$('#btnTypeText').html(typeMap[type]);
	table = $('#couponRuleList').DataTable({
		ordering: false,
        processing: true,
        serverSide: true,
        searching: false,
        ajax: {
        	url: urlPrefix+"ajaxListData",
        	data: function(d) {
        		d.activityId = $("#activityId").val();
        		d.startDate = $('#search_start_time').val();
                d.endDate = $('#search_end_time').val();
                d.name = $('#activityName').val();
                d.type = type;
                d.programType = programType;
                d.platformType = platformType;
                d.status = status;
                d.city = city;
                d.college = college;
                d.displayType = activityDisplayType;
            },
        	dataSrc: function ( json ) {
        		var newData = [];
        		activityList = json.data;
        		for (var i=0, len = json.data.length; i<len; i++) {
        			var activity = json.data[i];
        			
        			var timeMsg = "";
        			if(activity.openType == 1) {
        				var startDate = new Date();
            			var endDate = new Date();
        				startDate.setTime(activity.startDate);
            			endDate.setTime(activity.endDate);
            			if(activity.startDate && activity.endDate) {
            				timeMsg = "自动:" + startDate.Format("yyyy-MM-dd hh:mm:ss") + "~" + endDate.Format("yyyy-MM-dd hh:mm:ss");	
            			} else {
            				timeMsg = "自动";
            			}
            			
        			} else {
        				timeMsg = "手动";
        			}
        			
                    var openOpera = "";
                    /* if(activity.openType == 1) {
                    	openOpera = "开启";
                    } else { */
                    	// 关闭
                    	if(activity.isOpen == 0) {
                    		openOpera = '关闭[<a href="javascript:openActivity('+activity.activityId+','+activity.isOpen+');">开启</a>]';
                    	} else {
                    		openOpera = '开启[<a href="javascript:openActivity('+activity.activityId+','+activity.isOpen+');">关闭</a>]';
                    	}
                    /* } */
                    var isShowMsg = "";
                    if(activity.isShow==0) {
                    	isShowMsg = "否";
                    } else {
                    	isShowMsg = "是";
                    }
                    var imageInfo = "暂无";
                    if(activity.imageUrl) {
                    	imageInfo = '<a href="'+activity.imageUrl+'" target="_blank">查看</a>';
                    }
                    var wapImageInfo = "暂无";
                    if(activity.wapImageUrl) {
                    	wapImageInfo = '<a href="'+activity.wapImageUrl+'" target="_blank">查看</a>';
                    }
                    var acitivityName;
                    var activityShowName;
                    var activityType;
                    if(activity.activityType == 5) {
	                    acitivityName = '<span style="color:Red">' + activity.activityName +' </span>';
	                    activityShowName = '<span style="color:Red">' + activity.activityShowName +'</span>';
	                    activityType = '<span style="color:Red">' +  activityTypeMap[activity.activityType]+'</span>';
                    }
                    else if(activity.activityType == 6){
                    	acitivityName = '<a href="javascript:addMultiGoods('+activity.activityId+');">'+activity.activityName+'</a>';
                    	activityShowName = activity.activityShowName;
                        activityType = activityTypeMap[activity.activityType];
                    }else{
                    	acitivityName = activity.activityName;
                    	activityShowName = activity.activityShowName;
                        activityType = activityTypeMap[activity.activityType];
                    }
                    
                    var actionField = '<a href="javascript:delCouponRule('+activity.activityId+');">删除</a>&nbsp;&nbsp;<a href="javascript:editCouponRule('+i+');">编辑</a>&nbsp;&nbsp;<a href="javascript:getRegion('+ activity.activityId +');">范围设置</a>';
                    if(activity.activityType == 8) {  //爆品活动  一个活动只能上一个商品，上单在此
                    	actionField += ('&nbsp;&nbsp;<a href="javascript:addPopularGoods('+activity.activityId+');">爆品上单</a>');
                    }
                    if(activity.activityType == 9) {  //品牌活动  上单在此
                    	actionField += ('&nbsp;&nbsp;<a href="javascript:addBrandGoods('+activity.activityId+');">品牌上单</a>');
                    }
                    
        			newData[newData.length] = [activity.activityId,
        			                           acitivityName,
        			                           activityType,
        			                           activityPlatformTypeMap[activity.platformType],
        			                           activityShowName,
        			                           wapImageInfo,
        			                           imageInfo,
        			                           timeMsg,
        			                           isShowMsg,
        			                           activity.weight,
        			                           openOpera,
        			                           /* <a href="javascript:delCouponRule('+activity.activityId+');">删除</a>&nbsp;&nbsp; */
        			                           actionField,
        			                           activity.displayType,
        			                           activity.labelType,
        			                           activity.isLinkUrl,
        			                           activity.linkUrl];
        		}
        		json.data = newData;
        		return newData;
        		
        	}
        },
        sDom: "<'row'<'col-md-6'l><'col-md-6'f>r>t<'row'<'col-md-12'i><'col-md-12 center-block'p>>",
        sPaginationType: "bootstrap",
        oLanguage: {
            sLengthMenu: "_MENU_ 记录每页",
            sSearch: "",
            sZeroRecords: "暂无数据",
            sProcessing: "正在处理...", 
            sEmptyTable : "暂无数据",
            sInfo : "_START_ - _END_ (共_TOTAL_条)",
            sInfoFiltered: "",
            oPaginate: {
                sFirst: "第一页",
                sLast: "最后一页",
                sNext : "下一页",
                sPrevious : "前一页"

            }
                                           
        }
    } );

	$.getJSON(urlPrefix+"ajaxMateData", function (ret) {
		cityList = ret.citiList;
		collegeMap = ret.collegeMap;
		for (var i=0, len = cityList.length; i<len; i++) {
			$("#ccityList").append('<li><a href="javascript:void(0);" class="btnCity" data-val="'+cityList[i].cityId+'" onclick="changeCity(this)">'+cityList[i].cityName+'</a></li>');
		}
		console.log(ret);
    });
    
} );
var setting = {
		check: {
			enable: true,
			chkboxType: { "Y": "ps", "N": "ps" }
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: function(event, treeId, treeNode, clickFlag){
				
			}
		}
	};
var couponRuleIdTemp = 0;
function getRegion(couponRuleId){
	couponRuleIdTemp = couponRuleId
	/* $.fn.zTree.init($("#selectTree"), setting, zNodes); */
    $.post("getregion", {"activityId":couponRuleId},function(ret){
		 if (ret.code != 0) {
             alert(ret.msg);
         }else {	
        	 $('#treeEdit').modal('show');
        	 var zNodes = ret.data;
     		$.fn.zTree.init($("#selectTree"), setting, zNodes);
     	 }
	},"json"); 
}

//确定
$("#treeSubmit").click(function(){
	var treeObj=$.fn.zTree.getZTreeObj("selectTree");
	var nodes=treeObj.getNodes();
	var info = JSON.stringify(nodes);
	 $.post("setregion",{"couponRuleId":couponRuleIdTemp,"region":info}, function(ret){
			 if (ret.code != 0) {
	             alert(ret.msg);
	         }else {	
				alert("设置成功~");
	     	 }
		},"json");  
	 $('#treeEdit').modal('hide');
});


Date.prototype.Format = function(fmt)   
{   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}  


function addMultiGoods(activityId){
	//alert(activityId);
}

</script>