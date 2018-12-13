package com.edinarobotics.purepursuit;

public interface LineBase {
    public PPPoint getPt1();
    public PPPoint getPt2();

    public double getAngleDeg();

    default public double getLength() {
        double xd = getPt1().x - getPt2().x;
        double yd = getPt1().y - getPt2().y;
        return Math.sqrt((xd * xd) + (yd * yd));
    }

    default  String show() {return String.format("  line: %s -> %s; angle=%.2f", getPt1().toString(), getPt2().toString(), getAngleDeg()); }
}
