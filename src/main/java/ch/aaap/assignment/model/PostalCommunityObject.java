package ch.aaap.assignment.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ch.aaap.assignment.raw.CSVPostalCommunity;

public class PostalCommunityObject implements PostalCommunity {

  private String zipCode;
  private String zipCodeAddition;
  private String name;
  private Set<String> politicalCommunityNumbers;
  
  public PostalCommunityObject( CSVPostalCommunity csvPostalCommunity ){
    zipCode = csvPostalCommunity.getZipCode();
    zipCodeAddition = csvPostalCommunity.getZipCodeAddition();
    name = csvPostalCommunity.getName();
    politicalCommunityNumbers = new HashSet<>();
  }

  public String getZipCode(){
    return zipCode;
  }
  
  public String getZipCodeAddition(){
    return zipCodeAddition;
  }
  
  public String getName(){
    return name;
  }

  public Set<String> getPoliticalCommunities(){
    return politicalCommunityNumbers;
  }
    
  public void addNewPoliticalCommunity( String politicalCommunityNumber ){
    politicalCommunityNumbers.add(politicalCommunityNumber);
  }


  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof PostalCommunityObject)) {
      return false;
    }
    PostalCommunityObject postalCommunityObject = (PostalCommunityObject) o;
    return Objects.equals(zipCode, postalCommunityObject.zipCode) && Objects.equals(zipCodeAddition, postalCommunityObject.zipCodeAddition) && Objects.equals(name, postalCommunityObject.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(zipCode, zipCodeAddition, name);
  }

}
