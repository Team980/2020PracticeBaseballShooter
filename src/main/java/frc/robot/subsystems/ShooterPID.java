/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterPID extends SubsystemBase {
  private PIDSpeedControllerGroup speedController;
  private Spark leftShooter;
  private Spark rightShooter;
  private Encoder shooterEncoder;
  private double[] PIDF = {.5 , 0 , .0005 , .6};
  private double maxVel = 100;

  /**
   * Creates a new ShooterPID.
   */
  public ShooterPID() {
    leftShooter = new Spark(Constants.LEFT_SHOOTER_SPARK_CHANNEL);
    rightShooter = new Spark(Constants.RIGHT_SHOOTER_SPARK_CHANNEL);

    shooterEncoder = new Encoder(Constants.SHOOTER_ENCODER_CHANNEL_A , Constants.SHOOTER_ENCODER_CHANNEL_B);

    shooterEncoder.setReverseDirection(true);
    shooterEncoder.setDistancePerPulse(1.0/360);

    speedController = new PIDSpeedControllerGroup(shooterEncoder , maxVel , PIDF , leftShooter , rightShooter);


  }

  public void fire(double targetRPM){
    speedController.setRPM(targetRPM);
  }

  public void manual(double throttle){
    leftShooter.set(throttle);
    rightShooter.set(throttle);
  }

  public void enablePID(){
    speedController.enable();
  }

  public void disablePID(){
    speedController.disable();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("RPM", shooterEncoder.getRate() * 60);
    SmartDashboard.putNumber("Rotations", shooterEncoder.getDistance());
    // This method will be called once per scheduler run
  }
}
