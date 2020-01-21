/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import frc.robot.Util;

/**
 * Add your docs here.
 */
public class InnerPowerPortDistanceEstimator {

    private static final double POWER_PORT_TARGET_LOWEST_POINT_FEET = 6.8125;
    private static final double LIMELIGHT_MOUNTING_HEIGHT_FEET = 3.5;
    private static final double LIMELIGHT_MOUNTING_ELEVATION_RADIANS = Util.degreesToRadians(45); 

    private static final double LIMELIGHT_VERTICAL_FOV_PIXELS = 240;
    private static final double LIMELIGHT_VERTICAL_FOV_RADIANS = Util.degreesToRadians(41);
    private static final double LIMELIGHT_HORIZONTAL_FOV_RADIANS = Util.degreesToRadians(54);


    public static double estimateDistance(double[] cornerYs) { 
        double bottomY = 0.0;
        for (double cornerY : cornerYs) {
            if (cornerY < bottomY) {
                bottomY = cornerY;
            }
        }

        double bottomDeflectionAngle = Util.map(bottomY, LIMELIGHT_VERTICAL_FOV_PIXELS, 0.0, -LIMELIGHT_VERTICAL_FOV_RADIANS/2, LIMELIGHT_VERTICAL_FOV_RADIANS/2);

        // https://docs.limelightvision.io/en/latest/cs_estimating_distance.html

        return (POWER_PORT_TARGET_LOWEST_POINT_FEET-LIMELIGHT_MOUNTING_HEIGHT_FEET)
            / Math.tan(bottomDeflectionAngle + LIMELIGHT_MOUNTING_ELEVATION_RADIANS);
    }

}
