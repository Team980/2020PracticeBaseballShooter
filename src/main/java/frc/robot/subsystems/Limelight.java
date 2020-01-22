/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.Arrays;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.vision.InnerPowerPortDistanceEstimator;

public class Limelight extends SubsystemBase {
  private NetworkTable table;

  public Limelight() {
    table = NetworkTableInstance.getDefault().getTable("limelight");
  }

  public double[] getCornerXs() {
    return table.getEntry("tcornx").getDoubleArray(new double[0]);
  }

  public double[] getCornerYs() {
    return table.getEntry("tcorny").getDoubleArray(new double[0]); 
  }

  @Override
  public void periodic() {
    NetworkTableInstance.getDefault().getTable("debug").getEntry("target distance").setNumber(InnerPowerPortDistanceEstimator.estimateDistance(getCornerYs()));
  }
}
