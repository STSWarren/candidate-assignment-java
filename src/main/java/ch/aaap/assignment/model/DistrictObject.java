package ch.aaap.assignment.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ch.aaap.assignment.raw.CSVPoliticalCommunity;

public class DistrictObject implements District{
  private String number;
  private String name;
  private Set<String> politicalCommunityNumbersInDistrict;
  
  public DistrictObject( CSVPoliticalCommunity csvPoliticalCommunity ){
    number = csvPoliticalCommunity.getDistrictNumber();
    name = csvPoliticalCommunity.getDistrictName();
    politicalCommunityNumbersInDistrict = new HashSet<>();
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

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DistrictObject)) {
            return false;
        }
        DistrictObject districtObject = (DistrictObject) o;
        return Objects.equals(number, districtObject.number) && Objects.equals(name, districtObject.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(number, name);
  }
  
  }