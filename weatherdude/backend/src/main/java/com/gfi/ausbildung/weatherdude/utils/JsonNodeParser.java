package com.gfi.ausbildung.weatherdude.utils;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonNodeParser
{

  private final JsonNode              root;
  private final Map<String, JsonNode> paths = new HashMap<>();

  public JsonNodeParser(JsonNode root)
  {
    this.root = root;
  }

  public JsonNode get(String path)
  {
    if (paths.containsKey(path))
    {
      return paths.get(path);
    }

    String[] split = path.split("\\.");

    JsonNode node;

    if (split.length == 1)
    {
      node = root.get(split[0]);
    }
    else
    {
      int lastDot = path.lastIndexOf('.');
      String parentPath = path.substring(0, lastDot);
      JsonNode parent = get(parentPath);

      if (parent instanceof DefaultNode)
      {
        node = parent;
      }
      else
      {
        String lastWord = split[split.length - 1];

        try
        {
          int index = Integer.parseInt(lastWord);
          node = parent.get(index);
        }
        catch (NumberFormatException e)
        {
          node = parent.get(lastWord);
        }
      }
    }

    // Node not found - get default values
    if (node == null)
    {
      node = new DefaultNode();
    }

    paths.put(path, node);
    return node;
  }

}
