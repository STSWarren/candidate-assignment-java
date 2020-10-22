package ch.aaap.assignment.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;

public class ModelObject implements Model{
  private Set<PoliticalCommunity> politicalCommunitySet;
  private Set<PostalCommunity> postalCommunitySet;
  private Set<Canton> cantonSet;
  private Set<District> districtSet;

  public ModelObject(Set<CSVPoliticalCommunity> csvPoliticalCommunities, Set<CSVPostalCommunity> csvPostalCommunities){
    List<Canton> cantons = new ArrayList<>();
    List<District> districts = new ArrayList<>();
    List<PoliticalCommunity> politicalCommunities = new ArrayList<>();
    List<PostalCommunity> postalCommunities = new ArrayList<>();

    for( CSVPoliticalCommunity csvPoliticalCommunity : csvPoliticalCommunities ){
      PoliticalCommunity newPoliticalCommunity = new PoliticalCommunityObject( csvPoliticalCommunity );
      District newDistrict = new DistrictObject( csvPoliticalCommunity );
      Canton newCanton = new CantonObject( csvPoliticalCommunity );

      //if the canton already exists in the list, get its current state into a local variable to possibly update. 
      //in order to avoid adding the same canton at various states, remove it from the list.
      if( cantons.contains(newCanton)){
        newCanton = cantons.get( cantons.indexOf( newCanton ) );
        //we remove the old canton value from the list so that we don't end up with multiple cantons
        //with the same name and code but different values in their districts lists.
        cantons.remove( newCanton );
      }

      newCanton.addNewDistrict( newDistrict.getNumber() );

      //If the district has already been recorded before, store it in a local variable and remove it from the list.
      if( districts.contains( newDistrict ) ){
        newDistrict = districts.get( districts.indexOf( newDistrict ) );
        //we remove the old district value from the list so that we don't end up with multiple districts
        //with the same name and code but different values in their politicalCommunities lists.
        districts.remove( newDistrict );
      }

      newDistrict.addNewPoliticalCommunityNumber( newPoliticalCommunity.getNumber() );
      
      //Add the political community to its list.
      //Add the district and canton to their list or the updated district and canton back to the list if they were present previously.
      politicalCommunities.add( newPoliticalCommunity );
      districts.add( newDistrict );
      cantons.add( newCanton );
    }

    for( CSVPostalCommunity csvPostalCommunity : csvPostalCommunities ){
      PostalCommunity newPostalCommunity = new PostalCommunityObject( csvPostalCommunity );
      
      int indexOfPoliticalCommunity =  
        getPoliticalCommunityIndexFromListByNumber(csvPostalCommunity.getPoliticalCommunityNumber(), politicalCommunities);

      if(indexOfPoliticalCommunity > -1){
        PoliticalCommunity politicalCommunity = politicalCommunities.get( indexOfPoliticalCommunity );
        politicalCommunities.remove( indexOfPoliticalCommunity );
        
        politicalCommunity.addNewPostalCommunity( 
          newPostalCommunity.getZipCode(), 
          newPostalCommunity.getZipCodeAddition() );
          
        politicalCommunities.add( politicalCommunity );
      }

      if( postalCommunities.contains( newPostalCommunity )){
        newPostalCommunity = postalCommunities.get( postalCommunities.indexOf( newPostalCommunity ) );
        postalCommunities.remove(newPostalCommunity);
      }

      newPostalCommunity.addNewPoliticalCommunity( csvPostalCommunity.getPoliticalCommunityNumber() );
      postalCommunities.add( newPostalCommunity );
    }

    cantonSet = new HashSet<>( cantons );
    districtSet = new HashSet<>( districts );
    politicalCommunitySet = new HashSet<>( politicalCommunities );
    postalCommunitySet = new HashSet<>( postalCommunities );
  }
  
  /**
   * @param politicalCommunityNumberToFind, the number of a political community one wants to find.
   * @param politicalCommunities a list of PostalCommunity objects to be searched through. 
   * @return the index of a political community with a number matching politicalCommunityNumberToFind in postalCommunities 
   * or -1 if its not present or there is more than 1.
   */
  public int getPoliticalCommunityIndexFromListByNumber( String politicalCommunityNumberToFind, 
  List<PoliticalCommunity> politicalCommunities ){
    int indexOfPoliticalCommunity =  -1;
    List<PoliticalCommunity> politicalCommunityMatchList = 
      politicalCommunities.stream().filter( politicalCommunity -> 
        politicalCommunity.getNumber().equals( politicalCommunityNumberToFind ) )
          .collect( Collectors.toList() );

    if( !politicalCommunityMatchList.isEmpty() && politicalCommunityMatchList.size() < 2 ){
      indexOfPoliticalCommunity = politicalCommunities.indexOf( politicalCommunityMatchList.get(0) );
    }

    return indexOfPoliticalCommunity;
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
