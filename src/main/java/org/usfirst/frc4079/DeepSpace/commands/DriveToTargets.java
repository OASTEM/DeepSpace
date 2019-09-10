/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4079.DeepSpace.commands;

import org.usfirst.frc4079.DeepSpace.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToTargets extends CommandGroup {
  /**
   * Add your docs here.
   */
  public DriveToTargets() {
    addSequential(new DriveDistanceVision(Constants.cameraToFront, 1, 10));
    addSequential(new DriveDistance(10, 0.3));
  }
}
