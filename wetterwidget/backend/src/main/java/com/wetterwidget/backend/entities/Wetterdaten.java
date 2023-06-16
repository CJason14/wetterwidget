package com.wetterwidget.backend.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;



@Data
@Entity
@DynamicUpdate
@Table(name = "wetterdaten")
public class Wetterdaten {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "BINARY(16)")
	
	private UUID id;
	
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
	
	private String stadt;
	
	private float lat;
	
	private float lon;
	
	private String kurz_beschreibung;
	
	private int unix_timestamp;
	
	private String openweatherid;

  @Override
  public String toString()
  {
    return "Wetterdaten [id=" + id + ", temperatur_aktuell=" + temperatur_aktuell + ", temperatur_gefuehlt="
        + temperatur_gefuehlt + ", temperatur_min=" + temperatur_min + ", temperatur_max=" + temperatur_max
        + ", luftdruck=" + luftdruck + ", luftfeuchtigkeit=" + luftfeuchtigkeit + ", wind_geschwindigkeit="
        + wind_geschwindigkeit + ", wind_richtung=" + wind_richtung + ", bewölkung=" + bewölkung + ", niederschlag="
        + niederschlag + "]";
  }

  public void transferAktuelleWetterdaten(Wetterdaten wetterdaten)
  {
    this.setTemperatur_aktuell(wetterdaten.getTemperatur_aktuell());
    this.setTemperatur_gefuehlt(wetterdaten.getTemperatur_gefuehlt());
    this.setTemperatur_min(wetterdaten.getTemperatur_min());
    this.setTemperatur_max(wetterdaten.getTemperatur_max());
    this.setLuftdruck(wetterdaten.getLuftdruck());
    this.setLuftfeuchtigkeit(wetterdaten.getLuftfeuchtigkeit());
    this.setWind_geschwindigkeit(wetterdaten.getWind_geschwindigkeit());
    this.setWind_richtung(wetterdaten.getWind_richtung());
    this.setBewölkung(wetterdaten.getBewölkung());
    this.setNiederschlag(wetterdaten.getNiederschlag());
	  }
	  
}
