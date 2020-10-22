package ch.aaap.assignment.model;

import java.util.Set;

public class ModelObject implements Model{
    private Set<PoliticalCommunity> politicalCommunitySet;
    private Set<PostalCommunity> postalCommunitySet;
    private Set<Canton> cantonSet;
    private Set<District> districtSet;
  
    public ModelObject(Set<Canton> givenCantonSet, Set<District> givenDistrictSet, Set<PoliticalCommunity> givenPoliticalCommunitySet, Set<PostalCommunity> givenPostalCommunitySet){
      cantonSet = givenCantonSet;
      districtSet = givenDistrictSet;
      politicalCommunitySet = givenPoliticalCommunitySet;
      postalCommunitySet = givenPostalCommunitySet;
    }
  
    public Set<PoliticalCommunity> getPoliticalCommunities(){
      return politicalCommunitySet;
    }
  
    public Set<PostalCommunity> getPostalCommunities(){
      return postalCommunitySet;
    }
  
    public Set<Canton> getCantons(){
      return cantonSet;
    }
  
    public Set<District> getDistricts(){
      return districtSet;
    }
  
  }
