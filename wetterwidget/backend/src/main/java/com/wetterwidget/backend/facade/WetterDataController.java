package com.wetterwidget.backend.facade;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wetterwidget.backend.entities.*;
import com.wetterwidget.backend.entities.repositiories.OrtRepository;
import com.wetterwidget.backend.facade.dto.*;
import com.wetterwidget.backend.services.ApiService;
import com.wetterwidget.backend.services.FavoritenDatenService;
import com.wetterwidget.backend.services.ForecastDatenService;
import com.wetterwidget.backend.services.WetterDatenService;


@CrossOrigin
@RestController
@RequestMapping("/wetterdata")
public class WetterDataController {
	
	@Autowired
	private ApiService   apiService;
	
	@Autowired
	private FavoritenDatenService   dataService;
	 
	
	@Autowired
	private OrtRepository ortrepo;
	
	@Autowired
	private WetterDatenService wetterdatenservice;
	
	@Autowired
	private ForecastDatenService forecastdatenservice;
	
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
    @GetMapping("/air/{id}")
    public AirDto getAir(@PathVariable String id) {
    	WetterdatenDto wetterdaten = wetterdatenservice.getWeatherDatabyweatherId(id);
    	AirDto dto = new AirDto();
    	dto.setLuftdruck(wetterdaten.getLuftdruck());
    	dto.setLuftfeuchtigkeit(wetterdaten.getLuftfeuchtigkeit());
    	dto.setOpenWeatherId(id);
    	dto.setStadt(wetterdaten.getStadt());
    	System.out.println("Anfrage: Air");
    	return dto;
    }
    
    @CrossOrigin
    @GetMapping("/city/{id}")
    public CityDto getCity(@PathVariable String id) {
    	WetterdatenDto wetterdaten = wetterdatenservice.getWeatherDatabyweatherId(id);
    	CityDto dto = new CityDto();
    	dto.setAktuelle_temperatur(wetterdaten.getTemperatur_aktuell());
    	dto.setLuftfeuchtigkeit(wetterdaten.getLuftfeuchtigkeit());
    	dto.setOpenWeatherId(id);
    	dto.setStadt(wetterdaten.getStadt());
    	System.out.println("Anfrage: City");
    	return dto;
    }
    
    @CrossOrigin
    @GetMapping("/cityweather/{id}")
    public CityWeatherDto getCityWeather(@PathVariable String id) {
    	WetterdatenDto wetterdaten = wetterdatenservice.getWeatherDatabyweatherId(id);
    	CityWeatherDto dto = new CityWeatherDto();
    	dto.setAktuelle_temperatur(wetterdaten.getTemperatur_aktuell());
    	dto.setKurzBeschreibung(wetterdaten.getKurzBeschreibung());
    	dto.setOpenWeatherId(id);
    	dto.setStadt(wetterdaten.getStadt());
    	System.out.println("Anfrage: CityWeather");
    	return dto;
    }
    
    @CrossOrigin
    @GetMapping("/temperatur/{id}")
    public TemperaturDto getTemperatur(@PathVariable String id) {
    	WetterdatenDto wetterdaten = wetterdatenservice.getWeatherDatabyweatherId(id);
    	TemperaturDto dto = new TemperaturDto();
    	dto.setTemperatur_aktuell(wetterdaten.getTemperatur_aktuell());
    	dto.setTemperatur_gefuehlt(wetterdaten.getTemperatur_gefuehlt());
    	dto.setTemperatur_max(wetterdaten.getTemperatur_max());
    	dto.setTemperatur_min(wetterdaten.getTemperatur_min());
    	dto.setOpenWeatherId(id);
    	dto.setStadt(wetterdaten.getStadt());
    	System.out.println("Anfrage: Temperatur");
    	return dto;
    }
    
    @CrossOrigin
    @GetMapping("/wind/{id}")
    public WindDto getWind(@PathVariable String id) {
    	WetterdatenDto wetterdaten = wetterdatenservice.getWeatherDatabyweatherId(id);
    	WindDto dto = new WindDto();
    	dto.setWindrichtung(wetterdaten.getWind_richtung());
    	dto.setWindgeschwindigkeit(wetterdaten.getWind_geschwindigkeit());
    	dto.setOpenWeatherId(id);
    	dto.setStadt(wetterdaten.getStadt());
    	System.out.println("Anfrage: Wind");
    	return dto;
    }
    
    @CrossOrigin
    @GetMapping("/wetterdaten/{id}")
    public WetterdatenDto getWetterdaten(@PathVariable String id) {
    	System.out.println("Anfrage: GetWetterdaten");
    	return wetterdatenservice.getWeatherDatabyweatherId(id);
    }
    
    @CrossOrigin
    @PutMapping("/findcity")
    public CityDto findcity(@RequestBody Position pos) {
    	System.out.println("Anfrage: FindCity");
    	return apiService.findCityfromCoordinates(pos.getLat(), pos.getLon());
    }
    
    @CrossOrigin
    @PutMapping("/checkcity")
    public String checkcity(@RequestBody String input) {
    	System.out.println("Anfrage: Checkcity");
    	return apiService.getCheckCity(input);
    }
    
    @CrossOrigin
    @GetMapping("/checkcity")
    public String[] getcity() {
    	System.out.println("Anfrage: GetCity");
    	String[] list = new String[ortrepo.findAll().size()];
    	List<Ort> ortrepos = ortrepo.findAll();
    	for (int i = 0; ortrepo.findAll().size() > i; i ++) {
    		list[i] = (ortrepos.get(i).getStadt().toString().replace("\"", ""));
    	}
    	return list;
    }
    
    @CrossOrigin
    @PostMapping("/setfavorit")
    public String setfavorit(@RequestBody String input) {
    	System.out.println("Anfrage: SetFavorit");
    	return dataService.setFavoriten(input);
    }
    
    @CrossOrigin
    @PutMapping("/getfavorit")
    public List<FavoritenDto>  getfavorit(@RequestBody String input) {
    	System.out.println("Anfrage: GetFavorit");
    	return dataService.getFavoriten(input);
    }
    
    @CrossOrigin
    @PutMapping("/deletefavorit")
    public String deletefavorit(@RequestBody String input) {
    	System.out.println("Anfrage: DeleteFavorit");
    	return dataService.deleteFavoriten(input);
    }
    
    @CrossOrigin
    @GetMapping("/forecast/{id}")
    public List<List<String>> getForeCast(@PathVariable String id) {
    	System.out.println("Anfrage: GetForecast");
    	return forecastdatenservice.getForecastbyOpenweatherID(id);
    }
}

