package com.gfi.ausbildung.weatherdude.entities;

import java.util.UUID;

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
public class OrtFavorit
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(foreignKey = @ForeignKey(name = "FK_ORT_FAVORIT_favorit_id_ORT_id"))
  @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
  private Ort  favorit;

  private int  prioritaet;

  public OrtFavorit()
  {
  }

  public OrtFavorit(UUID id, Ort favorit, int prioritaet)
  {
    this.id = id;
    this.favorit = favorit;
    this.prioritaet = prioritaet;
  }

  public UUID getId()
  {
    return id;
  }

  public void setId(UUID id)
  {
    this.id = id;
  }

  public Ort getFavorit()
  {
    return favorit;
  }

  public void setFavorit(Ort favorit)
  {
    this.favorit = favorit;
  }

  public int getPrioritaet()
  {
    return prioritaet;
  }

  public void setPrioritaet(int prioritaet)
  {
    this.prioritaet = prioritaet;
  }

}
