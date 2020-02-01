/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Brumble;
import frc.robot.commands.ConstantRateShooter;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.vision.OffsetCalculator;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final XboxController xbox = new XboxController(0);

  private final DriveTrain driveTrain = new DriveTrain();
  private final Shooter shooter = new Shooter();

  private final Limelight limelight = new Limelight();

  private final Command constantRateShooterCommand = new ConstantRateShooter(shooter, 400);

  private final Command teleopDriveCommand = new TeleopDrive(driveTrain);

  private final Command brumbleCommand = new Brumble(driveTrain, limelight);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
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

    new JoystickButton(xbox, 1).whenPressed(brumbleCommand);
    new JoystickButton(xbox, 2).cancelWhenPressed(brumbleCommand);

  }



  public Command getShooterCommand() {
    return constantRateShooterCommand;
  }

  public Command getTeleopDriveCommand() {
    return teleopDriveCommand;
  }

}
