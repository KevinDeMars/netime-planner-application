package edu.baylor.csi3471.netime_planner.models.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public class XmlMapAdapter<K, V> extends XmlAdapter<MapElement<K, V>, Map<K, V>> {

    @Override
    public MapElement<K, V> marshal(Map<K, V> v) throws Exception {

        if (v == null || v.isEmpty()) {return null;}

        MapElement<K, V> map = new MapElement<>();

        for (K key : v.keySet()) {
            map.addEntry(key, v.get(key));
        }

        return map;
    }

    @Override
    public Map<K, V> unmarshal(MapElement<K, V> v) throws Exception {
        if (v == null) {return null;}

        Map<K, V> map = new HashMap<>(v.entries.size());

        for (EntryElement<K, V> entry : v.entries) {
            map.put(entry.key, entry.value);
        }

        return map;
    }

}