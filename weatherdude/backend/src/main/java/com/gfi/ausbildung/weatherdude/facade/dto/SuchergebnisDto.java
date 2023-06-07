package com.gfi.ausbildung.weatherdude.facade.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
/**
 * Trefferobjekt der Suche Entweder wurden aktuelle Wetter Daten gefunden oder die Suche war nicht eindeutig, sodass
 * eine Vorschlagsliste fuer Orte zurückgegeben wird oder es wurde nichts gefunden
 * 
 * @author Venohr
 */
public class SuchergebnisDto implements Serializable
{

  private static final long     serialVersionUID = 521206967717692898L;

  private OrtDto                aktWetterinfo;
  private List<OrtVorschlagDto> ortVorschlaege;
}
