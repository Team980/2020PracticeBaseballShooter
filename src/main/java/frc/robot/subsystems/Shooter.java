/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  PIDController pidController;

  SpeedControllerGroup speedController;
  Encoder shooterEncoder;

  public Shooter() {
    speedController = new SpeedControllerGroup(
      new Spark(Constants.LEFT_SHOOTER_SPARK_CHANNEL), 
      new Spark(Constants.RIGHT_SHOOTER_SPARK_CHANNEL)
    );

    shooterEncoder = new Encoder(
      Constants.SHOOTER_ENCODER_CHANNEL_A, 
      Constants.SHOOTER_ENCODER_CHANNEL_B
    );

    shooterEncoder.setReverseDirection(true);
    shooterEncoder.setDistancePerPulse(Constants.SHOOTER_ENCODER_DISTANCE_PER_PULSE);

    pidController = new PIDController(0.1, 0, 0.0005);
  }

  public double getActualRate() {
    return shooterEncoder.getRate();
  }

  public void setTargetRate(double rate) {
    pidController.setSetpoint(rate);
  }

  public void run() {
    double output = pidController.calculate(shooterEncoder.getRate());
    speedController.set(output);
  }

  public void stopMotors() {
    speedController.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
