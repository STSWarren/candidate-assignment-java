package ch.aaap.assignment;

import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.ModelObject;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PostalCommunity;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import ch.aaap.assignment.raw.CSVUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
    model = new ModelObject( csvPoliticalCommunities, csvPostalCommunities );
  }

  /** @return model */
  public Model getModel() {
    return model;
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of political communities in given canton
   */
  public long getAmountOfPoliticalCommunitiesInCanton( String cantonCode ) {
    long total = 0;
    List<Long> politicalCommunitiesInDistricts = new ArrayList<>();
    
    List<Canton> cantonMatchList = model
      .getCantons().stream().filter( canton -> 
        canton.getCode().equals( cantonCode ) )
          .collect(Collectors.toList() );

    if(cantonMatchList.isEmpty()){
      throw new IllegalArgumentException("No canton with that code exists.");
    }

    if( cantonMatchList.size() < 2){
      cantonMatchList.get(0)
        .getDistrictCodesInCanton().stream()
          .forEach( districtNumber -> politicalCommunitiesInDistricts
                .add( getAmountOfPoliticalCommunitiesInDistrict( districtNumber ) )
          );
    }
    
    total = politicalCommunitiesInDistricts.stream().collect(Collectors.summingLong(Long::longValue));

    return total;
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of districts in given canton
   */
  public long getAmountOfDistrictsInCanton( String cantonCode ) {
    List<Canton> cantonMatchList = 
      model.getCantons().stream()
      .filter( canton -> canton.getCode().equals( cantonCode ) )
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
  public long getAmountOfPoliticalCommunitiesInDistrict( String districtNumber ) {
    List<District> districtMatchList = 
      model.getDistricts().stream()
        .filter( district -> district
          .getNumber().equals( districtNumber ) )
            .collect( Collectors.toList() );

    if( districtMatchList.isEmpty() ){
      throw new IllegalArgumentException("No district with that number exists.");
    }
    
    return districtMatchList.get(0).getPoliticalCommunityNumbersInDistrict().size();
  }

  /**
   * @param zipCode 4 digit zip code
   * @return district that belongs to specified zip code
   */
  public Set<String> getDistrictsForZipCode( String zipCode ) {
    List<PostalCommunity> postalCommunitiesMatchList = 
      model.getPostalCommunities().stream()
        .filter(postalCommunity -> postalCommunity.getZipCode().equals( zipCode ) )
          .collect(Collectors.toList());

    List<String> politicalCommunityNumbers = new ArrayList<>();
    postalCommunitiesMatchList.stream().forEach( postalCommunity -> 
      politicalCommunityNumbers.addAll( postalCommunity.getPoliticalCommunities() ) );

    final Set<String> politicalCommunityNumbersSet = new HashSet<>( politicalCommunityNumbers );
    List<String> districts = Collections.synchronizedList(new ArrayList<>());

    model.getDistricts().stream().forEach( district ->{

      if(district.getPoliticalCommunityNumbersInDistrict().stream().anyMatch( politicalCommunityNumbersSet::contains )){
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
      String postalCommunityName ) {
    LocalDate lastUpdateOfPoliticalCommunity = LocalDate.now();
    List<PostalCommunity> postalCommunityMatchList = 
      model.getPostalCommunities().stream()
        .filter( postalCommunity -> postalCommunity.getName()
          .equals( postalCommunityName )).collect( Collectors.toList() );

    if( postalCommunityMatchList.isEmpty() ){
      throw new IllegalArgumentException("No postal community with that name exists.");
    }

    if( postalCommunityMatchList.size() > 1){
      throw new IllegalArgumentException("Postal community with that name does not resolve to a single postal community.");
    }

    PostalCommunity postalCommunity = postalCommunityMatchList.get(0);
    if( postalCommunity.getPoliticalCommunities().size() > 1){
      throw new IllegalArgumentException("Postal community with that name is linked to multiple political communities.");
    }

    if( postalCommunity.getPoliticalCommunities().isEmpty() ){
      throw new IllegalArgumentException("Postal community with that name is linked to no political communities.");
    }

    String politicalCommunityNumber = postalCommunity.getPoliticalCommunities().iterator().next();
    List<PoliticalCommunity> politicalCommunitiesMatchList = model.getPoliticalCommunities().stream()
      .filter( politicalCommunity -> politicalCommunity.getNumber().equals( politicalCommunityNumber ) )
          .collect(Collectors.toList() );

    if( !politicalCommunitiesMatchList.isEmpty() ){
      lastUpdateOfPoliticalCommunity = politicalCommunitiesMatchList.get(0).getLastUpdate();
    }

    return lastUpdateOfPoliticalCommunity;
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
