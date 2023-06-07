package com.gfi.ausbildung.weatherdude.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchTree<V>
{

  private class Node
  {
    final List<V>              values = new ArrayList<>();
    final Map<Character, Node> tree   = new HashMap<>();
  }

  private final Node    root = new Node();
  private final boolean allowCapitalLetters;

  public SearchTree(boolean allowCapitalLetters)
  {
    this.allowCapitalLetters = allowCapitalLetters;
  }

  public void put(String path, V value)
  {
    if (!allowCapitalLetters)
    {
      path = path.toLowerCase();
    }

    put(root, path, 0, value);
  }

  private void put(Node node, String path, int i, V value)
  {
    // if i at end
    // if i not at end & node has not next char
    // if i not at end & node has next char

    char next = path.charAt(i);
    Node n = node.tree.get(next);
    if (n == null)
    {
      n = new Node();
      node.tree.put(next, n);
    }

    if (i == path.length() - 1)
    {
      n.values.add(value);
    }
    else
    {
      put(n, path, i + 1, value);
    }
  }

  public List<V> get(String path)
  {
    if (!allowCapitalLetters)
    {
      path = path.toLowerCase();
    }

    return get(root, path, 0);
  }

  private List<V> get(Node node, String path, int i)
  {
    char next = path.charAt(i);
    Node n = node.tree.get(next);

    if (n == null)
    {
      return new ArrayList<>();
    }

    if (i == path.length() - 1)
    {
      return n.values;
    }
    else
    {
      return get(n, path, i + 1);
    }
  }

  public List<V> getAll(String path)
  {
    if (!allowCapitalLetters)
    {
      path = path.toLowerCase();
    }

    return getAll(root, path, 0);
  }

  private List<V> getAll(Node node, String path, int i)
  {
    if (i < path.length())
    {
      char next = path.charAt(i);
      Node n = node.tree.get(next);

      if (n == null)
      {
        return new ArrayList<>();
      }

      return getAll(n, path, i + 1);
    }
    else
    {
      List<V> values = new ArrayList<>();
      findAll(node, values);
      return values;
    }
  }

  private void findAll(Node n, List<V> values)
  {
    values.addAll(n.values);

    for (char c : n.tree.keySet())
    {
      findAll(n.tree.get(c), values);
    }
  }

}
