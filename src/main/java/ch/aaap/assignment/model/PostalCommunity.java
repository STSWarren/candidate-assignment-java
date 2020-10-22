package ch.aaap.assignment.model;

import java.util.Set;

public interface PostalCommunity {

  public String getZipCode();

  public String getZipCodeAddition();

  public String getName();

  public Set<String> getPoliticalCommunities();

  /**
   * @param politicalCommunityNumber to add to the list of political community numbers 
   * within with this postal community.
   */
  public void addNewPoliticalCommunity(String politicalCommunityNumber);

  /**
   * @param other a postal community object other than the one given to check if it has 
   * the same name, zip code and zip code addition.
   * @return a boolean indicating whether the conditions under which they would be considered equal have been met.
   */
  public boolean isEqualTo(PostalCommunity other);
}