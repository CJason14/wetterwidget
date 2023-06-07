package com.gfi.ausbildung.weatherdude.facade.dto;

import java.io.Serializable;

import com.gfi.ausbildung.weatherdude.entities.Ort;
import com.gfi.ausbildung.weatherdude.entities.Wetterdaten;
import com.gfi.ausbildung.weatherdude.entities.Wetterinfo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class OrtDto implements Serializable
{
  private static final long serialVersionUID = 8171370972694168572L;

  private String            openWeatherId;

  private String            stadt;

  private String            aktWetterBeschreibung;

  private String            aktWetterSymbol;

  private float             temperatur_aktuell;

  private float             temperatur_gefuehlt;

  private float             temperatur_min;

  private float             temperatur_max;

  private int               luftdruck;

  private int               luftfeuchtigkeit;

  private float             wind_geschwindigkeit;

  private int               wind_richtung;

  private int               bewölkung;

  private int               niederschlag;

  /**
   * Erzeugt aus einem Ort Objekt ein entsprechendes Dto Objekt.
   * 
   * @param ort umzuwandelnder Ort
   * @return OrtDto Objekt
   */
  public static OrtDto createDtoByOrt(Ort ort)
  {
    OrtDto dto = new OrtDto();
    dto.setStadt(ort.getStadt());
    dto.setOpenWeatherId(ort.getOpenWeatherId());

    if (ort.getAktMesszeitpunkt() != null && ort.getAktMesszeitpunkt().getWetterinfo() != null
        && ort.getAktMesszeitpunkt().getWetterdaten() != null)
    {
      Wetterinfo info = ort.getAktMesszeitpunkt().getWetterinfo();
      Wetterdaten daten = ort.getAktMesszeitpunkt().getWetterdaten();
      dto.setAktWetterBeschreibung(info.getBeschreibung());
      dto.setAktWetterSymbol(info.getSymbol());

      dto.setBewölkung(daten.getBewölkung());
      dto.setTemperatur_aktuell(daten.getTemperatur_aktuell());
      dto.setTemperatur_gefuehlt(daten.getTemperatur_gefuehlt());
      dto.setTemperatur_min(daten.getTemperatur_min());
      dto.setTemperatur_max(daten.getTemperatur_max());
      dto.setLuftfeuchtigkeit(daten.getLuftfeuchtigkeit());
      dto.setLuftdruck(daten.getLuftdruck());
      dto.setWind_geschwindigkeit(daten.getWind_geschwindigkeit());
      dto.setWind_richtung(daten.getWind_richtung());
      dto.setNiederschlag(daten.getNiederschlag());
    }

    return dto;
  }

}
