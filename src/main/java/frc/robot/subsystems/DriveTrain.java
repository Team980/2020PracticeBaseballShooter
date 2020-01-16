/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new DriveTrain.
   */

  DifferentialDrive drive;

  public DriveTrain() {
    Spark leftDrive = new Spark(Constants.LEFT_DRIVE_SPARK_CHANNEL);
    Spark rightDrive = new Spark(Constants.RIGHT_DRIVE_SPARK_CHANNEL);
    drive = new DifferentialDrive(leftDrive, rightDrive);
  }

  public void arcadeDrive(double forward, double turn) {
    drive.arcadeDrive(forward, turn);
  }

  public void stopMotor() {
    drive.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
