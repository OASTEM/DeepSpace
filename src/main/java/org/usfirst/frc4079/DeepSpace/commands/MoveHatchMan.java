/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4079.DeepSpace.commands;

import org.usfirst.frc4079.DeepSpace.Robot;
import org.usfirst.frc4079.DeepSpace.subsystems.HatchManV2;

import edu.wpi.first.wpilibj.command.Command;

public class MoveHatchMan extends Command {

  private HatchManV2 hatchManV2;
  private int position;

  // position : 0 indicates deploying, 1 indicates retracting
  public MoveHatchMan(int position, double timeout) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.hatchManV2);

    hatchManV2 = Robot.hatchManV2;
    this.position = position;
    setTimeout(timeout);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(position == 0){
      hatchManV2.push();
    }
    else if(position == 1){
      hatchManV2.pull();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(isTimedOut()){
      return true;
    }
    else if(position == 0 && !hatchManV2.getTopLimitSwitch()){
      return true;
    }
    else if(position == 1 && !hatchManV2.getBotLimitSwitch()){
      return true;
    } 
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    hatchManV2.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    hatchManV2.stop();
  }
}
