package edu.baylor.csi3471.netime_planner.models.adapters;

import javax.xml.bind.annotation.XmlAttribute;

public class EntryElement<K, V> {
    @XmlAttribute public K key;
    @XmlAttribute
    public V value;

    public EntryElement() {}

    public EntryElement(K key, V value) {
        this.key = key;
        this.value = value;
    }
}