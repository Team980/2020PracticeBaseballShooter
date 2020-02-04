/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.ConstantRateShooter;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterPID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrain driveTrain;
  private final ShooterPID shooter;

  private final Command teleopDriveCommand;

  private XboxController xBox;



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    xBox = new XboxController(0);

    driveTrain = new DriveTrain();
    shooter = new ShooterPID();
    teleopDriveCommand = new TeleopDrive(driveTrain);

    shooter.setDefaultCommand(new RunCommand(() -> {
      if (xBox.getTriggerAxis(Hand.kLeft) > 0){
        shooter.manual(applyDeadband(-xBox.getTriggerAxis(Hand.kLeft), .1));
      }
      else{
        shooter.manual(applyDeadband(xBox.getTriggerAxis(Hand.kRight), .1));
      }
    } , shooter));
    
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(xBox, Button.kA.value).whenPressed(new ConstantRateShooter(shooter, 300));
    new JoystickButton(xBox, Button.kB.value).whenPressed(new RunCommand(() -> {
      if (xBox.getTriggerAxis(Hand.kLeft) > 0){
        shooter.manual(applyDeadband(-xBox.getTriggerAxis(Hand.kLeft), .1));
      }
      else{
        shooter.manual(applyDeadband(xBox.getTriggerAxis(Hand.kRight), .1));
      }
    } , shooter));
    
  }

  /*public double getShooterRate() {
    return shooter.getActualRate();
  }*/

/*  public Command getShooterCommand() {
    return constantRateShooterCommand;
  }*/

  public Command getTeleopDriveCommand() {
    return teleopDriveCommand;
  }

    /**
   * Returns 0.0 if the given value is within the specified range around zero. The remaining range
   * between the deadband and 1.0 is scaled from 0.0 to 1.0.
   *
   * @param value    value to clip
   * @param deadband range around zero
   */
  public double applyDeadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } 
      else {
        return (value + deadband) / (1.0 - deadband);
      }
    } 
    else {
      return 0.0;
    }
    } 

}
