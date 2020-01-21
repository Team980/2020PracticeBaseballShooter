/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class Util {
    public static double degreesToRadians(double degrees) {
        return degrees * Math.PI/180;
    }

    public static double map(double x, double inMin, double inMax, double outMin, double outMax) {
        // this function is the spicy sauce
        // https://www.arduino.cc/reference/en/language/functions/math/map/
        return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }
}
