package com.wetterwidget.backend.entities.repositiories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wetterwidget.backend.entities.Ort;

@Repository
public interface OrtRepository extends CrudRepository<Ort, UUID>
{
  List<Ort> findByStadt(String stadt);

  Ort findByOpenWeatherId(String openWeatherId);

  List<Ort> findByStadtContainingIgnoreCaseOrderByStadtAsc(String stadt);

  List<Ort> findByStadtStartsWithIgnoreCaseOrderByStadtAsc(String stadt);
}
