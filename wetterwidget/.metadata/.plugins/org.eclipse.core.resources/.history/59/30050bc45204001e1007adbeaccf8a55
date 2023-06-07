package com.gfi.ausbildung.weatherdude.entities.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gfi.ausbildung.weatherdude.entities.Ort;

@Repository
public interface OrtRepository extends CrudRepository<Ort, UUID>
{
  List<Ort> findByStadt(String stadt);

  Ort findByOpenWeatherId(String openWeatherId);

  // Searches for names SIMILAR (Containing) to 'stadt' and ignoring all case (IgnoreCase)
  List<Ort> findByStadtContainingIgnoreCaseOrderByStadtAsc(String stadt);

  List<Ort> findByStadtStartsWithIgnoreCaseOrderByStadtAsc(String stadt);
}
