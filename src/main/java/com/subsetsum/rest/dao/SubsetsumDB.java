package com.subsetsum.rest.dao;

import com.subsetsum.rest.representations.Subsetsum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubsetsumDB {

  public static HashMap<Integer, Subsetsum> subsetsums = new HashMap<>();
  static{
    subsetsums.put(1, new Subsetsum(1, "{3, 4, 6}",9));
    subsetsums.put(2, new Subsetsum(2, "{3, 4, 6}",5));
    subsetsums.put(3, new Subsetsum(3, "{3, 4, 6}",13));
  }

  public static List<Subsetsum> getSubsetsums(){
    return new ArrayList<Subsetsum>(subsetsums.values());
  }

  public static Subsetsum getSubsetsum(Integer id){
    return subsetsums.get(id);
  }

  public static void updateSubsetsum(Integer id, Subsetsum Subsetsum){
    subsetsums.put(id, Subsetsum);
  }

  public static void removeSubsetsum(Integer id){
    subsetsums.remove(id);
  }
}
