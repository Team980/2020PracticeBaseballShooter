/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;

public class PIDSpeedControllerGroup extends PIDSubsystem implements SpeedController, Sendable, AutoCloseable {
  private SimpleMotorFeedforward ff;
  private Encoder encoder;

  private boolean m_isInverted;
  private SpeedController[] m_speedControllers;
  private static int instances;
  private double[] PIDF;
  private double maxVel;

  /**
   * Create a new SpeedControllerGroup with the provided SpeedControllers.
   *
   * @param speedControllers The SpeedControllers to add
   */
  @SuppressWarnings("PMD.AvoidArrayLoops")

  /**
   * Creates a new PIDSpeedControllerGroup.
   */
  public PIDSpeedControllerGroup(Encoder encoder , double maxVel , double[] PIDF , SpeedController speedController, SpeedController... speedControllers) {
    super(
        // The PIDController used by the subsystem
        new PIDController(PIDF[0], PIDF[1], PIDF[2]));

        ff = new SimpleMotorFeedforward(0, PIDF[3]);

    this.encoder = encoder;
    this.maxVel = maxVel;

    
    m_speedControllers = new SpeedController[speedControllers.length + 1];
    m_speedControllers[0] = speedController;
    SendableRegistry.addChild(this, speedController);
    for (int i = 0; i < speedControllers.length; i++) {
      m_speedControllers[i + 1] = speedControllers[i];
      SendableRegistry.addChild(this, speedControllers[i]);
    }
    instances++;
    SendableRegistry.addLW(this, "tSpeedControllerGroup", instances);

  }
//PID subsystem methods
  @Override
  public void useOutput(double output, double setpoint) {
    // Use the output here
    //switched set to setvoltage, changed speed to the pid output, added possible feed forward, and moved this from the set method
    for (SpeedController speedController : m_speedControllers) {
      speedController.setVoltage(m_isInverted ? -(output + ff.calculate(setpoint)) : (output + ff.calculate(setpoint)));
    
      //speedController.setVoltage(m_isInverted ? -output : output);

    }
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return encoder.getRate();
  }

//SpeedControllerGroup methods
  @Override
  public void close() {
    SendableRegistry.remove(this);
  }
  @Override
  public void set(double speed) {
    setSetpoint(speed * maxVel);// in place of below, made this call setsetpoint.  theoretically will take arcade drive inputs and run them through PID


    /*for (SpeedController speedController : m_speedControllers) {
      speedController.set(m_isInverted ? -speed : speed);
    }*/
  }

  public void setRPM(double targetRPM){
    setSetpoint(targetRPM / 60);
  }

  @Override
  public double get() {
    if (m_speedControllers.length > 0) {
      return m_speedControllers[0].get() * (m_isInverted ? -1 : 1);
    }
    return 0.0;
  }

  @Override
  public void setInverted(boolean isInverted) {
    m_isInverted = isInverted;
  }

  @Override
  public boolean getInverted() {
    return m_isInverted;
  }

  @Override
  public void disable() {
    for (SpeedController speedController : m_speedControllers) {
      speedController.disable();
    }
  }

  @Override
  public void stopMotor() {
    setSetpoint(0);
  /*  for (SpeedController speedController : m_speedControllers) {
      speedController.stopMotor();
    }*/
  }

  @Override
  public void pidWrite(double output) {
    set(output);
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Speed Controller");
    builder.setActuator(true);
    builder.setSafeState(this::stopMotor);
    builder.addDoubleProperty("Value", this::get, this::set);
  }

}
