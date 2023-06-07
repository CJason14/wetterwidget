package com.gfi.ausbildung.weatherdude.utils;

/**
 * In diesem enum werden alle Wettericons mit ihrer ID und dem Dateipfad gespeichert
 * @author eiserfey
 *
 */
public enum Wettergrafik
{
  DATEI_01d("01d", "/src/main/resources/wettergrafiken/01d@2x.png"),
  DATEI_02d("02d", "/src/main/resources/wettergrafiken/02d@2x.png"),
  DATEI_03d("03d", "/src/main/resources/wettergrafiken/03d@2x.png"),
  DATEI_04d("04d", "/src/main/resources/wettergrafiken/04d@2x.png"),
  DATEI_09d("09d", "/src/main/resources/wettergrafiken/09d@2x.png"),
  DATEI_10d("10d", "/src/main/resources/wettergrafiken/10d@2x.png"),
  DATEI_11d("11d", "/src/main/resources/wettergrafiken/11d@2x.png"),
  DATEI_13d("13d", "/src/main/resources/wettergrafiken/13d@2x.png"),
  DATEI_50d("50d", "/src/main/resources/wettergrafiken/50d@2x.png"),
  DATEI_01n("01n", "/src/main/resources/wettergrafiken/01n@2x.png"),
  DATEI_02n("02n", "/src/main/resources/wettergrafiken/02n@2x.png"),
  DATEI_03n("03n", "/src/main/resources/wettergrafiken/03n@2x.png"),
  DATEI_04n("04n", "/src/main/resources/wettergrafiken/04n@2x.png"),
  DATEI_09n("09n", "/src/main/resources/wettergrafiken/09n@2x.png"),
  DATEI_10n("10n", "/src/main/resources/wettergrafiken/10n@2x.png"),
  DATEI_11n("11n", "/src/main/resources/wettergrafiken/11n@2x.png"),
  DATEI_13n("13n", "/src/main/resources/wettergrafiken/13n@2x.png"),
  DATEI_50n("50n", "/src/main/resources/wettergrafiken/50n@2x.png");

  
  public String id;
  public String dateiname;
  
  private Wettergrafik(String id, String dateiname)
  {
    this.id = id;
    this.dateiname = dateiname;
  }
  
  /**
   * Finden einer Datei anhand ihrer ID
   * 
   * @param id
   * @return String
   */
  public String getDateinameVonId(String id)
  {
    switch(id)
    {
      case "01d": return DATEI_01d.dateiname;
      case "02d": return DATEI_02d.dateiname;
      case "03d": return DATEI_03d.dateiname;
      case "04d": return DATEI_04d.dateiname; 
      case "09d": return DATEI_09d.dateiname;
      case "10d": return DATEI_10d.dateiname;
      case "11d": return DATEI_11d.dateiname;
      case "13d": return DATEI_13d.dateiname;
      case "50d": return DATEI_50d.dateiname;
      case "01n": return DATEI_01n.dateiname;
      case "02n": return DATEI_02n.dateiname;
      case "03n": return DATEI_03n.dateiname;
      case "04n": return DATEI_04n.dateiname; 
      case "09n": return DATEI_09n.dateiname;
      case "10n": return DATEI_10n.dateiname;
      case "11n": return DATEI_11n.dateiname;
      case "13n": return DATEI_13n.dateiname;
      case "50n": return DATEI_50n.dateiname;
      default: return null;
    }
  }
  
}
