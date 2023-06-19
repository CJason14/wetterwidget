package com.wetterwidget.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.wetterwidget.backend.entities.Favoriten;
import com.wetterwidget.backend.entities.repositiories.FavoritenRepository;

@EnableScheduling
public class ScheduleService {

    @Autowired
    private FavoritenRepository favrepo;

    @Autowired
    private WetterDatenService wetterservice;


    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void scheduleFixedRateTask() {
        System.out.println("Scheduler: Update!");
        List < Favoriten > favlist = favrepo.findAll();
        for (int i = 0; i < favlist.size(); i++) {
            wetterservice.getWeatherDataByCity(favlist.get(i).getStadt());
        }
    }

}