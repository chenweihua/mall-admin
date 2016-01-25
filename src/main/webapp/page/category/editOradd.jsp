<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="modal fade" id="modalEdit" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel3" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel"></h4>
			</div>
			<div class="modal-body">

				<div class="row">
					<div class="box col-md-12">
						<div class="box-content">
							<form role="form" id="form1">
								<input type="hidden" name="categoryId" id="categoryId" />

								<!-- <div class="form-group">
                                    <label>类型</label>

                                    <div class="btn-group">
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown" id="modalBtnType">
                                            <span id="btnTypeEditText"></span>&nbsp;
                                            <span class="caret"></span>                                          
                                        </button>
                                        <ul class="dropdown-menu" role="menu">
                                            <li>
                                                <a href="javascript:void(0);" class="btnTypeEdit" data-val="0">普通类目</a>
                                            </li>
                                            <li>
                                                <a href="javascript:void(0);" class="btnTypeEdit" data-val="1">秒杀类目</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div> -->
								<div class="form-group">
									<label for="categoryName">类目名称</label> <input type="text"
										class="form-control" id="categoryName" name="categoryName" placeholder="类目名称">
								</div>
								<%-- <br>
                                
                             
							   <div class="form-group">
                                    <label for="description">仓库</label>
							        		<p style="padding-top:8px; color:red;display:inline-block;text-indent:10px">${loginInfo.storage.name }</p>
                                </div>
                                <br> --%>

								<br>

								<div class="form-group">
									<label for="weight">类目权重</label> <input type="text"
										class="form-control" id="weight" name="weight"
										placeholder="类目权重">
								</div>

								<div class="form-group">
									<label for="indexShow">是否首页展示</label>
									<div class="btn-group">
										<button type="button" class="btn btn-default dropdown-toggle"
											data-toggle="dropdown" id="modalBtnType1">
											<span id="btnShowIndexText"></span>&nbsp; <span class="caret"></span>
										</button>
										<ul class="dropdown-menu" role="menu" id="indexShow" name="indexShow">
											<li><a href="javascript:void(0);" class="btnShowIndex"
												data-val="1">是</a></li>
											<li><a href="javascript:void(0);" class="btnShowIndex"
												data-val="0">否</a></li>
										</ul>
									</div>
								</div>

								<div class="form-group" id="labelTypeId">
									<label for="labelTypeId">选择标签</label> 
									<input  type="radio" name="labelType" id="labelTypeId1" value="1" checked="checked" />HOT&nbsp;&nbsp;&nbsp;
									<input  type="radio" name="labelType" id="labelTypeId2" value="2" />NEW&nbsp;&nbsp;&nbsp;
									<input  type="radio" name="labelType" id="labelTypeId3" value="3" />推荐&nbsp;&nbsp;&nbsp;
									<input  type="radio" name="labelType" id="labelTypeId0" value="0" />空
								</div>
								<div class="form-group">
									<label for="indexWeight">首页权重</label> <input type="text"
										class="form-control" id="indexWeight" name="indexWeight"
										placeholder="首页权重">
								</div>
								<br>
								<div class="form-group">
									<label for="iconOn">激活图片</label> <img id="uploadPreview1"
										src="${category.iconOn }" style="width: 300px" alt="激活图片" /><br /> <input
										id="uploadImage1" type="file" name="p1"
										onchange="PreviewImage(1);" /> <input type="hidden"
										id="imageUrl1" name="imageUrl1" value="${category.iconOn }">

								</div>
								<br> <br>
								<div class="form-group">
									<label for="iconOff">未激活图片</label> <img id="uploadPreview2"
										src="${category.iconOff }" style="width: 300px" alt="未激活图片" /><br /> <input
										id="uploadImage2" type="file" name="p2"
										onchange="PreviewImage(2);" /> <input type="hidden"
										id="imageUrl2" name="imageUrl2" value="${category.iconOff }">

								</div>
								<div class="form-group">
									<label for="iconOn">APP图片</label> <img id="uploadPreview3"
										src="${category.iconOn }" style="width: 300px" alt="app图片" /><br /> <input
										id="uploadImage3" type="file" name="p3"
										onchange="PreviewImage(3);" /> <input type="hidden"
										id="imageUrl3" name="imageUrl3" value="${category.appIcon }">

								</div>

								<div class="form-group">
									<label for="description">描述</label> <input type="text"
										class="form-control" id="description" name="description"
										placeholder="类目描述">
								</div>

								<br>
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