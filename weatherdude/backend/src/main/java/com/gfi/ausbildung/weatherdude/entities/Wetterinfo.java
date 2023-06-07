package com.gfi.ausbildung.weatherdude.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Wetterinfo
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  private String kurzText;

  private String beschreibung;

  private String symbol;

  public UUID getId()
  {
    return id;
  }

  public void setId(UUID id)
  {
    this.id = id;
  }

  public String getKurzText()
  {
    return kurzText;
  }

  public void setKurzText(String kurzText)
  {
    this.kurzText = kurzText;
  }

  public String getBeschreibung()
  {
    return beschreibung;
  }

  public void setBeschreibung(String beschreibung)
  {
    this.beschreibung = beschreibung;
  }

  public String getSymbol()
  {
    return symbol;
  }

  public void setSymbol(String symbol)
  {
    this.symbol = symbol;
  }

  @Override
  public String toString()
  {
    return "Wetterinfo [id=" + id + ", kurzText=" + kurzText + ", beschreibung="
        + beschreibung + ", symbol=" + symbol + "]";
  }
  
  /**
   * Übernimmt die Fachdaten aus einer aktuellen Wetterdatenabfrage
   * @param wetterinfo
   */
  public void transferAktuelleWetterinfos(Wetterinfo wetterinfo)
  {
    this.setBeschreibung(wetterinfo.getBeschreibung());
    this.setKurzText(wetterinfo.getKurzText());
    this.setSymbol(wetterinfo.getSymbol());
  }
}
