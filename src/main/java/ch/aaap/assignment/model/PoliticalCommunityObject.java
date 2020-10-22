package ch.aaap.assignment.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PoliticalCommunityObject implements PoliticalCommunity{
    private String number;
    private String name;
    private String shortName;
    private LocalDate lastUpdate;
    private Map<String, Set<String>> postalCommunityZipCodesAndAdditions;
  
    public PoliticalCommunityObject( String givenNumber, String givenName, String givenShortName, LocalDate givenLastUpdate, Map<String, Set<String>> givenPostalCommunities ){
      number = givenNumber;
      name = givenName;
      shortName = givenShortName;
      lastUpdate = givenLastUpdate;
      postalCommunityZipCodesAndAdditions = givenPostalCommunities;
    }
  
    public String getNumber(){
      return number;
    }
  
    public String getName(){
      return name;
    }
  
    public String getShortName(){
      return shortName;
    }
  
    public LocalDate getLastUpdate(){
      return lastUpdate;
    }
  
    public Map<String, Set<String>> getPostalCommunities(){
      return postalCommunityZipCodesAndAdditions;
    }
  
    public void addNewPostalCommunity( String zip, String zipCodeAddition ){
      if( postalCommunityZipCodesAndAdditions.containsKey(zip) ){
        Set<String> zipCodeAdditions = postalCommunityZipCodesAndAdditions.get(zip);
        zipCodeAdditions.add(zipCodeAddition);
        postalCommunityZipCodesAndAdditions.replace(zip, zipCodeAdditions);
      }else{
        Set<String> zipCodeAdditions = new HashSet<>();
        zipCodeAdditions.add( zipCodeAddition );
        postalCommunityZipCodesAndAdditions.put( zip, zipCodeAdditions );
      }
    }
  
    public boolean isEqualTo( PoliticalCommunity other ){
      return this.getNumber().equals( other.getNumber() );
    }
}  