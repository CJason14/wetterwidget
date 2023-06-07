package com.wetterwidget.backend.facade.dto;

import com.wetterwidget.backend.entities.Ort;

import lombok.Data;

@Data
public class AirDto{
		
	private String stadt;
	
	private String openWeatherId;
	
	private int luftfeuchtigkeit;
	
	private int luftdruck;
	
	public static AirDto createairbyOrt(Ort ort) {
		  AirDto dto = new AirDto();
		  
		  dto.setStadt(ort.getStadt());
		  dto.setOpenWeatherId(ort.getOpenWeatherId());
		  dto.setLuftdruck(0);
		  dto.setLuftfeuchtigkeit(0);
		  return dto;
	  }
	
}
