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
}