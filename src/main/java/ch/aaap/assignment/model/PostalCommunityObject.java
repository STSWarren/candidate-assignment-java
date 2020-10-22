package ch.aaap.assignment.model;

import java.util.Set;

public class PostalCommunityObject implements PostalCommunity {

    private String zipCode;
    private String zipCodeAddition;
    private String name;
    private Set<String> politicalCommunityNumbers;
  
    public PostalCommunityObject(String givenZipCode, String givenZipCodeAddition, 
      String givenName, Set<String> givenPoliticalCommunityNumbers){
      zipCode = givenZipCode;
      zipCodeAddition = givenZipCodeAddition;
      name = givenName;
      politicalCommunityNumbers = givenPoliticalCommunityNumbers;
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

    public boolean isEqualTo(PostalCommunity other){
      if( !other.getName().equals(this.getName() ) ){
        return false;
      }
      
      if(!other.getZipCode().equals(this.getZipCode() ) ){
        return false;
      }

      if(!other.getZipCodeAddition().equals(this.getZipCodeAddition() ) ){
        return false;
      }

      return true;
    }
}
