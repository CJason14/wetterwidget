package com.gfi.ausbildung.weatherdude.facade.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindResponseDto
{
  private int    openWeatherId;
  private String errorMessage;

}
