/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import java.util.ArrayList;
import java.util.Comparator;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.vision.linear_algebra.Matrix;
/**
 * Add your docs here.
 */
import frc.robot.vision.linear_algebra.Point;
import frc.robot.vision.linear_algebra.Transform;

public class OffsetCalculator {
    private static final Point[] OUTER_TARGET_CORNERS = {
        new Point(0, 17), // top left
        new Point(39.25, 17), // top right
        new Point(29.445, 0), // bottom right
        new Point(9.825, 0) // bottom left
    };
    private static final Point INNER_TARGET_CENTER = new Point(19.625, 17);
    private static final double LIMELIGHT_HORIZONTAL_FOV_PIXELS = 320;

    public static double getTargetHorizontalOffset(double[] xs, double[] ys) throws Exception {
        Point[] screenCorners = sortPoints(points(xs, ys));

        Matrix worldToScreenMatrix = Transform.getPerspectiveTransformMatrix(OUTER_TARGET_CORNERS, screenCorners);

        Point screenPoint = Transform.transformPoint(worldToScreenMatrix, INNER_TARGET_CENTER);

        // instead of 160 being center screen, we want 0 to be center screen
        return screenPoint.x - LIMELIGHT_HORIZONTAL_FOV_PIXELS/2;
    }

    private static ArrayList<Point> points(double[] xs, double[] ys) {
        ArrayList<Point> ret = new ArrayList<>(xs.length);

        for (int i=0; i<xs.length; i++) {
            ret.add(new Point(xs[i], ys[i]));
        }

        return ret;
    }

    private static Point[] sortPoints(ArrayList<Point> corners)  {
        Point[] ret = {
            removeOnePoint(corners, (a, b) -> Double.compare(a.x + a.y, b.x + b.y)), // top left
            removeOnePoint(corners, (a, b) -> Double.compare(b.x - b.y, a.x - a.y)), // top right
            removeOnePoint(corners, (a, b) -> Double.compare(b.x + b.y, a.x + a.y)), // bottom right
            removeOnePoint(corners, (a, b) -> Double.compare(a.x - a.y, b.x - b.y)) // bottom left
        };
        return ret;
    }

    private static Point removeOnePoint(ArrayList<Point> points, Comparator<Point> shouldPrefer) {
        int maxIndex = 0;
        Point maxPoint = points.get(maxIndex);

        for (int i=1; i<points.size(); i++) {
            Point point = points.get(i);
            if (shouldPrefer.compare(maxPoint, point) > 0) {
                maxIndex = i;
                maxPoint = point;
            }
        }

        return points.remove(maxIndex);
    }

}
