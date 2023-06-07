package com.gfi.ausbildung.weatherdude.entities.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gfi.ausbildung.weatherdude.entities.Messzeitpunkt;

@Repository
public interface MesszeitpunktRepository extends CrudRepository<Messzeitpunkt, UUID>
{

}
