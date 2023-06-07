package com.wetterwidget.backend.facade.dto;

import java.io.Serializable;
import com.wetterwidget.backend.entities.Ort;

import lombok.Data;

@Data
public class PreviewDto implements Serializable{
	
	  private static final long serialVersionUID = 8171370972694168572L;

	  private String stadt;
	  
	  private String openWeatherId;
	  
	  private float temperatur_aktuell;
	  
	  public static PreviewDto createPreviewbyOrt(Ort ort) {
		  PreviewDto dto = new PreviewDto();
		  
		  dto.setStadt(ort.getStadt());
		  dto.setOpenWeatherId(ort.getOpenWeatherId());
		  dto.setTemperatur_aktuell(0);
		  return dto;
	  }
}
