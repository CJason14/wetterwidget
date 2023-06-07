package com.wetterwidget.backend.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Favoriten {
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(columnDefinition = "BINARY(16)")
	  @JsonIgnore
	  private UUID   user_id;
	  
	  private String openweatherid_stadt;
	  
	  private int widgetnumber;
}
