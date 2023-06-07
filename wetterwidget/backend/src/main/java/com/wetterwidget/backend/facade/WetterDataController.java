package com.wetterwidget.backend.facade;


import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wetterwidget.backend.entities.*;
import com.wetterwidget.backend.facade.dto.*;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@RequestMapping("/wetterdata")
@Slf4j
public class WetterDataController {
	
	@CrossOrigin
    @GetMapping("/online")
	public int getStatus() {
		return 1;
	}
    @GetMapping("/preview")
    public String preview() {
      return String.format("Hello Test");
    }
    
    @CrossOrigin
    @GetMapping("/air")
    public AirDto getAir() {
    	AirDto dto = new AirDto();
    	dto.setLuftdruck(10);
    	dto.setLuftfeuchtigkeit(9);
    	dto.setOpenWeatherId("100");
    	dto.setStadt("Dortmund");
    	log.debug("Anfrage: Air");
    	return dto;
    }
    
    @CrossOrigin
    @GetMapping("/city")
    public CityDto getCity() {
    	CityDto dto = new CityDto();
    	dto.setAktuelle_temperatur(10);
    	dto.setLuftfeuchtigkeit(9);
    	dto.setOpenWeatherId("100");
    	dto.setStadt("Dortmund");
    	log.debug("Anfrage: City");
    	return dto;
    }
    
    @CrossOrigin
    @GetMapping("/cityweather")
    public CityWeatherDto getCityWeather() {
    	CityWeatherDto dto = new CityWeatherDto();
    	dto.setAktuelle_temperatur(10);
    	dto.setKurzBeschreibung("Klarer Himmel");
    	dto.setOpenWeatherId("100");
    	dto.setStadt("Dortmund");
    	log.debug("Anfrage: CityWeather");
    	return dto;
    }
    
    @CrossOrigin
    @GetMapping("/temperatur")
    public TemperaturDto getTemperatur() {
    	TemperaturDto dto = new TemperaturDto();
    	dto.setTemperatur_aktuell(10);
    	dto.setTemperatur_gefuehlt(12);
    	dto.setTemperatur_max(15);
    	dto.setTemperatur_min(7);
    	dto.setOpenWeatherId("100");
    	dto.setStadt("Dortmund");
    	log.debug("Anfrage: Temperatur");
    	return dto;
    }
    
    @CrossOrigin
    @GetMapping("/wind")
    public WindDto getWind() {
    	WindDto dto = new WindDto();
    	dto.setWindrichtung(10);
    	dto.setWindgeschwindigkeit(12);
    	dto.setOpenWeatherId("100");
    	dto.setStadt("Dortmund");
    	log.debug("Anfrage: Wind");
    	return dto;
    }
    
    @CrossOrigin
    @GetMapping("/wetterdaten")
    public WetterdatenDto getWetterdaten() {
    	WetterdatenDto dto = new WetterdatenDto();
    	dto.setBewölkung(10);
    	dto.setId(10)
    	return dto;
    }
}

