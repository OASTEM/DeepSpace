/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4079.DeepSpace.commands;

import org.usfirst.frc4079.DeepSpace.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class SelfTest extends Command {

  private ShuffleboardTab selfTest;
  private NetworkTableEntry driveTrainVoltage;
  private NetworkTableEntry hatchManVoltage;
  private NetworkTableEntry backDriveVoltage;

  public SelfTest() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    selfTest = Shuffleboard.getTab("SelfTest");
    driveTrainVoltage = selfTest.add("DriveTrain Voltage", 0.0).getEntry();
    hatchManVoltage = selfTest.add("HatchMan Voltage", 0.0).getEntry();
    backDriveVoltage = selfTest.add("BackDrive Voltage", 0.0).getEntry();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    driveTrainVoltage.setString(Robot.drivetrain.getDrivetrainVoltage());
    hatchManVoltage.setDouble(Robot.hatchManV2.getHatchManVoltage());
    backDriveVoltage.setDouble(Robot.climber.getBackDriveVoltage());
    //Robot.drivetrain.getDrivetrainVoltage();
    //Robot.hatchManV2.getHatchManVoltage();
    //Robot.climber.getBackDriveVoltage();
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
