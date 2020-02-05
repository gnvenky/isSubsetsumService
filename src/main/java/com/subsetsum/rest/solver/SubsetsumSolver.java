package com.subsetsum.rest.solver;

import java.util.ArrayList;
import java.util.List;

public class SubsetsumSolver {

  public <T> boolean isFeasible(List<T> a, int sum)
  {
    List <Integer> al = (ArrayList<Integer>) a;
    return isSubSetSum(al.stream().mapToInt(i->i).toArray(), a.size(), sum);

  }

  private static void printTable(boolean subset[][], int x, int y)
  {
    for (int i = 0 ; i < x ; i++)
    {
      for (int j = 0 ; j < y ; j++)
        System.out.print((subset[i][j] == true ? 'T': 'F') + " ");
      System.out.println();
    }
  }

  private static boolean isSubSetSum(int set[], int n , int sum)
  {
    boolean subset[][] = new boolean[n+1][sum+1];

    for (int i = 0 ; i < (n+1) ; i++)
      subset[i][0] = true;

    for (int i = 1 ; i < (sum+1) ; i++)
      subset[1][i] = false;

    for (int i = 1; i < (n+1); i++)
    {
      for (int j = 1; j < (sum+1) ; j++)
      {
        subset[i][j] = subset[i-1][j];

        if (j >= set[i-1])
          subset[i][j] = subset[i - 1][j-set[i-1]] ||
            subset[i][j];
      }
    }
    /*
    printTable(subset, n+1, sum+1);
    // Lets print out the numbers
    int i = n;
    while (i >= 1)
    {
      if (subset[i][sum] != subset[i-1][sum])
      {
        System.out.print(set[i-1] + " ");
        sum = sum - set[i-1];
      }
      i--;
    }
    */
    return subset[n][sum];
  }
}
