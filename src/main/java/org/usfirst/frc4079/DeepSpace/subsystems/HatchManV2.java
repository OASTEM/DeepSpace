/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4079.DeepSpace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */

public class HatchManV2 extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private TalonSRX finger;
  private DigitalInput topLimitSwitch;
  private DigitalInput botLimitSwitch;

  public HatchManV2() {
    finger = new TalonSRX(4);
    //topLimitSwitch = new DigitalInput(0);
    //botLimitSwitch = new DigitalInput(1);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void push() {
    finger.set(ControlMode.PercentOutput, 0.5);
  }

  public void pull() {
    finger.set(ControlMode.PercentOutput, -0.5);
  }

  public void stop() {
    finger.set(ControlMode.PercentOutput, 0.0);
  }

  public boolean getTopLimitSwitch() {
    return topLimitSwitch.get();
  }

  public boolean getBotLimitSwitch() {
    return botLimitSwitch.get();
  }
}
