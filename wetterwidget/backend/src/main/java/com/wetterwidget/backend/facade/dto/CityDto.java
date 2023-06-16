package com.wetterwidget.backend.facade.dto;

import lombok.Data;

@Data
public class CityDto {
	
	private String stadt;
	
	private String openWeatherId;
	
	public int niederschlag;
	
	public int luftfeuchtigkeit;
	
	public float aktuelle_temperatur;
	
	public float lat;
	
	public float lon;
}
