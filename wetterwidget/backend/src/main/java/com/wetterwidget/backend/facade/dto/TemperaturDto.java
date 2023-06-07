package com.wetterwidget.backend.facade.dto;

import lombok.Data;

@Data
public class TemperaturDto {
	
	private String stadt;
	
	private String openWeatherId;
	
	private float temperatur_aktuell;
	
	private float temperatur_gefuehlt;
	
	private float temperatur_max;
	
	private float temperatur_min;
}
