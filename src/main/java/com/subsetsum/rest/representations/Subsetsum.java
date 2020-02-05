package com.subsetsum.rest.representations;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class Subsetsum {
  @NotNull
  private Integer id;
  @NotBlank @Length(min=2, max=255)
  private String array;
  @NotNull
  private Integer sum;

  public Subsetsum(){
  }

  public Subsetsum(Integer id, String array, Integer sum) {
    this.id = id;
    this.array = array;
    this.sum = sum;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getArray() {
    return array;
  }

  public void setArray(String array) {
    this.array = array;
  }

  public Integer getSum() {
    return sum;
  }

  public void setSum(Integer sum) {
    this.sum = sum;
  }

  @Override
  public String toString() {
    return "Subsetsum [id=" + id + ", array=" + array + ", sum="
      + sum  + "]";
  }
}
