package ch.aaap.assignment.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ch.aaap.assignment.raw.CSVPoliticalCommunity;

public class CantonObject implements Canton{
  private String code;
  private String name;
  private Set<String> districtCodesInCanton;
  
  public CantonObject( CSVPoliticalCommunity csvPoliticalCommunity ){
    code = csvPoliticalCommunity.getCantonCode();
    name = csvPoliticalCommunity.getCantonName();
    districtCodesInCanton = new HashSet<>();
  }
  
  public String getCode(){
    return code;
  }
  
  public String getName(){
    return name;
  }
  
  public Set<String> getDistrictCodesInCanton(){
    return districtCodesInCanton;
  }
  
  public void addNewDistrict( String newDistrictCode ){
    districtCodesInCanton.add( newDistrictCode );
  }

  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CantonObject)) {
            return false;
        }
        CantonObject cantonObject = (CantonObject) o;
        return Objects.equals(code, cantonObject.code) && Objects.equals(name, cantonObject.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, name);
  }


}
