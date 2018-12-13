package com.edinarobotics.purepursuit;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class PeteTest {
//    static class Point {
//        double x, y;
//        Point(double x, double y) { this.x = x; this.y = y; }
//        double[] asArray() { return new double[] { x, y }; }
//        public String toString() { return String.format("[%.2f, %.2f]", x, y); }
//    }
    
    static class Line implements LineBase {
        PPPoint pt1, pt2;
        private double angle, sinAngle, cosAngle;

        Line(PPPoint pt1, PPPoint pt2) {
            this.pt1 = pt1;
            this.pt2 = pt2;
            this.angle = Math.atan2(pt2.y - pt1.y, pt2.x - pt1.x);
            this.sinAngle = Math.sin(angle);
            this.cosAngle = Math.cos(angle);
        }
        @Override
        public PPPoint getPt1() { return pt1; }
        @Override
        public PPPoint getPt2() { return pt2; }
        double getAngle() { return angle; }
        @Override
        public double getAngleDeg() { return Math.toDegrees(angle); }
        
        PPPoint rotate(PPPoint pt) {
            double xTran = pt.x - pt1.x;
            double yTran = pt.y - pt1.y;
            double x, y;
//  TODO: figure out where this part of the algorithm is needed
//            if (angle > 0.0) {
                x = (xTran * cosAngle) + (yTran * sinAngle);
                y = -(xTran * sinAngle) + (yTran * cosAngle);
//            } else {
//                x = (xTran * cosAngle) - (yTran * sinAngle);
//                y = (xTran * sinAngle) + (yTran * cosAngle);
//            }
            return new PPPoint(x, y);
        }

        double getDesiredHeadingDeg(PPPoint pt, double lookAhead) {
            PPPoint ptT = rotate(pt);
            double hdg = Math.toDegrees(Math.asin(-ptT.y / lookAhead));
            return hdg + getAngleDeg();
        }

        public String toString() { return String.format("  line: %s -> %s; angle=%.2f", pt1.toString(), pt2.toString(), getAngleDeg()); }
    }
    
    /**
     * Command line format: lookAheadDist robotX robotY pt1X pt1Y pt2X pt2Y
     */
    public static void main(String[] args) {
        double lookAhead = 8.0;
        PPPoint pt1 = new PPPoint(0.0, 24.0);
        PPPoint pt2 = new PPPoint(36.0, 36.0);
        PPPoint robotPt = new PPPoint(18.0, 28.0);
        if (args.length > 0) { lookAhead = Double.parseDouble(args[0]); }
        if (args.length > 2) { robotPt = new PPPoint(Double.parseDouble(args[1]), Double.parseDouble(args[2])); }
        if (args.length > 4) { pt1 = new PPPoint(Double.parseDouble(args[3]), Double.parseDouble(args[4])); }
        if (args.length > 6) { pt2 = new PPPoint(Double.parseDouble(args[5]), Double.parseDouble(args[6])); }

        Line ln = new Line(pt1, pt2);
        PPPoint robotPtT = ln.rotate(robotPt);
        PPPoint pt2t = ln.rotate(pt2);
        double desiredHdgDeg = ln.getDesiredHeadingDeg(robotPt, lookAhead);
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("  LookAhead=%.2f, LineAngle=%5.2f, robot: %s,  desiredHdg=%.2f, line: %s\n",
                lookAhead, ln.getAngleDeg(), robotPt.toString(), desiredHdgDeg, ln.show());

        PPLine ppLine = new PPLine(pt1, pt2, lookAhead);
        double ppDesiredHdg = 90.0 - ppLine.getDesiredHeading(robotPt.x, robotPt.y);
        System.out.printf("desHdg=%6.2f;  ppDesHdg=%6.2f\n", desiredHdgDeg, ppDesiredHdg);

        boolean status = isOnLine(ln, robotPt, lookAhead, desiredHdgDeg);
        System.out.println("Using TransRot alg: on line = " + (status ? "YES" : "NO"));
        status = isOnLine(ppLine, robotPt, lookAhead, ppDesiredHdg);
        System.out.println("Using PPLine alg:  on line = " + (status ? "YES" : "NO"));
    }

    // determine if the end of the look-ahead line at the specified heading will intersect the line segment
    public static boolean isOnLine(LineBase line, PPPoint robotPt, double lookAhead, double headingDeg) {
        // use desired heading, look ahead distance and current robot position to determine x,y position
        double x = Math.cos(Math.toRadians(headingDeg)) * lookAhead + robotPt.x;
        double y = Math.sin(Math.toRadians(headingDeg)) * lookAhead + robotPt.y;

        // get the slope and y-intercept of the line segment we are trying to match
        double slope = (line.getPt2().y - line.getPt1().y) / (line.getPt2().x - line.getPt1().x);
        double a = line.getPt1().y - (line.getPt1().x * slope);

        // test to see if the calculated y position matches the y value at X
        return check("verifying line", x * slope + a, y);
    }

    public static boolean check(String msg, double a, double b) {
        if (Math.abs(a - b) > 0.01) {
            System.out.printf("  Problem: %s, a=%.2f, b=%.2f\n", msg, a, b);
            return false;
        }
        return true;
    }

}
