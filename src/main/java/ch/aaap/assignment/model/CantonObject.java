package ch.aaap.assignment.model;

import java.util.Set;

public class CantonObject implements Canton{
    private String code;
    private String name;
    private Set<String> districtCodesInCanton;
  
    public CantonObject(String givenCode, String givenName, Set<String> districts){
      code = givenCode;
      name = givenName;
      districtCodesInCanton = districts;
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
  
    public boolean isEqualTo( Canton other ){
      return this.getCode().equals(other.getCode() );
    }
  }
