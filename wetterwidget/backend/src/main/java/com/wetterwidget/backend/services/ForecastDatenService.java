package com.wetterwidget.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wetterwidget.backend.utils.JsonNodeParser;


@Service
public class ForecastDatenService {

    @Value("${wetterwidget.api.key}")
    private String apiKey;

    @Value("${wetterwidget.api.lang}")
    private String apiLang;

    @Autowired
    private ObjectMapper mapper;

    public List < List < String >> getForecastbyOpenweatherID(String ID) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            System.out.println("ApiAnfrage");
            String url = "http://api.openweathermap.org/data/2.5/weather?id={weatherId}&appid=f56decc156db3bb60627dc012bec274e";

            ResponseEntity < String > response = restTemplate.getForEntity(url, String.class, ID, apiKey);

            String jsonResponse = response.getBody();
            JsonNode root = mapper.readValue(jsonResponse, JsonNode.class);
            JsonNodeParser nodeParser = new JsonNodeParser(root);
            return getForecastbyCoords(nodeParser.get("coord.lat").floatValue(), nodeParser.get("coord.lon").floatValue(), ID);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Exception parsing json entity from api call response!", e);
        }
    }
    public List < List < String >> getForecastbyCoords(float lat, float lon, String ID) {
        try {
            RestTemplate template = new RestTemplate();
            System.out.println("ApiAnfrage");
            String url = "http://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid=f56decc156db3bb60627dc012bec274e&lang={apiLang}&units=metric";

            ResponseEntity < String > response = template.getForEntity(url, String.class, lat, lon, "de");

            String jsonResponse = response.getBody();
            JsonNode root = mapper.readValue(jsonResponse, JsonNode.class);
            JsonNodeParser nodeParser = new JsonNodeParser(root);
            List < String > listtemp = new ArrayList < > ();
            List < String > listhumidity = new ArrayList < > ();
            List < String > listmin = new ArrayList < > ();
            List < String > listmax = new ArrayList < > ();
            List < String > listclouds = new ArrayList < > ();
            for (int i = 0; 24 > i; i++) {
                listtemp.add(nodeParser.get("list." + i + ".main.temp").toString());
                listhumidity.add(nodeParser.get("list." + i + ".main.humidity").toString());
                listmin.add(nodeParser.get("list." + i + ".main.temp_min").toString());
                listmax.add(nodeParser.get("list." + i + ".main.temp_max").toString());
                listclouds.add(nodeParser.get("list." + i + ".clouds.all").toString());
                
            }
            List < List < String >> listlist = new ArrayList < > ();
            listlist.add(listtemp);
            listlist.add(listhumidity);
            listlist.add(listmin);
            listlist.add(listmax);
            listlist.add(listclouds);

            return listlist;
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Exception parsing json entity from api call response!", e);
        }
    }
}