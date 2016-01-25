package com.mall.admin.util;

import java.util.Collection;

public class CollectionUtil<T> {
	
	
	public static <T> String join(Collection<T> collection, String joint) {
		
		if(collection == null || collection.size() == 0) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		for(T c : collection) {
			sb.append(c.toString()).append(joint);
		}
		
		if(sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		
		return sb.toString();
	}
	

}
