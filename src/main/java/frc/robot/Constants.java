/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final int 
        LEFT_DRIVE_SPARK_CHANNEL = 1,
        RIGHT_DRIVE_SPARK_CHANNEL = 0,

        LEFT_SHOOTER_SPARK_CHANNEL = 2,
        RIGHT_SHOOTER_SPARK_CHANNEL = 3,
        XBOX_CONTROLLER_PORT_INDEX = 0,

        SHOOTER_ENCODER_CHANNEL_A = 0,
        SHOOTER_ENCODER_CHANNEL_B = 1;

    public static final double SHOOTER_ENCODER_DISTANCE_PER_PULSE = 60.0 / 2048.0; // for rpm

    public static final double POWER_PORT_TARGET_LOWEST_POINT_FEET = 6.8125;
    public static final double LIMELIGHT_MOUNTING_HEIGHT_FEET = 3.7;
    public static final double LIMELIGHT_MOUNTING_ELEVATION_RADIANS = Util.degreesToRadians(45); 

    public static final double LIMELIGHT_HORIZONTAL_FOV_PIXELS = 320;
    public static final double LIMELIGHT_VERTICAL_FOV_PIXELS = 240;
    public static final double LIMELIGHT_VERTICAL_FOV_RADIANS = Util.degreesToRadians(41);
    public static final double LIMELIGHT_HORIZONTAL_FOV_RADIANS = Util.degreesToRadians(54);

        
}
