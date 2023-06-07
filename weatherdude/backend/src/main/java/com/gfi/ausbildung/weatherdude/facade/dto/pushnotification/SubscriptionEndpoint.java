package com.gfi.ausbildung.weatherdude.facade.dto.pushnotification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Endpunkt URL für die Subscription
 * 
 * endpunkt = ...
 */
public class SubscriptionEndpoint
{
  private final String endpoint;

  /**
   * Konstruktor
   * 
   * @param endpoint Endpunkt URL
   */
  @JsonCreator
  public SubscriptionEndpoint(@JsonProperty("endpoint") String endpoint)
  {
    this.endpoint = endpoint;
  }

  /**
   * Liefert die Endpunkt URL
   * 
   * @return Endpunkt URL
   */
  public String getEndpoint()
  {
    return this.endpoint;
  }

  @Override
  public String toString()
  {
    return "SubscriptionEndpoint [endpoint=" + this.endpoint + "]";
  }

}
