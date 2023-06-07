package com.gfi.ausbildung.weatherdude.facade.dto;

import java.io.Serializable;

import com.gfi.ausbildung.weatherdude.entities.Ort;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper=true)
@NoArgsConstructor
public class OrtVorschlagDto implements Serializable
{
  private static final long serialVersionUID = 8171370972694168572L;
  
  private String        openWeatherId;
  private String        stadt;
  
  /**
   * Erzeugt aus einem Ort Objekt ein entsprechendes Dto Objekt.
   * @param ort umzuwandelnder Ort
   * @return OrtDto Objekt
   */
  public static OrtVorschlagDto createDtoByOrt(Ort ort)
  {
    OrtVorschlagDto dto = new OrtVorschlagDto();
    dto.setStadt(ort.getStadt());
    dto.setOpenWeatherId(ort.getOpenWeatherId());
    
    return dto;
  }
}
