function printLable(expNum, storeName, expType, expTime, deliveryName, expPrice, payStatus, userName, userAddress, mobilePhone, description, expCode){
	try{
		var TSCObj = new ActiveXObject("TSCActiveX.TSCLIB");
		TSCObj.ActiveXopenport ("Gprinter GP-3120TL");


		TSCObj.ActiveXsetup ("65","55","4","8","1","0","0");
		TSCObj.ActiveXsendcommand ("SET TEAR ON");

		TSCObj.ActiveXclearbuffer();
		expNum = "订单号:" + expNum;
		storeName = "店   铺:" + storeName;
		expType = "方   式:" + expType;
		expTime = "时   段:" + expTime;
		
		TSCObj.ActiveXwindowsfont (520, 430, 25, 180, 0, 0, "arial", expNum);
		TSCObj.ActiveXwindowsfont (520, 405, 25, 180, 0, 0, "arial", storeName);
		TSCObj.ActiveXwindowsfont (520, 380, 25, 180, 0, 0, "arial", expType);
		TSCObj.ActiveXwindowsfont (520, 355, 25, 180, 0, 0, "arial", expTime);

		TSCObj.ActiveXbarcode ("270", "430", "128", "100", "1", "180", "2", "2", expCode);
		
		var line  = '━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━';
		var line1  = '━━━━━━';

		
		TSCObj.ActiveXwindowsfont (520, 320, 40, 180, 0, 0, "arial", line);

		
		TSCObj.ActiveXwindowsfont (520, 290, 25, 180, 0, 0, "arial", "商品内容:");
		
		

		var height = 260;
		var length = deliveryName.length;
		var start = 0;
		while (start < length) {
			TSCObj.ActiveXwindowsfont (520, height, 30, 180, 0, 0, "arial", deliveryName.substr(start, start+20));
			height =  height - 30;
			start = start + 20; 
		}
		
		
		TSCObj.ActiveXwindowsfont (520, height, 40, 180, 0, 0, "arial", line);	
		height = height - 25;
				
		
		totalPrice = "支付价格:" + expPrice + "元";
		if (payStatus) {
			 totalPrice = totalPrice + "(已付款)";
			//TSCObj.ActiveXwindowsfont (240, height, 25, 180, 0, 0, "arial", "(已付款)");
		} else {
			totalPrice = totalPrice + "(未付款)";
			//TSCObj.ActiveXwindowsfont (240, height, 25, 180, 0, 0, "arial", "(未付款)");
		}		
		TSCObj.ActiveXwindowsfont (520, height, 32, 180, 0, 0, "arial", totalPrice);
		
		//var expPrice1 = "原价:" + expPrice + "元";
		//TSCObj.ActiveXwindowsfont (180, height-10, 25, 180, 0, 0, "arial", expPrice1);

				
		height = height - 25;		
		//TSCObj.ActiveXwindowsfont (520, height, 25, 180, 0, 0, "arial", "运费:5元(已优惠)");


		height = height - 25;
		TSCObj.ActiveXwindowsfont (520, height, 40, 180, 0, 0, "arial", line);
		
		height = height - 25;
		userName = "用户姓名:    " + userName;
		TSCObj.ActiveXwindowsfont (520, height, 32, 180, 0, 0, "arial", userName);
		
		height = height - 35;
		mobilePhone = "电       话:    " + mobilePhone;
		TSCObj.ActiveXwindowsfont (520, height, 32, 180, 0, 0, "arial", mobilePhone);
		
		/*userAddress = "收货地址:" + userAddress;
		var length = userAddress.length;
		var start = 0;
		while (start < length) {
			TSCObj.ActiveXwindowsfont (280, height, 30, 180, 0, 0, "arial", userAddress.substr(start, start+10));
			height =  height - 25;
			start = start + 10; 
		}*/
			
		
		height = height-25;
		TSCObj.ActiveXwindowsfont (520, height, 40, 180, 0, 0, "arial", line);

		
		height = height - 25;
		description = "小麦商城客服电话:" + description;
		TSCObj.ActiveXwindowsfont (520, height, 25, 180, 0, 0, "arial", description);

		/*var length = description.length;
		var start = 0;
		while (start < length) {
			TSCObj.ActiveXwindowsfont (520, height, 30, 180, 0, 0, "arial", description.substr(start, start+20));
			height =  height - 25;
			start = start + 20; 
		}*/
		
		//TSCObj.ActiveXwindowsfont (320, 55, 38, 0, 0, 0, "arial", getAMorPM());
		
		var len = expCode.length;
		//TSCObj.ActiveXwindowsfont (425, 120, 120, 180, 2, 0, "arial", expCode.substring(len-4, len));

		TSCObj.ActiveXprintlabel ("1","1");
		TSCObj.ActiveXcloseport();
	}catch(e){ 
		alert(e);
	} 
}

function getDate(){
	var d, s = "";   
	d = new Date();
    s += (d.getMonth() + 1) + "月";   
	s += d.getDate()+"日";
    return(s);
}

function getAMorPM(){
	var d, s = "";   
	d = new Date();
    s += (d.getHours() >= 12) ? "上午" : "下午";   
    return(s);
}
