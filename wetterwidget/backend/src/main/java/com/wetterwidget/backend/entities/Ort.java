package com.wetterwidget.backend.entities;

import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
// @DynamicUpdate
public class Ort
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "BINARY(16)")
  @JsonIgnore
  private UUID                 id;

  @Column(length = 20)
  private String               openWeatherId;

  @Column(length = 50)
  private String               stadt;

  private double               lon;

  private double               lat;

  @Column(length = 20)
  private String               land;

  public Ort()
  {
  }

  public Ort(UUID id, String openWeatherId, String stadt, double lon, double lat, String land)
  {
    this.id = id;
    this.openWeatherId = openWeatherId;
    this.stadt = stadt;
    this.lon = lon;
    this.lat = lat;
    this.land = land;

  }

  public UUID getId()
  {
    return id;
  }

  public void setId(UUID id)
  {
    this.id = id;
  }

  @JsonProperty("id")
  public String getOpenWeatherId()
  {
    return openWeatherId;
  }

  public void setOpenWeatherId(String openWeatherId)
  {
    this.openWeatherId = openWeatherId;
  }

  @JsonProperty("name")
  public String getStadt()
  {
    return stadt;
  }

  public void setStadt(String stadt)
  {
    this.stadt = stadt;
  }

  public double getLon()
  {
    return lon;
  }

  public void setLon(double lon)
  {
    this.lon = lon;
  }

  public double getLat()
  {
    return lat;
  }

  public void setLat(double lat)
  {
    this.lat = lat;
  }

  @JsonProperty("country")
  public String getLand()
  {
    return land;
  }

  public void setLand(String land)
  {
    this.land = land;
  }


  @Override
  public String toString()
  {
    return "Ort [id=" + id + ", stadt=" + stadt + ", lon=" + lon + ", lat=" + lat + ", land=" + land + "]";
  }

  @JsonProperty("coord")
  private void unpackNested(Map<String, Object> coord)
  {
    this.lat = (Double) coord.get("lat");
    this.lon = (Double) coord.get("lon");

  }
}
