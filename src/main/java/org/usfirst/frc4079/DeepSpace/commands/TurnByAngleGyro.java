/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4079.DeepSpace.commands;

import org.usfirst.frc4079.DeepSpace.Robot;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class TurnByAngleGyro extends Command {

  double goalAngle;
  double error;
  double error2;

  public TurnByAngleGyro(double goalAngle, double duration) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrain);

    this.goalAngle = goalAngle;
    error = goalAngle;
    error2 = goalAngle;

    setTimeout(duration);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
      Robot.drivetrain.resetAngle();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    error = goalAngle - (Robot.drivetrain.getAngle());
    double p = (error/Math.abs(goalAngle)) * .9;

    if (p > .25) {
      p = .25;
    }
    else if (p < -.25) {
      p = -.25;
    }

    Robot.drivetrain.drivePercentOutput(p, p);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    SmartDashboard.putNumber("ERROR", error);
    error2 = Math.abs(goalAngle) - Math.abs(Robot.drivetrain.getAngle());

    if (Math.abs(error2) <= 0.25) {
      return true;
    }

    SmartDashboard.putBoolean("Calibration", Robot.drivetrain.navX.isCalibrating());

    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.drivetrain.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
