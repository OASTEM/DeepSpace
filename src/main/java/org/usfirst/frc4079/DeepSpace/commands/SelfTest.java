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
  private NetworkTableEntry hatchManVoltage;
  private NetworkTableEntry backDriveVoltage;
  private NetworkTableEntry leftMasterVoltage;
  private NetworkTableEntry rightMasterVoltage;
  private NetworkTableEntry leftSlaveVoltage;
  private NetworkTableEntry rightSlaveVoltage;

  public SelfTest() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    selfTest = Shuffleboard.getTab("SelfTest");
    hatchManVoltage = selfTest.add("HatchMan Voltage", 0.0).getEntry();
    backDriveVoltage = selfTest.add("BackDrive Voltage", 0.0).getEntry();
    rightMasterVoltage = selfTest.add("RightMaster Voltage", false).getEntry();
    leftMasterVoltage = selfTest.add("LeftMaster Voltage", false).getEntry();
    rightSlaveVoltage = selfTest.add("RightSlave Voltage", false).getEntry();
    leftSlaveVoltage = selfTest.add("LeftSlave Voltage", false).getEntry();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Robot.drivetrain.drivePercentOutput(0.05, 0.05);
    hatchManVoltage.setDouble(Robot.hatchManV2.getHatchManCurrent());
    backDriveVoltage.setDouble(Robot.climber.getBackDriveVoltage());
    rightMasterVoltage.setBoolean(Robot.drivetrain.getRightMaster());
    leftMasterVoltage.setBoolean(Robot.drivetrain.getLeftMaster());
    rightSlaveVoltage.setBoolean(Robot.drivetrain.getRightSlave());
    leftSlaveVoltage.setBoolean(Robot.drivetrain.getLeftSlave());
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
