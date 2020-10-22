package ch.aaap.assignment.model;

import java.util.Set;

public interface Canton {

  public String getCode();

  public String getName();

  public Set<String> getDistrictCodesInCanton();

  /**
   * @param newDistrict to add to the list of district numbers 
   * within with this canton.
   */
  public void addNewDistrict( String newDistrict );
}

