package com.wetterwidget.backend.facade.dto;

import lombok.Data;

@Data
public class WindDto {
	
	private String stadt;
	
	private String openWeatherId;
	
	private int windrichtung;
	
	private int windgeschwindigkeit;
}
