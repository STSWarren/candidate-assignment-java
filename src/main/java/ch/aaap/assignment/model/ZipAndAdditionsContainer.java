package ch.aaap.assignment.model;

import java.util.Set;

public interface ZipAndAdditionsContainer {

    public String getZip();
    
    public Set<String> getZipAdditions();

    public void addZipAddition( String newAddition );
}

