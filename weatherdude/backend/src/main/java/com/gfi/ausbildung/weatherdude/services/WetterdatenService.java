package com.gfi.ausbildung.weatherdude.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gfi.ausbildung.weatherdude.entities.Messzeitpunkt;
import com.gfi.ausbildung.weatherdude.entities.Ort;
import com.gfi.ausbildung.weatherdude.entities.repositories.OrtRepository;
import com.gfi.ausbildung.weatherdude.utils.TimeUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WetterdatenService
{

  private final int         maxZeitDiff;

  @Autowired
  private WeatherApiService apiService;

  @Autowired
  private OrtRepository     ortRepo;

  /**
   * Konstruktor
   * 
   * @param maxZeitDiff
   */
  public WetterdatenService(@Value("${weatherdude.messzeitpunkt.maxdiff.minutes}") int maxZeitDiff)
  {
    this.maxZeitDiff = maxZeitDiff;
  }

  /**
   * Liefert zum Ort die aktuellen Wetterdaten
   * 
   * @param ort zugrundeliegender Ort
   * @return Ort mit aktualisierten Wetterdaten
   */
  public Ort aktualisiereWetterDatenFuerOrt(Ort ort)
  {
    log.debug("Updating WetterDaten for Ort with id: [{}]", ort.getId());

    LocalDateTime now = LocalDateTime.now();
    Messzeitpunkt messzeitpunkt = ort.getAktMesszeitpunkt();

    if (messzeitpunkt != null)
    {
      log.trace("Last Messzeitpunkt: [{}], Now: [{}]", messzeitpunkt.getMesszeitpunkt(), now);

      int diff = TimeUtils.calculateMinuteDifference(messzeitpunkt.getMesszeitpunkt(), now);

      log.trace("Difference between last Messzeitpunkt: [{}]", diff);

      if (diff >= maxZeitDiff)
      {
        log.debug("Letzter Messzeitpunkt ist abgelaufen.");
        ort = aktualisiereWetterDatenFuerOrtOhneZeitcheck(ort);
      }
      else
      {
        log.debug("[{}] < [{}]. Last Messzeitpunkt is still valid!", diff, maxZeitDiff);
      }
    }
    else
    {
      log.debug("Kein aktueller Messzeitpunkt gespeichert.");
      ort = aktualisiereWetterDatenFuerOrtOhneZeitcheck(ort);
    }

    return ort;
  }

  private Ort aktualisiereWetterDatenFuerOrtOhneZeitcheck(Ort ort)
  {
    log.info("Update Wetterdaten für [{}] mit openWeatherId [{}]", ort.getStadt(), ort.getOpenWeatherId());
    log.debug("Erstelle neuen Messzeitpunkt und aktuallisiere Daten für Ort mit OpenWeatherId: [{}] ...",
        ort.getOpenWeatherId());

    Messzeitpunkt altMesszeitpunkt = ort.getAktMesszeitpunkt();

    LocalDateTime now = LocalDateTime.now();
    Messzeitpunkt messzeitpunkt = new Messzeitpunkt();
    messzeitpunkt.setMesszeitpunkt(now);
    messzeitpunkt = apiService.getAktuelleWetterdatenFuerOrt(ort, messzeitpunkt);

    ort.setAktMesszeitpunkt(messzeitpunkt);
    if (altMesszeitpunkt != null)
    {
      log.debug("Adding new Messzeitpunkt to MesszeitpunktHistorie");
      List<Messzeitpunkt> historie = ort.getMesszeitpunktHistorie();
      historie.add(altMesszeitpunkt);
    }

    ort = ortRepo.save(ort);

    return ort;
  }

}
