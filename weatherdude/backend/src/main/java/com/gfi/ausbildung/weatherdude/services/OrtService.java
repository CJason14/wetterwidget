package com.gfi.ausbildung.weatherdude.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfi.ausbildung.weatherdude.entities.Messzeitpunkt;
import com.gfi.ausbildung.weatherdude.entities.Ort;
import com.gfi.ausbildung.weatherdude.entities.Wetterforecast;
import com.gfi.ausbildung.weatherdude.entities.repositories.OrtRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrtService
{

  private static final String ORT_LIST_JSON_PATH = "src/main/resources/city.list.min.json";

  @Autowired
  private OrtRepository       ortRepo;

  @Value("${weatherdude.vorschlaege.search.expanded.threshold}")
  private int                 expandedSearchThreshold;

  /**
   * Liefert eine Liste von Orten, passend zu der Sucheingabe
   * 
   * @param suche Eingabe des Anwenders
   * @return Liste mit Orten
   */
  public List<Ort> getOrtVorschlaege(String suche)
  {
    log.debug("Getting Vorschlaege for search: [{}]", suche);
    List<Ort> vorschlaegeListe;

    if (suche.length() < expandedSearchThreshold)
    {
      log.debug("Search exceeded [expandedSearchThreshold] -> Using expanded Search");
      vorschlaegeListe = ortRepo.findByStadtStartsWithIgnoreCaseOrderByStadtAsc(suche);
    }
    else
    {
      log.debug("Using default Search");
      vorschlaegeListe = ortRepo.findByStadtContainingIgnoreCaseOrderByStadtAsc(suche);
    }

    log.debug("Checking for matching names of cities. If a city matches [" + suche
        + "] perfectly it will be the only item in the list.");
    for (Ort ort : vorschlaegeListe)
    {
      if (ort.getStadt().equals(suche))
      {
        return Collections.singletonList(ort);
      }
    }

    return vorschlaegeListe;
  }

  public Ort getOrtById(UUID id)
  {
    log.debug("Getting Ort by id: [{}]", id);
    Optional<Ort> ortResult = ortRepo.findById(id);

    if (!ortResult.isPresent())
    {
      log.warn("Could not find Ort with id: [{}]!", id);
    }

    return ortResult.orElse(null);
  }

  @Transactional
  public Ort getOrtByOpenWeatherId(String openWeatherId)
  {
    log.debug("Getting Ort by openWeatherId: [{}]", openWeatherId);
    Ort ortResult = ortRepo.findByOpenWeatherId(openWeatherId);

    if (ortResult == null)
    {
      log.warn("Could not find Ort with openWeatherId: [{}]!", openWeatherId);
    }

    return ortResult;
  }

  @Transactional
  public List<Messzeitpunkt> getOrtHistorie(String openWeatherId, int page, int size)
  {
    Ort ort = getOrtByOpenWeatherId(openWeatherId);

    if (ort == null)
    {
      log.error("Failed to find Ort with openWeatherId: [{}]!", openWeatherId);
      return Collections.emptyList();
    }

    List<Messzeitpunkt> fullHistorie = ort.getMesszeitpunktHistorie();

    log.debug("Found [{}] number of history entries for Ort with openWeatherId [{}]", fullHistorie.size(),
        openWeatherId);

    List<Messzeitpunkt> historie = new ArrayList<>();

    for (int i = 0; i < size; i++)
    {
      int index = page * size + i;
      if (index >= fullHistorie.size())
        break;
      historie.add(fullHistorie.get(index));
    }

    log.debug("Found [{}] number of history entries after paging the full list.", historie.size(), openWeatherId);

    return historie;
  }

  @Transactional
  public List<Wetterforecast> getForecast(String openWeatherId, int page, int size)
  {
    Ort ort = getOrtByOpenWeatherId(openWeatherId);

    if (ort == null)
    {
      log.error("Failed to find Ort with openWeatherId: [{}]!", openWeatherId);
      return Collections.emptyList();
    }

    List<Wetterforecast> fullForecast = ort.getForecastList();

    log.debug("Found [{}] number of forecast entries for Ort with openWeatherId [{}]", fullForecast.size(),
        openWeatherId);

    List<Wetterforecast> forecast = new ArrayList<>();

    for (int i = 0; i < size; i++)
    {
      int index = page * size + i;
      if (index >= fullForecast.size())
        break;
      forecast.add(fullForecast.get(index));
    }

    log.debug("Found [{}] number of forecast entries after paging the full list.", forecast.size(), openWeatherId);

    return forecast;
  }

  /**
   * Befüllt die Ortstabelle mit Werten, die nach deutschen Städten gefiltert werden
   */
  @PostConstruct
  public void initialisiereOrt()
  {
    log.info("Starting Ort initialization...");

    if (ortRepo.count() == 0)
    {
      log.info("No Entry found int [{}] -> Filling Repository with data from [{}]...", OrtRepository.class,
          ORT_LIST_JSON_PATH);

      ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
          false);
      try
      {
        List<Ort> orte = objectMapper.readValue(new File(ORT_LIST_JSON_PATH), new TypeReference<List<Ort>>()
        {
        });

        List<Ort> result = new ArrayList<Ort>();
        for (Ort ort : orte)
        {
          if (ort.getLand().equals("DE"))
          {
            result.add(ort);
          }
        }

        ortRepo.saveAll(result);
      }
      catch (JsonParseException | JsonMappingException e)
      {
        throw new IllegalStateException("Corrupted ort-list-file [" + ORT_LIST_JSON_PATH + "]", e);
      }
      catch (IOException e)
      {
        throw new IllegalStateException("Exception while reading ort-list-file [" + ORT_LIST_JSON_PATH + "]", e);
      }
    }
    else
    {
      log.info("Ort Repository already initialized!");
    }
  }
}
