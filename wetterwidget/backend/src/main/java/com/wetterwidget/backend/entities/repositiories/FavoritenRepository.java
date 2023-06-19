package com.wetterwidget.backend.entities.repositiories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wetterwidget.backend.entities.Favoriten;


@Repository
public interface FavoritenRepository extends JpaRepository<Favoriten, UUID>
{
	List<Favoriten> findAllBycookieid(String cookieid);
	
	int deleteAllByCookieidAndCountAndOpenweatheridstadt(String cookieid, String count, String openweatheridstadt);
}