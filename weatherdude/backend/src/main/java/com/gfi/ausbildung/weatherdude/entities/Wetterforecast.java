package com.gfi.ausbildung.weatherdude.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Wetterforecast
{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "BINARY(16)")
  private UUID          id;

  private String        kurzText;

  private String        beschreibung;

  private String        symbol;

  private LocalDateTime messzeitpunkt;

  private LocalDateTime sunrise;

  private LocalDateTime sunset;

  private int           temp_day;

  private int           temp_min;

  private int           temp_max;

  private int           temp_night;

  private int           temp_eve;

  private int           temp_morn;

  private int           feels_like_day;

  private int           feels_like_night;

  private int           feels_like_eve;

  private int           feels_like_morn;

  private float         pressure;

  private float         humidity;

  private float         wind_speed;

  private float         wind_deg;

  private float         clouds;

  private float         rain;

  @ElementCollection
  private List<String>  alertMessages;

  @Override
  public String toString()
  {
    return "Wetterforecast [id=" + id + ", kurzText=" + kurzText + ", beschreibung=" + beschreibung + ", symbol="
        + symbol + ", messzeitpunkt=" + messzeitpunkt + ", sunrise=" + sunrise + ", sunset=" + sunset + ", temp_day="
        + temp_day + ", temp_min=" + temp_min + ", temp_max=" + temp_max + ", temp_night=" + temp_night + ", temp_eve="
        + temp_eve + ", temp_morn=" + temp_morn + ", feels_like_day=" + feels_like_day + ", feels_like_night="
        + feels_like_night + ", feels_like_eve=" + feels_like_eve + ", feels_like_morn=" + feels_like_morn
        + ", pressure=" + pressure + ", humidity=" + humidity + ", wind_speed=" + wind_speed + ", wind_deg=" + wind_deg
        + ", clouds=" + clouds + ", rain=" + rain + ", alertMessages=" + alertMessages + "]";
  }

}
