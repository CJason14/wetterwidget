package com.gfi.ausbildung.weatherdude.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gfi.ausbildung.weatherdude.facade.dto.pushnotification.Subscription;
import com.gfi.ausbildung.weatherdude.facade.dto.pushnotification.SubscriptionEndpoint;
import com.gfi.ausbildung.weatherdude.services.PushMessageService;

import lombok.extern.slf4j.Slf4j;

/**
 * Restcontroller rund um PushNotification. folgende Methoden stehen bereit: /publicSigningKey liefert den öffentlichen
 * Schlüssel des Servers /subscribe Anmelden /unsubscribe Abmelden /isSubscribed liefert die Info, ob man für eine
 * Subscription registriert ist /lastNumbersAPIFact Ergebnis der Schnittstelle http://numbersapi.com/random/date
 */
@RestController
@RequestMapping("/pushMessage")
@CrossOrigin
@Slf4j
public class PushMessageController
{

  @Autowired
  private PushMessageService pushMessageService;

  @GetMapping(path = "/publicSigningKey", produces = "application/octet-stream")
  public byte[] publicSigningKey()
  {
    log.info("Controller[GET]: Getting public signing key.");
    return pushMessageService.getPublicSigningKey();
  }

  @GetMapping(path = "/publicSigningKeyBase64")
  public String publicSigningKeyBase64()
  {
    log.info("Controller[GET]: Getting public signing key base 64.");
    return pushMessageService.getPublicSigningKeyBase64();
  }

  @PostMapping("/subscribe")
  @ResponseStatus(HttpStatus.CREATED)
  public void subscribe(@RequestBody Subscription subscription)
  {
    log.info("Controller[POST]: Subscribing");
    pushMessageService.subscribe(subscription);
  }

  @PostMapping("/unsubscribe")
  public void unsubscribe(@RequestBody SubscriptionEndpoint subscription)
  {
    log.info("Controller[POST]: Unsubscribing");
    pushMessageService.unsubscribe(subscription);
  }

  @PostMapping("/isSubscribed")
  public boolean isSubscribed(@RequestBody SubscriptionEndpoint subscription)
  {
    log.info("Controller[POST]: is subscribed");
    return pushMessageService.isSubscribed(subscription);
  }

}
