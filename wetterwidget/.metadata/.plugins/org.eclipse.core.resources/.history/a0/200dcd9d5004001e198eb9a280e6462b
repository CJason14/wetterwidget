package com.wetterwidget.backend.services;


import org.springframework.stereotype.Service;

import com.wetterwidget.backend.entities.Ort;


@Service
public class ApiService {
	
    public void getAktuelleWetterdatenFuerOrt(Ort ort)
    {	
    	String url = "https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&appid={apiKey}&exclude=minutely,hourly&lang={apiLang}&units=metric";
        double lat = ort.getLat();
        double lon = ort.getLon();
        
    }
      
}
