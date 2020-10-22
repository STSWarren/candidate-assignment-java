package ch.aaap.assignment.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public interface PoliticalCommunity {

  public String getNumber();

  public String getName();

  public String getShortName();

  public LocalDate getLastUpdate();

  public Map<String, Set<String>> getPostalCommunities();

  /**
   * @param zip the zip of a postal community object associated with this political community to act as a key in the 
   * postal communities map.
   * @param zipCodeAddition the zip code addition of the postal community associated with this political community
   * to act as the value in the postal communities map.
   */
  public void addNewPostalCommunity( String zip, String zipCodeAddition  );

  /**
   * @param other a political community object other than the one given to check if it has 
   * the same number.
   * @return a boolean indicating whether the conditions under which they would be considered equal have been met.
   */
  public boolean isEqualTo( PoliticalCommunity other );
}
