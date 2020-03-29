package edu.baylor.csi3471.netime_planner.models;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {
    public LocalTime unmarshal(String v) {
        return LocalTime.parse(v);
    }

    public String marshal(LocalTime v) {
        return v.toString();
    }
}