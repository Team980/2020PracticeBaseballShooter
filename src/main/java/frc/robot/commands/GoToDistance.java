/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Lidar;

public class GoToDistance extends CommandBase {
  /**
   * Creates a new GoToDistance.
   */
  private Lidar lidar;
  private DriveTrain driveTrain;
  private double targetDistance;

  private static final double GO_TO_DISTANCE_TOLERANCE_FEET = 0.5;

  public GoToDistance(Lidar lidar, DriveTrain driveTrain, double targetDistance) {
    // Use addRequirements() here to declare subsystem dependencies.\
    this.lidar = lidar;
    this.driveTrain = driveTrain;
    this.targetDistance = targetDistance;
    addRequirements(driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double forward = 0;

    if (lidar.isValid()) {
      double error = error();

      if (error > 0) {
        forward = 0.7;
      } else {
        forward = -0.7;
      }

      System.out.println("hello " + forward);
    }

    driveTrain.arcadeDrive(forward, 0);
  }

  private double error() {
    return lidar.getDistanceFeet() - targetDistance;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.stopMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(error()) < GO_TO_DISTANCE_TOLERANCE_FEET;
  }
}
