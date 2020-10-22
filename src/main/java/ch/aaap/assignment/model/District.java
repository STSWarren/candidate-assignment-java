package ch.aaap.assignment.model;

import java.util.Set;

public interface District {

  public String getNumber();

  public String getName();

  public Set<String> getPoliticalCommunityNumbersInDistrict();

  /**
   * @param newPoliticalCommunityNumber to add to the list of political community numbers 
   * within with this district.
   */
  public void addNewPoliticalCommunityNumber( String newPoliticalCommunityNumber );

  /**
   * @param other a district object other than the one given to check if it has 
   * the same number.
   * @return a boolean indicating whether the conditions under which they would be considered equal have been met.
   */
  public boolean isEqualTo( District other );
}