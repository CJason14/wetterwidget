package com.gfi.ausbildung.weatherdude.facade.dto;

import com.gfi.ausbildung.weatherdude.entities.Ort;
import com.gfi.ausbildung.weatherdude.entities.OrtFavorit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrtFavoritDto
{

  private int    prioritaet;
  private String openWeatherId;

  public static OrtFavoritDto createDtoByFavorit(OrtFavorit favorit)
  {
    Ort ort = favorit.getFavorit();
    String openWeatherId = ort.getOpenWeatherId();
    return new OrtFavoritDto(favorit.getPrioritaet(), openWeatherId);
  }

}
