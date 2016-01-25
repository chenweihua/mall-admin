package com.mall.admin.util;

import java.text.DecimalFormat;

public class MoneyUtils {
	
	public static long yuan2Fen(double yuan) {
		DecimalFormat formatter = new DecimalFormat("#0.00");
	    String tmp = formatter.format(yuan);
	    String fen = tmp.replace(".", "");
	    return Long.parseLong(fen);
	}
	
	public static String fen2Yuan(long fen) {
		DecimalFormat formatter = new DecimalFormat("#0.00");
	    String yuan = formatter.format(fen/100.0);
	    return yuan;
	}
	
	public static void main(String[]args) {
		System.out.println(MoneyUtils.yuan2Fen(112));
		System.out.println(MoneyUtils.fen2Yuan(1012));
	}

}
