package com.wetterwidget.backend.facade.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class FavoritenDto {
	
	  private UUID   user_id;
	  
	  private String cookieid;
	  
	  private String openweatherid_stadt;
	  
	  private String widgetnumber;
	  
	  private String stadt;
	  
	  private String count;
}
