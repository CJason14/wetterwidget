package com.gfi.ausbildung.weatherdude.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gfi.ausbildung.weatherdude.entities.Messzeitpunkt;
import com.gfi.ausbildung.weatherdude.entities.Ort;
import com.gfi.ausbildung.weatherdude.entities.OrtFavorit;
import com.gfi.ausbildung.weatherdude.entities.Wetterforecast;
import com.gfi.ausbildung.weatherdude.facade.dto.FindResponseDto;
import com.gfi.ausbildung.weatherdude.facade.dto.OrtDto;
import com.gfi.ausbildung.weatherdude.facade.dto.OrtFavoritDto;
import com.gfi.ausbildung.weatherdude.facade.dto.OrtVorschlagDto;
import com.gfi.ausbildung.weatherdude.facade.dto.SuchergebnisDto;
import com.gfi.ausbildung.weatherdude.services.OrtFavoritenService;
import com.gfi.ausbildung.weatherdude.services.OrtService;
import com.gfi.ausbildung.weatherdude.services.WeatherApiService;
import com.gfi.ausbildung.weatherdude.services.WetterdatenService;

import lombok.extern.slf4j.Slf4j;

/**
 * RestController für WetterInfo Daten
 * 
 * @author Venohr
 */
@RestController
@RequestMapping("/wetterinfo")
@CrossOrigin
@Slf4j
public class WetterInfoController
{
  @Autowired
  private OrtService          ortservice;

  @Autowired
  private WetterdatenService  wetterdatenService;

  @Autowired
  private OrtFavoritenService ortFavoritService;

  @Autowired
  private WeatherApiService   apiService;

  /**
   * Liefert eine Vorschlagsliste von Orten, passend zur Eingabe des Nutzers
   * 
   * @param suche Sucheingabe
   * @return Liste mit Vorschlaegen
   */
  @GetMapping("/vorschlaege_{suche}")
  public List<OrtVorschlagDto> getOrtVorschlaege(@PathVariable String suche)
  {
    log.info("Controller[GET]: Getting Ort Vorschlaege");
    log.debug("Search input is: [{}]", suche);

    List<Ort> suchErgebnisse = ortservice.getOrtVorschlaege(suche);
    List<OrtVorschlagDto> vorschlaege = new ArrayList<>();

    if (suchErgebnisse == null)
    {
      log.warn("Vorschlaege list is null!");
    }
    else
    {
      log.debug("Found [{}] Vorschlaege.", suchErgebnisse.size());
      log.debug("Creating OrtVorschlaDtos...");

      for (Ort ort : suchErgebnisse)
      {
        log.trace("Creating OrtVorschlagDto for ort with id: [{}]", ort.getId());
        vorschlaege.add(OrtVorschlagDto.createDtoByOrt(ort));
      }
    }

    return vorschlaege;
  }

  /**
   * Liefert die Wetterdaten zu einem Ort
   * 
   * @param ortsName
   * @return SuchergebnisDto
   */
  @GetMapping("/ort")
  public SuchergebnisDto getOrtSuchergebniss(@RequestParam(required = false) String ortsName,
      @RequestParam(required = false) String openWeatherId)
  {
    SuchergebnisDto ret = new SuchergebnisDto();
    if (ortsName != null)
    {
      log.info("Controller[GET]: Getting Ort Suchergebniss");
      log.debug("Search input is: [{}]", ortsName);

      // Ort über den Ortsnamen suchen
      List<Ort> ortVorschlaege = ortservice.getOrtVorschlaege(ortsName);

      if (ortVorschlaege == null)
      {
        log.warn("Vorschlaege list is null!");
      }
      else
      {
        log.debug("Found [{}] Vorschlaege", ortVorschlaege.size());

        if (ortVorschlaege.size() == 1)
        {
          log.debug("Found single Vorschlag - Entry update Ort and returning OrtDto in SuchergebnisDto.");

          Ort uncheckedOrt = ortVorschlaege.get(0);
          log.debug("Update WetterDaten for Ort with id: [{}]", uncheckedOrt.getId());

          Ort aktOrt = wetterdatenService.aktualisiereWetterDatenFuerOrt(uncheckedOrt);

          log.trace("Creating OrtVorschlagDto for ort with id: [{}]", uncheckedOrt.getId());
          ret.setAktWetterinfo(OrtDto.createDtoByOrt(aktOrt));
        }
        else
        {
          log.debug("Found multiple Vorschlag - Entries returning OrtDtos in SuchergebnisDto.");
          List<OrtVorschlagDto> vorschlaege = new ArrayList<>();

          for (Ort ort : ortVorschlaege)
          {
            log.trace("Creating OrtVorschlagDto for ort with id: [{}]", ort.getId());
            vorschlaege.add(OrtVorschlagDto.createDtoByOrt(ort));
          }

          ret.setOrtVorschlaege(vorschlaege);
        }
      }

    }
    else if (openWeatherId != null)
    {
      log.info("Controller[GET]: Getting Ort Suchergebniss");
      log.debug("Search input is: [{}]", openWeatherId);
      Ort uncheckedOrt = ortservice.getOrtByOpenWeatherId(openWeatherId);
      log.debug("Found ort [{}] by openWeatherId [{}].", uncheckedOrt.getStadt(), openWeatherId);

      Ort aktOrt = wetterdatenService.aktualisiereWetterDatenFuerOrt(uncheckedOrt);
      ret.setAktWetterinfo(OrtDto.createDtoByOrt(aktOrt));
    }
    return ret;
  }

  @GetMapping("/historie")
  public List<Messzeitpunkt> getOrtHistorie(@RequestParam String openWeatherId, @RequestParam int page,
      @RequestParam int size)
  {
    log.info("Controller[GET]: Getting Ort Historie");
    log.debug("Search input is: [openWeatherId = {}, page = {}, size = {}]", openWeatherId, page, size);
    return ortservice.getOrtHistorie(openWeatherId, page, size);
  }

  @GetMapping("/forecast")
  public List<Wetterforecast> getForecast(@RequestParam String openWeatherId, @RequestParam int page,
      @RequestParam int size)
  {
    log.info("Controller[GET]: Getting Ort Forecast");
    log.debug("Search input is: [openWeatherId = {}, page = {}, size = {}]", openWeatherId, page, size);
    return ortservice.getForecast(openWeatherId, page, size);
  }

  @GetMapping("/find")
  public FindResponseDto findOpenWeatherIdFromCoordinates(@RequestParam float lat, @RequestParam float lon)
  {
    log.info("Controller[GET]: Getting openWeatherId from coordinates");
    log.debug("Search input is: [lat = {}, lon = {}]", lat, lon);
    return apiService.findOpenWeatherIdFromCoordinates(lat, lon);
  }

  @GetMapping("/fav")
  public List<OrtFavoritDto> getOrtFavoriten()
  {
    log.info("Controller[GET]: Getting OrtFavorit List");
    List<OrtFavorit> favoriten = ortFavoritService.getOrtFavoriten();

    List<OrtFavoritDto> favoritDtos = new ArrayList<>();
    for (OrtFavorit favorit : favoriten)
    {
      favoritDtos.add(OrtFavoritDto.createDtoByFavorit(favorit));
    }

    return favoritDtos;
  }

  @PutMapping("/fav")
  public void addFavorit(@RequestBody String openWeatherId)
  {
    log.info("Controller[PUT]: Adding OrtFavorit to OrtFavorit List");
    log.trace("OpenWeatherId: [{}]", openWeatherId);
    ortFavoritService.addFavorit(openWeatherId);
  }

  @DeleteMapping("/fav")
  public void removeFavorit(@RequestBody String openWeatherId)
  {
    log.info("Controller[DELETE]: Removing OrtFavorit from OrtFavorit List");
    log.trace("OpenWeatherId: [{}]", openWeatherId);
    ortFavoritService.removeFavorit(openWeatherId);
  }
  
  @GetMapping("/hello")
  public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    return String.format("Hello %s!", name);
  }

}
