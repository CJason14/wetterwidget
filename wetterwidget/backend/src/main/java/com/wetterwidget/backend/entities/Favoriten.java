package com.wetterwidget.backend.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Favoriten")
public class Favoriten {
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(columnDefinition = "BINARY(16)")
	  @JsonIgnore
	  private UUID   user_id;
	  
	  private String cookieid;
	  
	  private String openweatheridstadt;
	  
	  private String widgetnumber;
	  
	  private String stadt;
	  
	  private String count;
}
