package edu.baylor.csi3471.netime_planner.models.adapters;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "map")
public class MapElement<K, V> {

    @XmlElement(name = "entry")
    public List<EntryElement<K, V>> entries = new ArrayList<EntryElement<K, V>>();

    public void addEntry(K key, V value) {
        entries.add(new EntryElement<>(key, value));
    }

}