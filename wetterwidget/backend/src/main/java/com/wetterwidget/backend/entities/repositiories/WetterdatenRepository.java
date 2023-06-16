package com.wetterwidget.backend.entities.repositiories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wetterwidget.backend.entities.Wetterdaten;

@Repository
public interface WetterdatenRepository extends JpaRepository<Wetterdaten, UUID>
{
	List<Wetterdaten> findAllByopenweatherid(String openweatherid);
}