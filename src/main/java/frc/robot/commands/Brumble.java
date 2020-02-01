/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.Map;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Util;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;

public class Brumble extends CommandBase {
    private DriveTrain driveTrain;
    private Limelight limelight;
    
    private static final double TURN_SPEED = 1.0;

    private PIDController pidController = new PIDController(0, 3, 0);

    public Brumble(DriveTrain driveTrain, Limelight limelight) {
        this.driveTrain = driveTrain;
        this.limelight = limelight;

        addRequirements(driveTrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double offset = limelight.xOffset();
        //double offset = limelight.getOffset();
        if (isReasonable(offset)) {
            //double turn = TURN_SPEED * Math.signum(offset);
            double turn = pidController.calculate(Util.map(offset, -27, 27, 1.0, -1.0), 0);

            driveTrain.arcadeDrive(0, turn);
            System.out.println(turn);
        } else {
            driveTrain.arcadeDrive(0, 0);
        }

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        driveTrain.stopMotor();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    private static boolean isReasonable(double offset) {
        return -200 < offset && offset < 200;
    }
}
