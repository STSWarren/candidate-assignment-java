package ch.aaap.assignment.model;

import java.time.LocalDate;
import java.util.List;

public interface PoliticalCommunity {

  public String getNumber();

  public String getName();

  public String getShortName();

  public LocalDate getLastUpdate();

  public List<ZipAndAdditionsContainer> getPostalCommunities();

  /**
   * @param zip the zip of a postal community object associated with this political 
   *  community to act as a key in the postal communities map.
   * @param zipCodeAddition the zip code addition of the postal community 
   * associated with this political community to act as the value in the postal communities map.
   */
  public void addNewPostalCommunity( String zip, String zipCodeAddition  );
}
