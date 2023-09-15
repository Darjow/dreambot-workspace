package com.darjow.framework.enums;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.interactive.Locatable;

import java.util.Arrays;

public enum Location{
    DRAYNOR_VILLAGE_MARKET(new Area(3075, 3246, 3086, 3255)),
    PORT_SARIM_SEAGULLS(new Area(3026,3227,3040,3240)),
    LUMBRIDGE_TELEPORT(new Area(3215,3213,3226,3321,0)),
    EDGEVILLE_FURNACE(new Area(0,0,0,0));


    Location(Area area){
        this.area = area;
    }


    private final Area area;


    public Area getArea(){
        return area;
    }

    public Tile getWalkableTile() {
        return Arrays.stream(area.getTiles())
                .filter(e-> e.canReach())
                .findAny()
                .orElse(null);
    }
}
