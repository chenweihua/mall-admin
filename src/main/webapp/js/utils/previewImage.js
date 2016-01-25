function PreviewImage(number) {
	var image = document.getElementById('uploadImage'+number).files[0];
	if (image.size > 2*1024*1024) {
		alert("图片不能超过2M!");
		return;
	}
	var type = image.type;
	supportedType = new Array("image/jpeg", "image/png", "image/bmp");
	if (supportedType.indexOf(type) < 0) {
		alert("not support file type \t" + type);
		return;
	}
	
    var oFReader = new FileReader();
    oFReader.readAsDataURL(image);
    oFReader.onload = function (oFREvent) {        	
        $.ajax({
        	type: 'POST',
            enctype : 'multipart/form-data',
            url: '/tool/image/upload',
            data: {'content': oFREvent.target.result, 'name' : image.name},
            success : function(result) {
            	if (result.code == 0) {
                	document.getElementById('uploadPreview'+number).src = oFREvent.target.result;
                	var id = "imageUrl" + number;
                	$("#" + id).val(result.data);
            	}else {
            		alert("上传失败,"+result.msg);
            	}
            },
            error: function(e) {
                if(typeof console != undefined){
                    console.log(e);
                }
            	alert(e);
            }
        });
    };
}

function PreviewMutiImage(uploadImage, uploadPreview, imageUrl, maxCount, imageUrlShowId) {
	var image = document.getElementById(uploadImage).files[0];
	if (image.size > 2*1024*1024) {
		alert("图片不能超过2M!");
		return;
	}
	var type = image.type;
	supportedType = new Array("image/jpeg", "image/png", "image/bmp");
	if (supportedType.indexOf(type) < 0) {
		alert("not support file type \t" + type);
		return;
	}
	//已经上传数量
	var imageCount = $('span[id^='+uploadPreview+']').size();
	if(imageCount >= maxCount) {
		alert("已超过最大上传数");
		return;
	}
	var maxNum = 0;//最大数
	$('span[id^='+uploadPreview+']').each(function(){
		var num = parseInt($(this).attr("id").replace(/[^0-9]/ig,""));
		if(maxNum < num){
			maxNum = num;
		}
	});
    var oFReader = new FileReader();
    oFReader.readAsDataURL(image);
    oFReader.onload = function (oFREvent) {        	
        $.ajax({
        	type: 'POST',
            enctype : 'multipart/form-data',
            url: '/tool/image/upload',
            data: {'content': oFREvent.target.result, 'name' : image.name},
            success : function(result) {
            	if (result.code == 0) {
            		if(imageCount == 1 && $("#"+uploadPreview+1+ " img").attr("src") == '') {
            			$("#"+uploadPreview+1+ " img").attr("src",oFREvent.target.result);
            			$("#"+uploadPreview+1+ " input").val(result.data);
            			$("#"+uploadPreview+1).append('<a href="javascript:void(0);" onclick="removeElement(\''+uploadPreview+(maxNum)+'\',\''+ uploadImage + '\');">删除</a>');
            		}
            		else {
            			var imageUrlShow = '<span id="'+uploadPreview+(maxNum+1)+'">'+'<img  src="'+oFREvent.target.result+'" style="width:200px" alt="商品大图"/>'+
            			'<a href="javascript:void(0);" onclick="removeElement(\''+uploadPreview+(maxNum+1)+'\',\'' + uploadImage + '\');">删除</a><br />'+
            			'<input type="hidden" name="'+imageUrl+'" value="'+result.data+'"></span>'
            			if(imageUrlShowId) {
            				$("#" + imageUrlShowId).append(imageUrlShow);
            			} else {
            				$("#" + uploadImage).parent().append(imageUrlShow);
            			}
            		}
            	}else {
            		alert("上传失败,"+result.msg);
            	}
            },
            error: function(e) {
                if(typeof console != undefined){
                    console.log(e);
                }
            	alert(e);
            }
        });
    };
}

function removeElement(uploadPreview,fileChooserId){
	$("#" + uploadPreview).remove();
	$("#" + fileChooserId).val("");
}
