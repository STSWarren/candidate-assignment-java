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
}