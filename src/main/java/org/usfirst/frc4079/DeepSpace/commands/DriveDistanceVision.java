    // RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4079.DeepSpace.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc4079.DeepSpace.Constants;

import org.usfirst.frc4079.DeepSpace.Robot;

/**
 *
 */
public class DriveDistanceVision extends Command {
      private double distance;
      private double subtraction;
      private int divisor;
      private double timeout;
      private double leftPosition, rightPosition;
      private boolean targetNotVisible = false;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public DriveDistanceVision(double subtraction, int divisor, double duration) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.drivetrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        this.subtraction = subtraction;
        this.divisor = divisor;
        this.timeout = duration;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
      if (Robot.getDistance() != -1) {
        this.distance = (Robot.getDistance() - subtraction) / divisor;
        //SmartDashboard.putNumber("Commanded Distance", this.distance);
        leftPosition=Robot.drivetrain.getLeftDistance();
    	rightPosition=Robot.drivetrain.getRightDistance();
        Robot.drivetrain.configureForDrive();
        
        setTimeout(timeout);
      } else {
        targetNotVisible = true;
      }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.getDistance() != -1) {
            double centerX = Robot.getCenterX();
            double deltaX = Constants.cameraCenter - centerX;

            SmartDashboard.putNumber("Delta X", deltaX);

            double visionKRight = -(Constants.kTargetDistanceKp * (Robot.getDistance() - subtraction)) - (Constants.kTargetDeltaXKp * deltaX);        
            double visionKLeft = (Constants.kTargetDistanceKp * (Robot.getDistance() - subtraction)) - (Constants.kTargetDeltaXKp * deltaX);

            SmartDashboard.putNumber("Left Vision K:", visionKLeft);
            SmartDashboard.putNumber("Right Vision K:", visionKRight);

            Robot.drivetrain.drivePercentOutput(visionKLeft, visionKRight);
        } else {
            SmartDashboard.putNumber("Left Vision K:", 0);
            SmartDashboard.putNumber("Right Vision K:", 0);
            Robot.drivetrain.drivePercentOutput(0, 0);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        /*
        if (targetNotVisible) {
            SmartDashboard.putBoolean("Target Not Visible: ", true);
            return true;
        } SmartDashboard.putBoolean("Target Not Visible: ", false);
        */
        
        if (Math.abs(Robot.oi.drivePad.getLeftAnalogY()) > 0.25 || Math.abs(Robot.oi.drivePad.getRightAnalogY()) > 0.25) {
            SmartDashboard.putBoolean("Manual Override: ", true);
            return true;
        }   SmartDashboard.putBoolean("Manual Override: ", false);

        if (Math.abs((Robot.getDistance() - subtraction) / divisor) < Constants.maxAutoError) {
            SmartDashboard.putBoolean("Reached Point: ", true);
            return true;
        }   SmartDashboard.putBoolean("Reached Point: ", false);

        SmartDashboard.putBoolean("Timed Out: ", isTimedOut());
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
    	end();
    }
}
