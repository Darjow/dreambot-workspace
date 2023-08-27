package com.darjow.framework.Locations;

import org.dreambot.api.methods.map.Area;

public enum Location {
    DRAYNOR_VILLAGE_MARKET(new Area(0,0,0,0)),
    PORT_SARIM_SEAGULLS(new Area(0,0,0,0));


    Location(Area area){
        this.area = area;
    }
    private Area area;


    public Area getArea(){
        return area;
    }
}
