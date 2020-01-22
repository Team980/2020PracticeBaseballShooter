/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import frc.robot.Util;
import static frc.robot.Constants.*;

import java.util.Arrays;
import java.util.Collections;

public class InnerPowerPortDistanceEstimator {
    public static double estimateDistance(double[] cornerYs) { 
        //double bottomY = Collections.min(Arrays.asList(cornerYs));
        double bottomY = Double.POSITIVE_INFINITY;
        for (double cornerY : cornerYs) {
            if (cornerY < bottomY) {
                bottomY = cornerY;
            }
        }

        double bottomDeflectionAngle = Util.map(bottomY, LIMELIGHT_VERTICAL_FOV_PIXELS, 0.0, -LIMELIGHT_VERTICAL_FOV_RADIANS/2, LIMELIGHT_VERTICAL_FOV_RADIANS/2);

        // https://docs.limelightvision.io/en/latest/cs_estimating_distance.html

        return (POWER_PORT_TARGET_LOWEST_POINT_FEET - LIMELIGHT_MOUNTING_HEIGHT_FEET)
            / Math.tan(bottomDeflectionAngle + LIMELIGHT_MOUNTING_ELEVATION_RADIANS);
    }
}
