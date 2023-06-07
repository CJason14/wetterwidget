package com.gfi.ausbildung.weatherdude.services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gfi.ausbildung.weatherdude.entities.Ort;
import com.gfi.ausbildung.weatherdude.entities.OrtFavorit;
import com.gfi.ausbildung.weatherdude.entities.repositories.OrtFavoritRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrtFavoritenService
{

  private static final int     MAX_FAVORITE_COUNT = 5;

  @Autowired
  private OrtFavoritRepository repository;

  @Autowired
  private OrtService           ortService;

  private int                  maxPrioritaet;

  @PostConstruct
  public void initPriorityCheck()
  {
    log.info("Retriving highest priority from OrtFavorit Entries...");
    List<OrtFavorit> ortFavoriten = getOrtFavoriten();
    if (!ortFavoriten.isEmpty())
    {
      this.maxPrioritaet = ortFavoriten.get(0).getPrioritaet();
    }

    log.debug("Priority is set to: [{}]", this.maxPrioritaet);
  }

  public List<OrtFavorit> getOrtFavoriten()
  {
    log.debug("Getting OrtFavorit Entires ordered by priority in DESC - Order");
    return repository.findAllByOrderByPrioritaetDesc();
  }

  @Transactional
  public void addFavorit(String openWeatherId)
  {
    log.debug("Adding new OrtFavorit");

    if (repository.count() >= MAX_FAVORITE_COUNT)
      throw new IllegalStateException("You cannot have more than " + MAX_FAVORITE_COUNT + " favorites!");

    Ort ort = ortService.getOrtByOpenWeatherId(openWeatherId);

    log.trace("Adding Ort with id: [{}] as OrtFavorit", ort.getId());

    if (repository.existsByFavorit(ort))
    {
      log.warn("Ort with id: [{}] is already a Favorit", ort.getId());
      return;
    }

    int nPriority = getNewPriority();

    OrtFavorit nFavorit = new OrtFavorit();
    nFavorit.setFavorit(ort);
    nFavorit.setPrioritaet(nPriority);

    log.trace("New Favorit received priority: [{}]", nPriority);

    repository.save(nFavorit);
  }

  @Transactional
  public void removeFavorit(String openWeatherId)
  {
    log.debug("Removing OrtFavorit by openWeatherId: [{}]", openWeatherId);
    Ort ort = ortService.getOrtByOpenWeatherId(openWeatherId);

    if (!repository.existsByFavorit(ort))
    {
      log.warn("Ort with openWeatherId: [{}] is not a Favorit and cannot be removed from OrtFavoritRepository!",
          openWeatherId);
      return;
    }

    repository.deleteByFavorit(ort);
  }

  private int getNewPriority()
  {
    return ++maxPrioritaet;
  }
}
