package ch.aaap.assignment.model;

import java.util.Set;

public class DistrictObject implements District{
    private String number;
    private String name;
    private Set<String> politicalCommunityNumbersInDistrict;
  
    public DistrictObject(String givenNumber, String givenName, Set<String> givenPoliticalCommunities){
      number = givenNumber;
      name = givenName;
      politicalCommunityNumbersInDistrict = givenPoliticalCommunities;
    }
  
    public String getNumber(){
      return number;
    }
  
    public String getName(){
      return name;
    }
  
    public Set<String> getPoliticalCommunityNumbersInDistrict(){
      return politicalCommunityNumbersInDistrict;
    }
  
    public void addNewPoliticalCommunityNumber( String newPoliticalCommunityNumber ){
      politicalCommunityNumbersInDistrict.add( newPoliticalCommunityNumber );
    }
  
    public boolean isEqualTo( District other ){
      return this.getNumber().equals( other.getNumber() );
    }
  
  }