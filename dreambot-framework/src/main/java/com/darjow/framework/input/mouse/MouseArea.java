package com.darjow.framework.input.mouse;

import java.awt.*;
import java.util.Random;

public class MouseArea extends Rectangle {
    private Random r;

    public MouseArea(int x1, int y1, int x2, int y2) {
        super(x1, y1, Math.abs(x1 - x2), Math.abs(y1 - y2));
        r = new Random();
    }
    public MouseArea(Rectangle area){
        super(area.x, area.y, area.width, area.height);
        r = new Random();
    }

    public MouseArea(Point topleft, Point bottomright) {
        super(topleft.x, topleft.y, Math.abs(topleft.x - bottomright.x), Math.abs(topleft.y - bottomright.y));
        r = new Random();
    }

    public Point getRandomPoint() {
        return new Point(x + r.nextInt(width + 1), y + r.nextInt(height + 1));
    }

    public Point getRandomGaussianPoint() {
        return new Point((int) (getCenterX() + r.nextGaussian() * width / 6), (int) (getCenterY() + r.nextGaussian() * height / 6));
    }

    public Point getRandomGaussianPoint(boolean forceInside) {
        Point p = new Point((int) (getCenterX() + r.nextGaussian() * width / 6), (int) (getCenterY() + r.nextGaussian() * height / 6));
        if (forceInside)
            while (!this.contains(p))
                p = new Point((int) (getCenterX() + r.nextGaussian() * width / 6), (int) (getCenterY() + r.nextGaussian() * height / 6));
        return p;
    }
}