package com.gfi.ausbildung.weatherdude.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gfi.ausbildung.weatherdude.entities.Messzeitpunkt;
import com.gfi.ausbildung.weatherdude.entities.Ort;
import com.gfi.ausbildung.weatherdude.entities.repositories.OrtRepository;
import com.gfi.ausbildung.weatherdude.facade.dto.OrtDto;
import com.gfi.ausbildung.weatherdude.facade.dto.pushnotification.PushMessage;
import com.gfi.ausbildung.weatherdude.utils.TimeUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@ConditionalOnProperty(value = "weatherdude.wetterdaten.scheduler.enabled", havingValue = "true")
@Slf4j
public class WetterdatenAktuallisiererService
{

  @Value("${weatherdude.wetterdaten.scheduler.ortListe}")
  private List<String>       ortListe;

  @Autowired
  private OrtRepository      ortRepository;

  @Autowired
  private WetterdatenService wetterdatenService;

  @Autowired
  private PushMessageService messageService;

  @PostConstruct
  public void notifySchedulerEnabled()
  {
    log.info("Wetterdaten Scheduler is enabled!");
  }

  @Bean("weatherdude_wetterdaten_scheduler_time_millis")
  public int getWetterdatenSchedulerTimeInMilliseconds(
      @Value("${weatherdude.wetterdaten.scheduler.time.minutes}") double minutes)
  {
    return (int) (minutes * 60 * 1000);
  }

  // Using Bean so the value in application.properties can be in minutes instead of millis
  @Scheduled(fixedRateString = "#{weatherdude_wetterdaten_scheduler_time_millis}")
  @Transactional
  public void aktuallisiereWetterdatenListe()
  {
    log.info("Starting [Wetterdaten Update Scheduler] ...");
    log.debug("Update Id List: {}", ortListe);

    for (String ortId : ortListe)
    {
      Ort ort = ortRepository.findByOpenWeatherId(ortId);
      log.trace("Updating Ort: {}[{}]", ort.getStadt(), ort.getOpenWeatherId());

      ort = wetterdatenService.aktualisiereWetterDatenFuerOrt(ort);

      // Workaround to check if data was updated just now
      LocalDateTime now = LocalDateTime.now();
      Messzeitpunkt messzeitpunkt = ort.getAktMesszeitpunkt();

      if (TimeUtils.calculateSecondDifference(messzeitpunkt.getMesszeitpunkt(), now) < 10)
      {
        try
        {
          messageService.sendPushMessageToAllSubscribers(new PushMessage("Neue Daten zu: " + ort.getStadt(),
              messzeitpunkt.getMesszeitpunkt(), OrtDto.createDtoByOrt(ort)));
        }
        catch (JsonProcessingException e)
        {
          log.error("Error parsing PushMessage!", e);
        }
      }
    }

  }

}
