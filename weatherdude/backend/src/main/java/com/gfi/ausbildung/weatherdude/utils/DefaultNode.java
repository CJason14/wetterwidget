package com.gfi.ausbildung.weatherdude.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ValueNode;

class DefaultNode extends ValueNode
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Override
  public String textValue()
  {
    return "";
  }

  @Override
  public String asText(String defaultValue)
  {
    return "";
  }

  @Override
  public float floatValue()
  {
    return 0;
  }

  @Override
  public long longValue()
  {
    return 0;
  }

  @Override
  public int intValue()
  {
    return 0;
  }

  @Override
  public double doubleValue()
  {
    return 0;
  }

  // IGNORE REST
  @Override
  public JsonToken asToken()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int hashCode()
  {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void serialize(JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException
  {
    // TODO Auto-generated method stub

  }

  @Override
  public JsonNodeType getNodeType()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String asText()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean equals(Object o)
  {
    // TODO Auto-generated method stub
    return false;
  }

}
