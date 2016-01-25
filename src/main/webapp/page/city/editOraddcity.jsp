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
								<input type="hidden" name="cityId" id="cityId" />
								<br>
								
								<div class="form-group" id="parentCityName">
									<label for="pCityName">一级城市名称</label> 
									<input type="text"
										class="form-control" id="pCityName" name="pCityName" 
										placeholder="城市名称">
								</div>
								
								<div class="form-group" id="childCityName">
									<label for="cCityName">二级城市名称</label> 
									<input type="text"
										class="form-control" id="cCityName" name="cCityName" 
										placeholder="城市名称">
								</div>
								
								<input type="hidden" name="cityName" id="cityName"/>
								<input type="hidden" name="level" id="level"/>
								<input type="hidden" name="pid" id="pid"/>
								<input type="hidden" name="isShow" id="isShow"/>
								<input type="hidden" name="isStop" id="isStop"/>
						
								<br>

								<div class="form-group">
									<label for="weight">权重</label> <input type="text"
										class="form-control" id="weight" name="weight"
										placeholder="权重">
								</div>
								
								<div class="form-group">
									<label for="ucId">UC ID（可为空）</label> <input type="text"
										class="form-control" id="ucId" name="ucId"
										placeholder="UC ID">
								</div>

								<div class="form-group">
									<label>是否前端展示</label>

									<div class="btn-group">
										<button type="button" class="btn btn-default dropdown-toggle"
											data-toggle="dropdown" id="modalBtnType1">
											<span id="btnIsShowText"></span>&nbsp; <span class="caret"></span>
										</button>
										<ul class="dropdown-menu" role="menu">
											<li><a href="javascript:void(0);" class="btnIsShow"
												data-val="1">是</a></li>
											<li><a href="javascript:void(0);" class="btnIsShow"
												data-val="0">否</a></li>
										</ul>
									</div>
								</div>
								
<!-- 								<div class="form-group">
									<label>是否休业</label>

									<div class="btn-group">
										<button type="button" class="btn btn-default dropdown-toggle"
											data-toggle="dropdown" id="modalBtnType1">
											<span id="btnIsStopText"></span>&nbsp; <span class="caret"></span>
										</button>
										<ul class="dropdown-menu" role="menu">
											<li><a href="javascript:void(0);" class="btnIsStop"
												data-val="0">否</a></li>
											<li><a href="javascript:void(0);" class="btnIsStop"
												data-val="1">是</a></li>
										</ul>
									</div>
								</div> -->
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