package ch.aaap.assignment.model;

import java.util.Set;

public interface ZipAndAdditionsContainer {

    public String getZip();
    
    public Set<String> getZipAdditions();
    
 /**
   * @param newAddition to add to the list of zipcode addition numbers 
   * associated with this object's zip.
   */
    public void addZipAddition( String newAddition );
}

