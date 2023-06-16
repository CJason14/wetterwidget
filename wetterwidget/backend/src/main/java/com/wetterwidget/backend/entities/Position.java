package com.wetterwidget.backend.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Position")
public class Position {
	@Id
	private float lat;
	
	private float lon;
}
