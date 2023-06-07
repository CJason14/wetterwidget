package com.wetterwidget.backend.facade.dto;

import lombok.Data;

@Data
public class CityWeatherDto {
	
	private String stadt;
	
	private String openWeatherId;
	
	private String kurzBeschreibung;
	
	private float aktuelle_temperatur;
}
