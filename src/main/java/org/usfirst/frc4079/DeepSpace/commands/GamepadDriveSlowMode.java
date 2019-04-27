/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4079.DeepSpace.commands;

import org.usfirst.frc4079.DeepSpace.Robot;
import org.usfirst.frc4079.DeepSpace.Constants;
import edu.wpi.first.wpilibj.command.Command;

public class GamepadDriveSlowMode extends Command {
  public GamepadDriveSlowMode() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
            //Robot.drivetrain.drivePercentOutput((-Robot.oi.drivePad.getLeftAnalogY()*Constants.kMaxDrivePower)/2, (Robot.oi.drivePad.getRightAnalogY()*Constants.kMaxDrivePower)/2);
            Robot.drivetrain.drivePercentOutput(-Robot.oi.left.getY()*Constants.kMaxDrivePower*0.5, Robot.oi.right.getY()*Constants.kMaxDrivePower*0.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
