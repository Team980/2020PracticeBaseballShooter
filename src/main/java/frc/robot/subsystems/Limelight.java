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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.vision.InnerPowerPortDistanceEstimator;
import frc.robot.vision.OffsetCalculator;

public class Limelight extends SubsystemBase {
  private NetworkTable table;
  private double offset;

  public Limelight() {
    table = NetworkTableInstance.getDefault().getTable("limelight");
  }

  public double xOffset() {
    return table.getEntry("tx").getDouble(0);
  }

  public double[] getCornerXs() {
    return table.getEntry("tcornx").getDoubleArray(new double[0]);
  }

  public double[] getCornerYs() {
    return table.getEntry("tcorny").getDoubleArray(new double[0]); 
  }

  public double getOffset() {
    return offset;
  }


  private void calculateOffset() {
    try {
      offset = OffsetCalculator.getTargetHorizontalOffset(getCornerXs(), getCornerYs());
    } catch (Exception e) {
    }
  }

  @Override
  public void periodic() {
    calculateOffset();
    SmartDashboard.putNumber("offset thingy", offset);
    SmartDashboard.putBoolean("valid", true);

    NetworkTableInstance.getDefault().getTable("debug").getEntry("target distance").setNumber(InnerPowerPortDistanceEstimator.estimateDistance(getCornerYs()));
  }
}
