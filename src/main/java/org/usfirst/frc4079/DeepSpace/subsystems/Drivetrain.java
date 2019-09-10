// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4079.DeepSpace.subsystems;

import org.usfirst.frc4079.DeepSpace.commands.*;
import org.usfirst.frc4079.DeepSpace.Constants;
import org.usfirst.frc4079.DeepSpace.motion.MotionProfileManager;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI.Port;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;


public class Drivetrain extends Subsystem implements PIDOutput {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private TalonSRX leftMaster;
    private TalonSRX leftSlave;
    private TalonSRX rightMaster;
    private TalonSRX rightSlave;
    private AHRS navX;
    private PIDController turnController;
    private double turnMotorInput;
    
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public Drivetrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        leftMaster = new TalonSRX(0); // 0 for 2019, 1 for 2018
        leftSlave = new TalonSRX(2);  // 2 for 2019, 2 for 2018
        rightMaster = new TalonSRX(3); // 3 for 2019, 3 for 2018
        rightSlave = new TalonSRX(1);  // 1 for 2019, 0 for 2018
        navX = new AHRS(Port.kMXP, (byte)50);
        addChild(navX);
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		
		//FeedbackDevice encoder = FeedbackDevice.CTRE_MagEncoder_Relative;
		FeedbackDevice encoder = FeedbackDevice.QuadEncoder;
        rightMaster.configSelectedFeedbackSensor(encoder, 0, Constants.kTimeoutMs); //timeout is 10 msec
		leftMaster.configSelectedFeedbackSensor(encoder, 0, Constants.kTimeoutMs); 
		
		
		

		if (rightSlave != null)
			rightSlave.set(ControlMode.Follower, rightMaster.getDeviceID());
		if (leftSlave != null) 
            leftSlave.set(ControlMode.Follower, leftMaster.getDeviceID());
            
        turnController = new PIDController(0.008, 0.0, 0, navX, this);
        turnController.setAbsoluteTolerance(2.0);
        
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        setDefaultCommand(new GamepadDrive());
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void drivePercentOutput(double left, double right) {
    	leftMaster.set(ControlMode.PercentOutput, left);
    	rightMaster.set(ControlMode.PercentOutput, right);
    }
    
    public void driveToTarget (double left_inches, double right_inches) {
    	double left_target = getNativeUnitsFromInches(left_inches);
        double right_target = getNativeUnitsFromInches(right_inches);
        
        leftMaster.set(ControlMode.MotionMagic, left_target);
		 rightMaster.set(ControlMode.MotionMagic, right_target);
		
		//leftMaster.set(ControlMode.Position, left_target);
        //rightMaster.set(ControlMode.Position, right_target);
    }
	public void driveByProfile (MotionProfileManager leftProfile, MotionProfileManager rightProfile) {
    	leftProfile.control();
    	rightProfile.control();
    	
    	SetValueMotionProfile setLeftOutput = leftProfile.getSetValue();
		leftMaster.set(ControlMode.MotionProfile, setLeftOutput.value);
    	SetValueMotionProfile setRightOutput = leftProfile.getSetValue();
		rightMaster.set(ControlMode.MotionProfile, setRightOutput.value);
    	
    }
    public void turnByAngle (double angle) {
        double delta_distance = getNativeUnitsFromInches(angle/180*Math.PI*Constants.kTrackWidthInches/2);
        double left_position = leftMaster.getSelectedSensorPosition(0);
        double right_position = rightMaster.getSelectedSensorPosition(0);
        leftMaster.set(ControlMode.Position, left_position+delta_distance);
        rightMaster.set(ControlMode.Position, right_position+delta_distance);
    }
	
    public void turnToAngle (double angle) {
    	double current_angle = navX.getAngle();
    	double delta_angle = angle-current_angle;
    	turnByAngle(delta_angle);
	}
	
	public void turnToAnglePID (double angle){
		turnController.setSetpoint(angle);
		leftMaster.set(ControlMode.PercentOutput, turnMotorInput);
		rightMaster.set(ControlMode.PercentOutput, turnMotorInput);
	}
    private double getNativeUnitsFromInches (double inches) {
    	return inches*Constants.kSensorUnitsPerRotation/Constants.kDriveWheelDiameterInches/Math.PI;
    }
    
    private double getInchesFromNativeUnits (double native_units) {
    	return native_units/Constants.kSensorUnitsPerRotation*Constants.kDriveWheelDiameterInches*Math.PI;
    }
    
    public void stop() {
    	drivePercentOutput(0.0,0.0);
    }

    public synchronized void resetEncoders() {
    	leftMaster.getSensorCollection().setQuadraturePosition(0, 10);
    	leftMaster.setSelectedSensorPosition(0, 0, 10);
    	rightMaster.getSensorCollection().setQuadraturePosition(0, 10);
    	rightMaster.setSelectedSensorPosition(0, 0, 10);
    	leftSlave.setSelectedSensorPosition(0, 0, 10);
    	rightSlave.setSelectedSensorPosition(0, 0, 10);
    	navX.reset();
    	
    }
	
	public synchronized void initTalons(){
		// Set General Parameters
		rightMaster.setSensorPhase(true); // 2019
		leftMaster.setSensorPhase(true); // 2019

		//rightMaster.setSensorPhase(false); // 2018
		//leftMaster.setSensorPhase(false); // 2018

		leftMaster.configClosedloopRamp(Constants.kClosedLoopRampRate, Constants.kTimeoutMs);
		leftMaster.configOpenloopRamp(Constants.kOpenLoopRampRate, Constants.kTimeoutMs);
		rightMaster.configClosedloopRamp(Constants.kClosedLoopRampRate, Constants.kTimeoutMs);
		rightMaster.configOpenloopRamp(Constants.kOpenLoopRampRate, Constants.kTimeoutMs);
		leftSlave.configClosedloopRamp(Constants.kClosedLoopRampRate, Constants.kTimeoutMs);
		leftSlave.configOpenloopRamp(Constants.kOpenLoopRampRate, Constants.kTimeoutMs);
		rightSlave.configClosedloopRamp(Constants.kClosedLoopRampRate, Constants.kTimeoutMs);
		rightSlave.configOpenloopRamp(Constants.kOpenLoopRampRate, Constants.kTimeoutMs);

		leftMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, Constants.kStatus_13_TimeMs, Constants.kTimeoutMs);
		leftMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, Constants.kStatus_10_TimeMs, Constants.kTimeoutMs);
		leftMaster.configMotionProfileTrajectoryPeriod(Constants.kBaseTrajPeriodMs, Constants.kTimeoutMs);

		rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, Constants.kStatus_13_TimeMs, Constants.kTimeoutMs);
		rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, Constants.kStatus_10_TimeMs, Constants.kTimeoutMs);
		rightMaster.configMotionProfileTrajectoryPeriod(Constants.kBaseTrajPeriodMs, Constants.kTimeoutMs);

		leftMaster.configPeakOutputForward(1, Constants.kTimeoutMs);
		leftMaster.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		leftMaster.configNominalOutputForward(0, Constants.kTimeoutMs);
		leftMaster.configNominalOutputReverse(0, Constants.kTimeoutMs);

		rightMaster.configPeakOutputForward(1, Constants.kTimeoutMs);
		rightMaster.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		rightMaster.configNominalOutputForward(0, Constants.kTimeoutMs);
		rightMaster.configNominalOutputReverse(0, Constants.kTimeoutMs);
		// Set Parameters For Driving Straight
		rightMaster.config_kP(Constants.kSlotStraight, Constants.kDriveStraightKp, Constants.kTimeoutMs);
		rightMaster.config_kI(Constants.kSlotStraight, Constants.kDriveStraightKi, Constants.kTimeoutMs);
		rightMaster.config_kD(Constants.kSlotStraight, Constants.kDriveStraightKd, Constants.kTimeoutMs);
		rightMaster.config_kF(Constants.kSlotStraight, Constants.kDriveStraightKf, Constants.kTimeoutMs);
		rightMaster.config_IntegralZone(Constants.kSlotStraight, Constants.kDriveStraightIZone, Constants.kTimeoutMs);
		
		rightMaster.setIntegralAccumulator(0, 0, Constants.kTimeoutMs);
		
		rightMaster.configClosedLoopPeakOutput(Constants.kSlotStraight, Constants.kDriveStraightPeakOutput, Constants.kTimeoutMs);
		
		rightMaster.configMotionCruiseVelocity(Constants.kDriveStraightCruiseVelocity, Constants.kTimeoutMs);
		rightMaster.configMotionAcceleration(Constants.kDriveStraightAcceleration, Constants.kTimeoutMs);
		
		leftMaster.config_kP(Constants.kSlotStraight, Constants.kDriveStraightKp, Constants.kTimeoutMs);
		leftMaster.config_kI(Constants.kSlotStraight, Constants.kDriveStraightKi, Constants.kTimeoutMs);
		leftMaster.config_kD(Constants.kSlotStraight, Constants.kDriveStraightKd, Constants.kTimeoutMs);
		leftMaster.config_kF(Constants.kSlotStraight, Constants.kDriveStraightKf, Constants.kTimeoutMs);
		leftMaster.config_IntegralZone(Constants.kSlotStraight, Constants.kDriveStraightIZone, Constants.kTimeoutMs);
		
		leftMaster.setIntegralAccumulator(0, 0, Constants.kTimeoutMs);

		leftMaster.configClosedLoopPeakOutput(Constants.kSlotStraight, Constants.kDriveStraightPeakOutput, Constants.kTimeoutMs);

		leftMaster.configMotionCruiseVelocity(Constants.kDriveStraightCruiseVelocity, Constants.kTimeoutMs);
		leftMaster.configMotionAcceleration(Constants.kDriveStraightAcceleration, Constants.kTimeoutMs);

        // Set Parameters For Turn
		rightMaster.config_kP(Constants.kSlotTurn, Constants.kDriveTurnKp, Constants.kTimeoutMs);
 		rightMaster.config_kI(Constants.kSlotTurn, Constants.kDriveTurnKi, Constants.kTimeoutMs);
 		rightMaster.config_kD(Constants.kSlotTurn, Constants.kDriveTurnKd, Constants.kTimeoutMs);
 		rightMaster.config_kF(Constants.kSlotTurn, Constants.kDriveTurnKf, Constants.kTimeoutMs);
 		rightMaster.config_IntegralZone(Constants.kSlotTurn, Constants.kDriveTurnIZone, Constants.kTimeoutMs);
		
		rightMaster.configClosedLoopPeakOutput(Constants.kSlotTurn, Constants.kDriveTurnPeakOutput, Constants.kTimeoutMs);

 		leftMaster.config_kP(Constants.kSlotTurn, Constants.kDriveTurnKp, Constants.kTimeoutMs);
 		leftMaster.config_kI(Constants.kSlotTurn, Constants.kDriveTurnKi, Constants.kTimeoutMs);
 		leftMaster.config_kD(Constants.kSlotTurn, Constants.kDriveTurnKd, Constants.kTimeoutMs);
 		leftMaster.config_kF(Constants.kSlotTurn, Constants.kDriveTurnKf, Constants.kTimeoutMs);
 		leftMaster.config_IntegralZone(Constants.kSlotTurn, Constants.kDriveTurnIZone, Constants.kTimeoutMs);
		 
		leftMaster.configClosedLoopPeakOutput(Constants.kSlotTurn, Constants.kDriveTurnPeakOutput, Constants.kTimeoutMs);

	}
    public double getLeftDistance() {
		return getInchesFromNativeUnits(leftMaster.getSelectedSensorPosition(0));
		//return getInchesFromNativeUnits(leftMaster.getSensorCollection().getQuadraturePosition());
    }
    
    public double getRightDistance() {
		return getInchesFromNativeUnits(rightMaster.getSelectedSensorPosition(0));
		//return getInchesFromNativeUnits(rightMaster.getSensorCollection().getQuadraturePosition());
    }
    
    public double getAngle() {
    	return navX.getAngle(); // in 
	}
	
	public void resetAngle() {
		navX.zeroYaw();
	}
    
    public void configureForDrive() {
		rightMaster.selectProfileSlot(Constants.kSlotStraight, 0);
		leftMaster.selectProfileSlot(Constants.kSlotStraight, 0);

		rightMaster.setIntegralAccumulator(0, 0, Constants.kTimeoutMs);
		leftMaster.setIntegralAccumulator(0, 0, Constants.kTimeoutMs);
		
    }
    public void configureForTurn() {
		rightMaster.selectProfileSlot(Constants.kSlotTurn, 0);
		leftMaster.selectProfileSlot(Constants.kSlotTurn, 0);
 	
 		rightMaster.setIntegralAccumulator(0, 0, Constants.kTimeoutMs);
 		leftMaster.setIntegralAccumulator(0, 0, Constants.kTimeoutMs);
	
     }
    public void outputToSmartDashboard() {
    	SmartDashboard.putNumber("left position", getLeftDistance());
    	SmartDashboard.putNumber("right position", getRightDistance());
    	SmartDashboard.putNumber("gyro angle", getAngle());
    	SmartDashboard.putNumber("Left CL Error", leftMaster.getClosedLoopError(0));
    	SmartDashboard.putNumber("Right CL Error", rightMaster.getClosedLoopError(0));
	}
	
	@Override
    /* This function is invoked periodically by the PID Controller, */
    /* based upon navX MXP yaw angle input and PID Coefficients.    */
    public void pidWrite(double output) {
        turnMotorInput = output;
	}
	
	public PIDController getController(){
        return turnController;
	}

	public void getDrivetrainVoltage() {
		//drivePercentOutput(.25, .25);
		double l1 = leftMaster.getMotorOutputVoltage();
		double r1 = rightMaster.getMotorOutputVoltage();
		double l2 = leftSlave.getMotorOutputVoltage();
		double r2 = rightSlave.getMotorOutputVoltage();
		if (l1 < .6) {
			System.out.println("Left Master Broken");	
		}
		else if (l2 < .6) {
			System.out.println("Left Slave Broken");	
		}
		else if (r1 < .6) {
			System.out.println("Right Master Broken");	
		}
		else if (r2 < .6) {
			System.out.println("Right Slave Broken");	
		}
		else System.out.println("Drive Talons Fine");
	}

}

