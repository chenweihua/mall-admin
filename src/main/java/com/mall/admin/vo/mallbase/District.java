package com.mall.admin.vo.mallbase;

import java.util.List;

public class District {
	private City city;
	private List<City> areas;
	
	public City getCity(){
		return city;
	}
	
	public void setCity(City city){
		this.city = city;
	}
	
	public List<City> getAreas(){
		return areas;
	}
	
	public void setAreas(List<City> areas){
		this.areas = areas;
	}
	
}
