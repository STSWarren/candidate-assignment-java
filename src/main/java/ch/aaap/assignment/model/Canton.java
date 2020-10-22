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

  /**
   * @param other a canton object other than the one given to check if it has 
   * the same canton code.
   * @return a boolean indicating whether the conditions under which they would be considered equal have been met.
   */
  public boolean isEqualTo( Canton other );
}

