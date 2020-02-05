package com.subsetsum.rest.solver;

import java.util.List;

public interface iSolver <E> {

  public <T> boolean isFeasible(List <T> a, int sum) ;

}
