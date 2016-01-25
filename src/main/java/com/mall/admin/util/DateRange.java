package com.mall.admin.util;

import java.util.Calendar;
import java.util.Date;

public class DateRange {
	
	private Date startDate;
	
	private Date endDate;
	
	public DateRange(Date startDate,Date endDate) {
		if(startDate == null || endDate == null) {
			throw new RuntimeException("DateRange入参不得为空！");
		}
		if(startDate.after(endDate)) {
			throw new RuntimeException("DateRange入参startDate不得早于endDate！");
		}
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	
	
	/**
	 * 判断当前DateRange与给出的参数dateRange是否有交集，有返回true，无返回false
	 * @param dateRange
	 * @return
	 */
	public boolean overlaps(DateRange dateRange) {
		
		
		if(dateRange.getStartDate().after(endDate) || dateRange.getEndDate().before(startDate)) {
			return false;
		}
		
		return true;
	}
	
	
	

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	
	public static void main(String[] args) {
		
		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.MONTH, -1);
		Date d1 = c1.getTime();
		Date d2 = new Date();
		new DateRange(d1,d2);
	}

}
