package com.wetterwidget.backend.facade.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class WetterdatenDto {

	private String stadt;
	
	private int bewölkung;
	
	private int luftdruck;
	
	private int luftfeuchtigkeit;
	
	private int niederschlag;
	
	private float temperatur_aktuell;

	private float temperatur_gefuehlt;

	private float temperatur_min;

	private float temperatur_max;
	
	private float wind_geschwindigkeit;
	
	private int   wind_richtung;
}