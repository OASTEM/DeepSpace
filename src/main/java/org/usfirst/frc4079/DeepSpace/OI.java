// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc4079.DeepSpace;

import org.usfirst.frc4079.DeepSpace.commands.*;
import org.usfirst.frc4079.DeepSpace.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    public LogitechGamingPad drivePad;
    public LogitechGamingPad payloadPad;

    public Joystick left;
    public Joystick right;

    public JoystickButton driveButtonA;
    public JoystickButton driveButtonB;
    public JoystickButton driveButtonX;
    public JoystickButton driveButtonY;
    public JoystickButton driveLeftBumper;
    public JoystickButton driveRightBumper;
    public JoystickButton driveButtonBack;
    public JoystickButton driveButtonStart;

    public JoystickButton payloadButtonA;
    public JoystickButton payloadButtonB;
    public JoystickButton payloadButtonX;
    public JoystickButton payloadButtonY;
    public JoystickButton payloadLeftBumper;
    public JoystickButton payloadRightBumper;
    public JoystickButton payloadButtonBack;
    public JoystickButton payloadButtonStart;

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        // SmartDashboard Buttons
      //  SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
      //  SmartDashboard.putData("GamepadDrive", new GamepadDrive());
      //  SmartDashboard.putData("Climb13", new Climb13());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        
        drivePad = new LogitechGamingPad(0);
        payloadPad = new LogitechGamingPad(1);

        left = new Joystick(0);
        right = mew Joystick(1);

        // DRIVE CONTROLLER

        driveButtonA = new JoystickButton(drivePad, 1);
        driveButtonA.whenPressed(new DriveDistanceVision(Constants.cameraToFront, 1, 10));

        driveButtonB = new JoystickButton(drivePad, 2);
        driveButtonB.whenPressed(new DriveDistance(50, 2));

        driveButtonY = new JoystickButton(drivePad, 4);
        driveButtonY.whenPressed(new GamepadDriveSlowMode());

        driveButtonX = new JoystickButton(drivePad, 3);

        driveLeftBumper = new JoystickButton(drivePad, 5);

        driveRightBumper = new JoystickButton(drivePad, 6);
        driveRightBumper.whenPressed(new GamepadDrive());

        driveButtonBack = new JoystickButton(drivePad, 7);

        driveButtonStart = new JoystickButton(drivePad, 8);

        // PAYLOAD CONTROLLER

        payloadButtonA = new JoystickButton(payloadPad, 1);
        payloadButtonA.whenPressed(new DisableClimber());

        payloadButtonB = new JoystickButton(payloadPad, 2);
        payloadButtonB.whileHeld(new PullBackLeg(2.5));

        payloadButtonX = new JoystickButton(payloadPad, 3);
        payloadButtonX.whenPressed(new MoveHatchMan(0, 2));

        payloadButtonY = new JoystickButton(payloadPad, 4);
        payloadButtonY.whenPressed(new MoveHatchMan(1, 2));

        payloadLeftBumper = new JoystickButton(payloadPad, 5);
        payloadLeftBumper.whileHeld(new PushBothLegs(2.5));

        payloadRightBumper = new JoystickButton(payloadPad, 6);

        payloadButtonBack = new JoystickButton(payloadPad, 7);

        payloadButtonStart = new JoystickButton(payloadPad, 8);
        payloadButtonStart.whenPressed(new GamepadRunClimber());
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
 

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public LogitechGamingPad getpayloadPad() {
        return payloadPad;
    }
    public LogitechGamingPad getdrivePad() {
        return drivePad;
    }
}

