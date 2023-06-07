package com.gfi.ausbildung.weatherdude.entities.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gfi.ausbildung.weatherdude.entities.Ort;
import com.gfi.ausbildung.weatherdude.entities.OrtFavorit;

@Repository
public interface OrtFavoritRepository extends CrudRepository<OrtFavorit, UUID>
{

  boolean existsByFavorit(Ort favorit);

  void deleteByFavorit(Ort favorit);

  List<OrtFavorit> findAllByOrderByPrioritaetDesc();

  OrtFavorit findByFavorit(Ort ort);

}
