package com.gfi.ausbildung.weatherdude.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Messzeitpunkt
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "BINARY(16)")
  private UUID          id;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(foreignKey = @ForeignKey(name = "FK_MESSZEITPUNKT_wetterinfo_id_WETTERINFO_id"))
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  private Wetterinfo    wetterinfo;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(foreignKey = @ForeignKey(name = "FK_MESSZEITPUNKT_wetterdaten_id_WETTERDATEN_id"))
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  private Wetterdaten   wetterdaten;

  @Column
  private LocalDateTime messzeitpunkt;

  public Messzeitpunkt()
  {
  }

  public Messzeitpunkt(UUID id, Wetterinfo wetterinfo, LocalDateTime messzeitpunkt)
  {
    this.id = id;
    this.wetterinfo = wetterinfo;
    this.messzeitpunkt = messzeitpunkt;
  }

  public UUID getId()
  {
    return id;
  }

  public void setId(UUID id)
  {
    this.id = id;
  }

  public Wetterinfo getWetterinfo()
  {
    return wetterinfo;
  }

  public void setWetterinfo(Wetterinfo wetterinfo)
  {
    this.wetterinfo = wetterinfo;
  }

  public Wetterdaten getWetterdaten()
  {
    return wetterdaten;
  }

  public void setWetterdaten(Wetterdaten wetterdaten)
  {
    this.wetterdaten = wetterdaten;
  }

  public LocalDateTime getMesszeitpunkt()
  {
    return messzeitpunkt;
  }

  public void setMesszeitpunkt(LocalDateTime messzeitpunkt)
  {
    this.messzeitpunkt = messzeitpunkt;
  }

  @Override
  public String toString()
  {
    return "Messzeitpunkt [id=" + id + ", wetterinfo=" + wetterinfo + ", messzeitpunkt=" + messzeitpunkt + "]";
  }
}
