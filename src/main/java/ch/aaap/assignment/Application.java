package ch.aaap.assignment;

import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.ModelObject;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PoliticalCommunityObject;
import ch.aaap.assignment.model.PostalCommunity;
import ch.aaap.assignment.model.PostalCommunityObject;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.DistrictObject;
import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.CantonObject;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import ch.aaap.assignment.raw.CSVUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Application {

  private Model model = null;

  public Application() {
    initModel();
  }

  public static void main(String[] args) {
    new Application();
  }

  /** Reads the CSVs and initializes a in memory model */
  private void initModel() {
    Set<CSVPoliticalCommunity> csvPoliticalCommunities = CSVUtil.getPoliticalCommunities();
    Set<CSVPostalCommunity> csvPostalCommunities = CSVUtil.getPostalCommunities();

    List<Canton> cantons = new ArrayList<>();
    List<District> districts = new ArrayList<>();
    List<PoliticalCommunity> politicalCommunities = new ArrayList<>();
    List<PostalCommunity> postalCommunities = new ArrayList<>();

    for( CSVPoliticalCommunity csvPoliticalCommunity : csvPoliticalCommunities ){
      PoliticalCommunity newPoliticalCommunity = new PoliticalCommunityObject(
        csvPoliticalCommunity.getNumber(),
        csvPoliticalCommunity.getName(),
        csvPoliticalCommunity.getShortName(),
        csvPoliticalCommunity.getLastUpdate(),
        new HashMap<>() );

      District newDistrict = new DistrictObject(
        csvPoliticalCommunity.getDistrictNumber(),
        csvPoliticalCommunity.getDistrictName(),
        new HashSet<>() );

      Canton newCanton = new CantonObject(
        csvPoliticalCommunity.getCantonCode(),
        csvPoliticalCommunity.getCantonName(),
        new HashSet<>() );

      int cantonIndexInCantonList = getCantonIndexFromList(newCanton, cantons);
      int districtIndexInCantonsDistrictList = getDistrictIndexFromList(newDistrict, districts);

      //if the canton already exists in the list, get its current state into a local variable to possibly update. 
      //in order to avoid adding the same canton at various states, remove it from the list.
      if( cantonIndexInCantonList > -1){
        newCanton = cantons.get( cantonIndexInCantonList );
        cantons.remove( cantonIndexInCantonList );
      }

      //Check if the canton's list of the districts within it has this district already, if not, add it.
      if( !newCanton.getDistrictCodesInCanton().contains( newDistrict.getNumber() ) ){
            newCanton.addNewDistrict( newDistrict.getNumber() );
      }

      //If the district has already been recorded before, store it in a local variable and remove it from the list.
      if( districtIndexInCantonsDistrictList > -1 ){
        newDistrict = districts.get( districtIndexInCantonsDistrictList );
        districts.remove( newDistrict );
      }

      //Check if the district's list of the political communities within it has this political community already, if not, add it.
      if( !newDistrict.getPoliticalCommunityNumbersInDistrict().contains( newPoliticalCommunity.getNumber() ) ){
        newDistrict.addNewPoliticalCommunityNumber( newPoliticalCommunity.getNumber() );
      }
      
      //Add the political community to its list.
      //Add the district and canton to their list or the updated district and canton back to the list if they were present previously.
      politicalCommunities.add( newPoliticalCommunity );
      districts.add( newDistrict );
      cantons.add( newCanton );
    }

    for( CSVPostalCommunity csvPostalCommunity : csvPostalCommunities ){
      PostalCommunity newPostalCommunity = new PostalCommunityObject(
        csvPostalCommunity.getZipCode(),
        csvPostalCommunity.getZipCodeAddition(),
        csvPostalCommunity.getName(),
        new HashSet<>() );
      
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

      int indexOfPostalCommunity = getPostalCommunityIndexFromList(newPostalCommunity, postalCommunities);
      if( indexOfPostalCommunity != -1){
        newPostalCommunity = postalCommunities.get(indexOfPostalCommunity);
        postalCommunities.remove(indexOfPostalCommunity);
      }

      newPostalCommunity.addNewPoliticalCommunity( csvPostalCommunity.getPoliticalCommunityNumber() );
      postalCommunities.add( newPostalCommunity );
    }

    Set<Canton> cantonsSet = new HashSet<>( cantons );
    Set<District> districtsSet = new HashSet<>( districts );
    Set<PoliticalCommunity> politicalCommunitiesSet = new HashSet<>( politicalCommunities );
    Set<PostalCommunity> postalCommunitiesSet = new HashSet<>( postalCommunities );
    
    model = new ModelObject( cantonsSet, districtsSet, politicalCommunitiesSet, postalCommunitiesSet );
  }

  /**
   * @param cantonToFind, an object of type Canton.
   * @param cantons a list of Canton objects to be searched through. 
   * @return the index cantonToFind in cantons or -1 if its not present.
   */
  public int getCantonIndexFromList(Canton cantonToFind, List<Canton> cantons){
    int cantonIndexInCantonList = -1;
      
    List<Canton> cantonMatchList = 
      cantons.stream().filter( canton -> 
        canton.isEqualTo( cantonToFind ) )
          .collect( Collectors.toList() );

    if( !cantonMatchList.isEmpty() && cantonMatchList.size()<2 ){
      cantonIndexInCantonList = 
          cantons.indexOf(cantonMatchList.get( 0 ) );
    }
    
    return cantonIndexInCantonList;
  }

  /**
   * @param districtToFind, an object of type District.
   * @param districts a list of District objects to be searched through. 
   * @return the index districtToFind in districts or -1 if its not present.
   */
  public int getDistrictIndexFromList(District districtToFind, List<District> districts){
    int districtIndexInCantonsDistrictList = -1;
    List<District> districtMatchList = 
      districts.stream().filter( district -> 
        district.isEqualTo( districtToFind ) )
          .collect( Collectors.toList() );

    if( !districtMatchList.isEmpty() && districtMatchList.size()<2 ){
      districtIndexInCantonsDistrictList = 
          districts.indexOf( districtMatchList.get(0) );
    }

    return districtIndexInCantonsDistrictList;
  }

  /**
   * @param politicalCommunityNumberToFind, the number of a political community one wants to find.
   * @param politicalCommunities a list of PostalCommunity objects to be searched through. 
   * @return the index of a political community with a number matching politicalCommunityNumberToFind in postalCommunities 
   * or -1 if its not present.
   */
  public int getPoliticalCommunityIndexFromListByNumber(String politicalCommunityNumberToFind, 
  List<PoliticalCommunity> politicalCommunities){
    int indexOfPoliticalCommunity =  -1;
    List<PoliticalCommunity> politicalCommunityMatchList = 
      politicalCommunities.stream().filter( politicalCommunity -> 
        politicalCommunity.getNumber().equals(politicalCommunityNumberToFind) )
          .collect( Collectors.toList() );

    if( !politicalCommunityMatchList.isEmpty() && politicalCommunityMatchList.size()<2 ){
      indexOfPoliticalCommunity = politicalCommunities.indexOf( politicalCommunityMatchList.get(0) );
    }

    return indexOfPoliticalCommunity;
  }

  /**
   * @param postalCommunityToFind, an object of type PostalCommunity.
   * @param postalCommunities a list of PostalCommunity objects to be searched through for postalCommunity to find.
   * @return the index of postalCommunityToFind in postalCommunities or -1 if its not present.
   */
  public int getPostalCommunityIndexFromList( PostalCommunity postalCommunityToFind,
        List<PostalCommunity> postalCommunities ){
    int indexOfPostalCommunity = -1;
    List<PostalCommunity> postalCommunityMatchList = 
      postalCommunities.stream().filter( postalCommunity -> 
        postalCommunity.isEqualTo(postalCommunityToFind))
          .collect(Collectors.toList());

    if( !postalCommunityMatchList.isEmpty() && postalCommunityMatchList.size() < 2){
      indexOfPostalCommunity = postalCommunities.indexOf( postalCommunityMatchList.get(0) );
    }

    return indexOfPostalCommunity;
  }

  /** @return model */
  public Model getModel() {
    return model;
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of political communities in given canton
   */
  public long getAmountOfPoliticalCommunitiesInCanton(String cantonCode) {
    long total = 0;
    List<Long> politicalCommunitiesInDistricts = Collections.synchronizedList( new ArrayList<>() );
    
    List<Canton> cantonMatchList = model
      .getCantons().stream().filter(canton -> 
        canton.getCode().equals( cantonCode ) )
          .collect(Collectors.toList() );

    if(cantonMatchList.isEmpty()){
      throw new IllegalArgumentException("No canton with that code exists.");
    }

    if( cantonMatchList.size() < 2){
      cantonMatchList.get(0)
        .getDistrictCodesInCanton().stream().parallel()
          .forEach( districtNumber -> politicalCommunitiesInDistricts
                .add( getAmountOfPoliticalCommunitiesInDistrict( districtNumber ) )
          );
    }
    
    for( Long subtotal : politicalCommunitiesInDistricts ){
      total += subtotal;
    }

    return total;
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of districts in given canton
   */
  public long getAmountOfDistrictsInCanton(String cantonCode) {
    List<Canton> cantonMatchList = 
      model.getCantons().stream()
      .filter(canton -> canton.getCode().equals( cantonCode ) )
        .collect(Collectors.toList() );

    if( cantonMatchList.isEmpty() ){
      throw new IllegalArgumentException("No canton with that code exists.");
    }

    return cantonMatchList.get(0).getDistrictCodesInCanton().size();
  }

  /**
   * @param districtNumber of a district (e.g. 101)
   * @return amount of districts in given canton
   */
  public long getAmountOfPoliticalCommunitiesInDistrict(String districtNumber) {
    List<District> districtMatchList = 
      model.getDistricts().stream()
        .filter(district -> district.getNumber().equals( districtNumber ) )
          .collect(Collectors.toList() );

    if( districtMatchList.isEmpty() ){
      throw new IllegalArgumentException("No district with that number exists.");
    }
    
    return districtMatchList.get(0).getPoliticalCommunityNumbersInDistrict().size();
  }

  /**
   * @param zipCode 4 digit zip code
   * @return district that belongs to specified zip code
   */
  public Set<String> getDistrictsForZipCode(String zipCode) {
    List<PostalCommunity> postalCommunitiesMatchList = 
      model.getPostalCommunities().stream()
        .filter(postalCommunity -> postalCommunity.getZipCode().equals(zipCode) )
          .collect(Collectors.toList());

    List<String> politicalCommunityNumbers = Collections.synchronizedList( new ArrayList<>() );
    postalCommunitiesMatchList.stream().parallel().forEach(postalCommunity -> 
      politicalCommunityNumbers.addAll(postalCommunity.getPoliticalCommunities() ) );

    final Set<String> politicalCommunityNumbersSet = new HashSet<>( politicalCommunityNumbers );
    List<String> districts = Collections.synchronizedList(new ArrayList<>());

    model.getDistricts().stream().parallel().forEach(district ->{

      if(district.getPoliticalCommunityNumbersInDistrict().stream().anyMatch(politicalCommunityNumbersSet::contains)){
        districts.add( district.getName() );
      }

    } );

    return new HashSet<>( districts );
  }

  /**
   * @param postalCommunityName name
   * @return lastUpdate of the political community by a given postal community name
   */
  public LocalDate getLastUpdateOfPoliticalCommunityByPostalCommunityName(
      String postalCommunityName) {
    List<PostalCommunity> postalCommunityMatchList = 
      model.getPostalCommunities().stream()
        .filter(postalCommunity -> postalCommunity.getName()
          .equals(postalCommunityName)).collect(Collectors.toList());

    if( postalCommunityMatchList.isEmpty() ){
      throw new IllegalArgumentException("No postal community with that name exists.");
    }

    if( postalCommunityMatchList.size() > 1){
      throw new IllegalArgumentException("Postal community with that name does not resolve to a single postal community.");
    }

    PostalCommunity postalCommunity = postalCommunityMatchList.get(0);
    if(postalCommunity.getPoliticalCommunities().size() > 1){
      throw new IllegalArgumentException("Postal community with that name is linked to multiple political communities.");
    }

    if(postalCommunity.getPoliticalCommunities().isEmpty()){
      throw new IllegalArgumentException("Postal community with that name is linked to no political communities.");
    }

    String politicalCommunityNumber = postalCommunity.getPoliticalCommunities().iterator().next();
    List<PoliticalCommunity> politicalCommunitiesList = model.getPoliticalCommunities().stream()
      .filter( politicalCommunity -> politicalCommunity.getNumber().equals(politicalCommunityNumber) )
          .collect(Collectors.toList() );

    if( !politicalCommunitiesList.isEmpty() ){
      return politicalCommunitiesList.get(0).getLastUpdate();
    }
    
    return null;
  }

  /**
   * https://de.wikipedia.org/wiki/Kanton_(Schweiz)
   *
   * @return amount of canton
   */
  public long getAmountOfCantons() {
    return model.getCantons().size();
  }

  /**
   * https://de.wikipedia.org/wiki/Kommunanz
   *
   * @return amount of political communities without postal communities
   */
  public long getAmountOfPoliticalCommunityWithoutPostalCommunities() {
    return model
      .getPoliticalCommunities().stream()
        .filter( politicalCommunity -> 
          politicalCommunity
          .getPostalCommunities()
            .isEmpty() )
              .collect( Collectors.toList() )
                .size();
  }
}
