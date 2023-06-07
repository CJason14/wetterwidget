package com.gfi.ausbildung.weatherdude.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfi.ausbildung.weatherdude.entities.Messzeitpunkt;
import com.gfi.ausbildung.weatherdude.entities.Ort;
import com.gfi.ausbildung.weatherdude.entities.Wetterdaten;
import com.gfi.ausbildung.weatherdude.entities.Wetterforecast;
import com.gfi.ausbildung.weatherdude.entities.Wetterinfo;
import com.gfi.ausbildung.weatherdude.facade.dto.FindResponseDto;
import com.gfi.ausbildung.weatherdude.utils.JsonNodeParser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WeatherApiService
{

  @Value("${weatherdude.api.key}")
  private String       apiKey;

  @Value("${weatherdude.api.lang}")
  private String       apiLang;

  @Autowired
  private ObjectMapper mapper;

  /**
   * Holt die Wetterinfo und Wetterdaten zu einem Ort und setzt diese in Messzeitpunkt
   * 
   * @param ort
   * @param messzeitpunkt
   * @return
   */
  public Messzeitpunkt getAktuelleWetterdatenFuerOrt(Ort ort, Messzeitpunkt messzeitpunkt)
  {
    log.debug("ApiCall: Getting Wetterdaten for Ort with openWeatherId: [{}]", ort.getOpenWeatherId());

    if (messzeitpunkt == null)
    {
      throw new NullPointerException("Messzeitpunkt cannot be null");
    }

    try
    {
      RestTemplate template = new RestTemplate();

      // Args
      String url = "https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&appid={apiKey}&exclude=minutely,hourly&lang={apiLang}&units=metric";
      double lat = ort.getLat();
      double lon = ort.getLon();

      log.debug("Calling api with url: [{}] (lat={}, lon={}, apiKey={}, apiLang={}", url, lat, lon, apiKey, apiLang);

      ResponseEntity<String> response = template.getForEntity(url, String.class, lat, lon, apiKey, apiLang);

      if (response.getStatusCode() == HttpStatus.OK)
      {
        log.debug("Api Call successfull!");
        String jsonResponse = response.getBody();

        log.trace("Response is: {}", jsonResponse);

        Wetterinfo nInfo = new Wetterinfo();
        Wetterdaten nDaten = new Wetterdaten();

        JsonNode root = mapper.readValue(jsonResponse, JsonNode.class);

        JsonNodeParser nodeParser = new JsonNodeParser(root);

        nInfo.setKurzText(nodeParser.get("current.weather.0.main").asText());
        nInfo.setBeschreibung(nodeParser.get("current.weather.0.description").asText());
        nInfo.setSymbol(nodeParser.get("current.weather.0.icon").asText());

        nDaten.setTemperatur_aktuell(nodeParser.get("current.temp").floatValue());
        nDaten.setTemperatur_gefuehlt(nodeParser.get("current.feels_like").floatValue());
        nDaten.setTemperatur_min(nodeParser.get("daily.0.temp.min").floatValue());
        nDaten.setTemperatur_max(nodeParser.get("daily.0.temp.max").floatValue());
        nDaten.setLuftdruck(nodeParser.get("current.pressure").asInt());
        nDaten.setLuftfeuchtigkeit(nodeParser.get("current.humidity").asInt());
        nDaten.setWind_geschwindigkeit(nodeParser.get("current.wind_speed").floatValue());
        nDaten.setWind_richtung(nodeParser.get("current.wind_deg").asInt());
        nDaten.setBewölkung(nodeParser.get("current.clouds").asInt());

        // New forecast
        ort.getForecastList().clear();

        // 7 Tage
        for (int i = 0; i <= 7; i++)
        {
          String fPath = "daily." + i;

          Wetterforecast f = new Wetterforecast();

          // Dates
          long dt = nodeParser.get(fPath + ".dt").longValue();
          LocalDateTime dtTime = LocalDateTime.ofEpochSecond(dt, 0, ZoneOffset.UTC);
          f.setMesszeitpunkt(dtTime);

          long sunrise = nodeParser.get(fPath + ".sunrise").longValue();
          LocalDateTime sunriseTime = LocalDateTime.ofEpochSecond(sunrise, 0, ZoneOffset.UTC);
          f.setSunrise(sunriseTime);

          long sunset = nodeParser.get(fPath + ".sunset").longValue();
          LocalDateTime sunsetTime = LocalDateTime.ofEpochSecond(sunset, 0, ZoneOffset.UTC);
          f.setSunset(sunsetTime);

          // Info
          f.setKurzText(nodeParser.get(fPath + ".weather.0.main").asText());
          f.setBeschreibung(nodeParser.get(fPath + ".weather.0.description").asText());
          f.setSymbol(nodeParser.get(fPath + ".weather.0.icon").asText());

          // Data - temp
          f.setTemp_day(nodeParser.get(fPath + ".temp.day").intValue());
          f.setTemp_min(nodeParser.get(fPath + ".temp.min").intValue());
          f.setTemp_max(nodeParser.get(fPath + ".temp.max").intValue());
          f.setTemp_night(nodeParser.get(fPath + ".temp.night").intValue());
          f.setTemp_eve(nodeParser.get(fPath + ".temp.eve").intValue());
          f.setTemp_morn(nodeParser.get(fPath + ".temp.morn").intValue());

          // Data - feels - like
          f.setFeels_like_day(nodeParser.get(fPath + ".feels_like.day").intValue());
          f.setFeels_like_night(nodeParser.get(fPath + ".feels_like.night").intValue());
          f.setFeels_like_eve(nodeParser.get(fPath + ".feels_like.eve").intValue());
          f.setFeels_like_morn(nodeParser.get(fPath + ".feels_like.morn").intValue());

          // Data ...
          f.setPressure(nodeParser.get(fPath + ".pressure").floatValue());
          f.setHumidity(nodeParser.get(fPath + ".humidity").floatValue());
          f.setWind_speed(nodeParser.get(fPath + ".wind_speed").floatValue());
          f.setWind_deg(nodeParser.get(fPath + ".wind_deg").floatValue());
          f.setClouds(nodeParser.get(fPath + ".clouds").floatValue());
          f.setRain(nodeParser.get(fPath + ".rain").floatValue());

          // Alert Messages
          JsonNode alertNode = nodeParser.get(fPath + ".alerts");
          List<String> alertMessages = new ArrayList<>();

          for (JsonNode alertMessageNode : alertNode)
          {
            String alertMessage = alertMessageNode.asText();
            alertMessages.add(alertMessage);
          }

          f.setAlertMessages(alertMessages);

          // Data ready
          ort.getForecastList().add(f);
        }

        if (messzeitpunkt.getWetterdaten() != null && messzeitpunkt.getWetterinfo() != null)
        {
          messzeitpunkt.getWetterdaten().transferAktuelleWetterdaten(nDaten);
          messzeitpunkt.getWetterinfo().transferAktuelleWetterinfos(nInfo);

        }
        else
        {
          messzeitpunkt.setWetterdaten(nDaten);
          messzeitpunkt.setWetterinfo(nInfo);
        }

      }
      else
      {
        log.error("Request failed with code [{}]: {}", response.getStatusCode(),
            response.getStatusCode().getReasonPhrase());
      }
    }
    catch (JsonProcessingException e)
    {
      throw new IllegalStateException("Exception parsing json entity from api call response!", e);
    }

    return messzeitpunkt;

  }

  public FindResponseDto findOpenWeatherIdFromCoordinates(float lat, float lon)
  {
    try
    {
      RestTemplate template = new RestTemplate();
      // Args
      String url = "https://api.openweathermap.org/data/2.5/find?lat={lat}&lon={lon}&cnt={cnt}&appid={apiKey}&lang={apiLang}&units=metric";
      int numberCities = 1;

      log.debug("Calling api with url: [{}] (lat={}, lon={}, cnt={}, apiKey={}, apiLang={}", url, lat, lon,
          numberCities, apiKey, apiLang);

      ResponseEntity<String> response = template.getForEntity(url, String.class, lat, lon, numberCities, apiKey,
          apiLang);

      if (response.getStatusCode() == HttpStatus.OK)
      {
        log.debug("Api Call successfull!");
        String jsonResponse = response.getBody();
        JsonNode root = mapper.readValue(jsonResponse, JsonNode.class);
        JsonNodeParser nodeParser = new JsonNodeParser(root);

        JsonNode listNode = nodeParser.get("list");

        FindResponseDto findResponseDto = new FindResponseDto();

        if (listNode.size() > 1)
        {
          findResponseDto.setErrorMessage("Error: Found multiple Cities!");
        }
        else if (listNode.size() == 0)
        {
          findResponseDto.setErrorMessage("Error: Found no city close to you!");
        }
        else
        {
          int openWeatherId = nodeParser.get("list.0.id").intValue();
          findResponseDto.setOpenWeatherId(openWeatherId);

          log.debug("Found ort with openWeatherId [{}]!", openWeatherId);
        }

        return findResponseDto;
      }
      else
      {
        throw new IllegalStateException(
            "Exception on api call. Status expected: " + HttpStatus.OK + " - GOT: " + response.getStatusCode() + "!");
      }
    }
    catch (JsonProcessingException e)
    {
      throw new IllegalStateException("Exception parsing json entity from api call response!", e);
    }
  }

}
