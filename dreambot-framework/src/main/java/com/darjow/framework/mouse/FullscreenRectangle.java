package com.darjow.framework.mouse;

import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.methods.widget.Widgets;

import java.awt.*;

public class FullscreenRectangle{

    private static final int WIDGET_ID = 548;
    private static final int WIDGET_CHILD_ID = 0;

    public static Rectangle getRectangle(){
        return Widgets.getWidget(WIDGET_ID).getChild(WIDGET_CHILD_ID).getRectangle();

    }
}
