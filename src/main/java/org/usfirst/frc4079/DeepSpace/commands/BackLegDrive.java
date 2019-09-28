/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4079.DeepSpace.commands;

import org.usfirst.frc4079.DeepSpace.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class BackLegDrive extends Command {

  private double timeout; 
  public BackLegDrive(double timeout) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.timeout = timeout;
    requires(Robot.climber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setTimeout(timeout);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(AutoClimb.isDisabled) {

    }
    else {
      Robot.climber.drive(0.5, -0.5);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return AutoClimb.isDisabled || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.climber.drive(0.0, 0.0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    AutoClimb.isDisabled = true;
    Robot.climber.drive(0.0, 0.0);
  }
}
