package com.gfi.ausbildung.weatherdude.facade.dto.pushnotification;

import java.time.LocalDateTime;

import com.gfi.ausbildung.weatherdude.facade.dto.OrtDto;

/**
 * Pushnachricht mit einem Titel und einem Body
 */
public class PushMessage
{
  private final String        title;

  private final LocalDateTime zeitpunkt;

  private final OrtDto        value;

  /**
   * Konstruktor
   * 
   * @param title Titel der Nachricht
   * @param body Body der Nachricht
   */
  public PushMessage(String title, LocalDateTime zeitpunkt, OrtDto value)
  {
    this.title = title;
    this.zeitpunkt = zeitpunkt;
    this.value = value;
  }

  public String getTitle()
  {
    return this.title;
  }

  public LocalDateTime getZeitpunkt()
  {
    return zeitpunkt;
  }

  public Object getValue()
  {
    return this.value;
  }

  @Override
  public String toString()
  {
    return "PushMessage [title=" + this.title + ", zeitpunkt=" + this.zeitpunkt + ", value=" + this.value + "]";
  }

}
