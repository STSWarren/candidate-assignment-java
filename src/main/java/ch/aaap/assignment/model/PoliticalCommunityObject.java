package ch.aaap.assignment.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ch.aaap.assignment.raw.CSVPoliticalCommunity;

public class PoliticalCommunityObject implements PoliticalCommunity{
  private String number;
  private String name;
  private String shortName;
  private LocalDate lastUpdate;
  private List<ZipAndAdditionsContainer> postalCommunityZipCodesAndAdditions;
  
  public PoliticalCommunityObject( CSVPoliticalCommunity csvPoliticalCommunity ){
    number = csvPoliticalCommunity.getNumber();
    name = csvPoliticalCommunity.getName();
    shortName = csvPoliticalCommunity.getShortName();
    lastUpdate = csvPoliticalCommunity.getLastUpdate();
    postalCommunityZipCodesAndAdditions = new ArrayList<>();  
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
  
  public List<ZipAndAdditionsContainer> getPostalCommunities(){
    return postalCommunityZipCodesAndAdditions;
  }
  
  public void addNewPostalCommunity( String zip, String zipCodeAddition ){
    ZipAndAdditionsContainer newContainer = new ZipAndAdditionsContainerObject( zip );
    
    if( postalCommunityZipCodesAndAdditions.contains( newContainer ) ){

      newContainer = postalCommunityZipCodesAndAdditions.get( 
        postalCommunityZipCodesAndAdditions.indexOf(
          postalCommunityZipCodesAndAdditions.get( 0 ) ) );

      postalCommunityZipCodesAndAdditions.remove( newContainer );

      newContainer.addZipAddition( zipCodeAddition );
    }

    postalCommunityZipCodesAndAdditions.add( newContainer );
  }

  @Override
    public boolean equals(Object o) {
        if (o == this){
          return true;
        }
        if (!(o instanceof PoliticalCommunityObject)) {
            return false;
        }
        PoliticalCommunityObject politicalCommunityObject = (PoliticalCommunityObject) o;

        return Objects.equals(number, politicalCommunityObject.number) 
          && Objects.equals(name, politicalCommunityObject.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(number, name);
  }
  
}  