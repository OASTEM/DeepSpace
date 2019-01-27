package org.usfirst.frc4079.DeepSpace;

public class Constants {
	/* ROBOT PHYSICAL CONSTANTS */
    // Encoder
    public static double kSensorUnitsPerRotation = 585; //4096 for 2019, 585 for 2018
    // Sensor units per one rotation of the wheel shaft

    // Wheels
    public static double kDriveWheelDiameterInches = 6.0;
    public static double kTrackWidthInches = 23.75;
    
    public static int kSlotStraight = 0;
    public static double kDriveStraightKp = 0.2;
    public static double kDriveStraightKi = 0.0;
    public static double kDriveStraightKd = 0.0;
    public static double kDriveStraightKf = 1.6;
    public static int kDriveStraightIZone = 100;
    public static double kDriveStraightPeakOutput = 1;
    public static int kDriveStraightCruiseVelocity = 500;
    public static int kDriveStraightAcceleration = 2500;

    public static int kSlotTurn =1;
    public static double kDriveTurnKp = 2.0;
    public static double kDriveTurnKi = 0.0;
    public static double kDriveTurnKd = 0.0;
    public static double kDriveTurnKf = 0.0; // must be zero for position control
    public static int kDriveTurnIZone = 100;
    public static double kDriveTurnPeakOutput = 1;
   // public static int kDriveTurnCruiseVelocity = 500; // Assumes position control for turn
   // public static int kDriveTurnAcceleration = 2500;  // Assumes position control for turn

    public static int kTimeoutMs = 10;

    public static int kStatus_13_TimeMs = 10; // Default 160
    public static int kStatus_10_TimeMs = 10; // Default 160

    public static double kClosedLoopRampRate = 0; // seconds from zero to full
    public static double kOpenLoopRampRate = 1; // seconds from zero to full

    public static final int kBaseTrajPeriodMs = 0;
    public static final double kNeutralDeadband = 0.01;
}
