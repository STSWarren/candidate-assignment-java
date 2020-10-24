package ch.aaap.assignment.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ZipAndAdditionsContainerObject implements ZipAndAdditionsContainer{
    private String zip;
    private Set<String> zipAdditions;

    public ZipAndAdditionsContainerObject( String newZip ){
        zip = newZip;
        zipAdditions = new HashSet<>();
    }

    public String getZip(){
        return zip;
    }
    
    public Set<String> getZipAdditions(){
        return zipAdditions;
    }

    public void addZipAddition( String newAddition ){
        zipAdditions.add( newAddition );
    }


    @Override
    public boolean equals(Object o) {
        if (o == this){
            return true;
        }
        if (!(o instanceof ZipAndAdditionsContainerObject)) {
            return false;
        }
        ZipAndAdditionsContainerObject zipAndAdditionsContainerObject = 
            (ZipAndAdditionsContainerObject) o;

        return Objects.equals(zip, zipAndAdditionsContainerObject.zip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zip);
    }

}
