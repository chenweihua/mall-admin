/**
 * 活动展示方式 1：导航栏 0：首页
 */
function displayTypeF(type) {
	if(type == 1) {
		$("#labelTypeId").show();
		$("#displayTypeId").show();
		$("#isLinkRadioId").hide();
		$("#isLinkAddressId").hide();
		$("#appImgUrlId").hide();
		$("#wapImgUrlId").hide();
	} 
	else {
		$("#labelTypeId").hide();
		$("#displayTypeId").show();
		$("#isLinkRadioId").show();
		if($("#isLinkUrlId1").prop("checked")) {
			$("#isLinkAddressId").show();
		}
		else {
			$("#isLinkAddressId").hide();
		}
		if($("#addPlatformType").val() == 1) {//app平台
			$("#appImgUrlId").show();
			$("#wapImgUrlId").hide();
		}
		else if($("#addPlatformType").val() == 2){//h5平台
			$("#appImgUrlId").hide();
			$("#wapImgUrlId").show();
		}
		else {
			$("#appImgUrlId").show();
			$("#wapImgUrlId").show();
		}
	}
}

/**
 * 链接地址是否显示 
 * @param type 0：不显示 1：显示
 */
function displayLinkUrlAddress(type) {
	if(type == 1) {
		$("#isLinkAddressId").show();
	} 
	else {
		$("#isLinkAddressId").hide();
	}
}

/**
 * code 0:app+h5 1:app 2:h5
 * @param code
 */
function changeActivityPlatFrom(code) {
	if($("#addType").val() == 5) {//系统推荐
    	$("#labelTypeId").hide();
		$("#displayTypeId").hide();
		$("#isLinkRadioId").hide();
		$("#isLinkAddressId").hide();
		$("#appImgUrlId").hide();
		$("#wapImgUrlId").hide();
		return;
    }
	
	//如果是导航
	if($("#displayTypeId1").prop("checked")){
		displayTypeF(1);
		$("#appImgUrlId").hide();
		$("#wapImgUrlId").hide();
	}
	//如果是首页
	if($("#displayTypeId0").prop("checked")){
		displayTypeF(0);
		if(code == 1 ) {
			$("#wapImgUrlId").hide();
			$("#appImgUrlId").show();
		}
		else if(code == 2) {
			$("#wapImgUrlId").show();
			$("#appImgUrlId").hide();
		}
		else {
			$("#appImgUrlId").show();
			$("#wapImgUrlId").show();
		}
	}
}

function resetParamValue() {
	//如果支持链接是否，则清空链接地址
	if($("#isLinkUrlId0").prop("checked") == true) {
		$("#linkUrlId").val("");
	}
	//如果是导航，则首页中标签都至成默认
	if($("#displayTypeId1").prop("checked")){
		$("#isLinkUrlId0").prop("checked",true);
		$("#linkUrlId").val("");
		
	}
	//如果是首页，则导航标签都设置成默认
	if($("#displayTypeId0").prop("checked")){
		$("#labelTypeId0").prop("checked",true);
	}
	
}


