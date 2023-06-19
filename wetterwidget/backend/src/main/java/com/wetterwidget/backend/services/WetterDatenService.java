package com.wetterwidget.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wetterwidget.backend.entities.Wetterdaten;
import com.wetterwidget.backend.entities.repositiories.WetterdatenRepository;
import com.wetterwidget.backend.facade.dto.WetterdatenDto;
import com.wetterwidget.backend.utils.JsonNodeParser;

@Service
public class WetterDatenService {

    @Value("${wetterwidget.api.key}")
    private String apiKey;

    @Value("${wetterwidget.api.lang}")
    private String apiLang;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ApiService apiservice;
    
    @Autowired
    private WetterdatenRepository wetterrepo;

    public WetterdatenDto getWeatherDataByCity(String cityName) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid=f56decc156db3bb60627dc012bec274e&lang={apiLang}&units=metric";


            ResponseEntity < String > response = restTemplate.getForEntity(url, String.class, cityName, apiLang);

            String jsonResponse = response.getBody();
            JsonNode root = mapper.readValue(jsonResponse, JsonNode.class);
            JsonNodeParser nodeParser = new JsonNodeParser(root);

            return apiservice.getAktuelleWetterdatenfromCoordinates(nodeParser.get("coord.lat").floatValue(), nodeParser.get("coord.lon").floatValue(), nodeParser.get("id").toString());

        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Exception parsing json entity from api call response!", e);
        }


    }

    public WetterdatenDto getWeatherDatabyweatherId(String ID) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://api.openweathermap.org/data/2.5/weather?id={weatherId}&appid=f56decc156db3bb60627dc012bec274e";

            ResponseEntity < String > response = restTemplate.getForEntity(url, String.class, ID, apiKey);

            String jsonResponse = response.getBody();
            JsonNode root = mapper.readValue(jsonResponse, JsonNode.class);
            JsonNodeParser nodeParser = new JsonNodeParser(root);
            return apiservice.getAktuelleWetterdatenfromCoordinates(nodeParser.get("coord.lat").floatValue(), nodeParser.get("coord.lon").floatValue(), ID);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Exception parsing json entity from api call response!", e);
        }
    }
    
    public List<Wetterdaten> getWeatherHistoriebyweatherId(String ID){
    	return wetterrepo.findAllByopenweatherid(ID);
    }
}