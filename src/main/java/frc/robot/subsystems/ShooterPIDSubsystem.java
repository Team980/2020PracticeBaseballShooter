/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;

public class ShooterPIDSubsystem extends PIDSubsystem {
  private Spark leftMotor;
  private Spark rightMotor;
  private Encoder shootEncoder;
  private SimpleMotorFeedforward shootFF;
  /**
   * Creates a new ShooterPIDSubsystem.
   */
  public ShooterPIDSubsystem() {
    super(
        // The PIDController used by the subsystem
        new PIDController(Constants.ShooterP, Constants.ShooterI, Constants.ShooterD));
    getController().setTolerance(1);//rotations per second

    leftMotor = new Spark(Constants.LEFT_SHOOTER_SPARK_CHANNEL);
    rightMotor = new Spark(Constants.RIGHT_SHOOTER_SPARK_CHANNEL);

    shootEncoder = new Encoder(Constants.SHOOTER_ENCODER_CHANNEL_A, Constants.SHOOTER_ENCODER_CHANNEL_B, true, Encoder.EncodingType.k4X);
    shootEncoder.setDistancePerPulse(Constants.SHOOTER_ENCODER_DISTANCE_PER_PULSE);

    shootFF = new SimpleMotorFeedforward(Constants.kSVolts, Constants.kVVoltSecondsPerRotation);
  }

  @Override
  public void useOutput(double output, double setpoint) {
    leftMotor.setVoltage(output + shootFF.calculate(setpoint));
    rightMotor.setVoltage(output + shootFF.calculate(setpoint));
    // Use the output here
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return shootEncoder.getRate();
  }

  public void manual(double throttle){
    leftMotor.set(throttle);
    rightMotor.set(throttle);
  }

  public void fire(double targetRPS){
    setSetpoint(targetRPS);
  }

  public Encoder getEncoder(){
    return shootEncoder;
  }
}
