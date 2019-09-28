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

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc4079.DeepSpace.commands.AutoClimb;
import org.usfirst.frc4079.DeepSpace.commands.GamepadDrive;
import org.usfirst.frc4079.DeepSpace.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {
    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();
    public static OI oi;
    public static boolean startAutoFlag = false;
    public static double centerX = 0.0;
    public static double distance = 0.0;
    public static double offsetAngle = 0.0;
    public static boolean isEndgame;
    public static boolean allowAutoClimb;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Drivetrain drivetrain;
    public static Climber climber;
    public static HatchMan hatchMan;
    public static HatchManV2 hatchManV2;
    public static Vision vision;
    public static Camera camera;
    private Timer timer;
    
   // public static SpectrumJeVois jevoisCam;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        drivetrain = new Drivetrain();
        climber = new Climber();
        hatchMan = new HatchMan();
        hatchManV2 = new HatchManV2();
        vision = new Vision();
        camera = new Camera();
        timer = new Timer();
        isEndgame = false;
        allowAutoClimb = false;

       // jevoisCam = new SpectrumJeVois();
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();
        
        vision.startJevoisCamStream();
        vision.initializeSerialPort();

        // Add commands to Autonomous Sendable Chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

      //  chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        drivetrain.initTalons();      
        drivetrain.resetAngle();
        //chooser.setDefaultOption("CmdGrpTest", new MiddleTarget0());
        //chooser.addObject("LeftTarget1", new LeftTarget1());
        //SmartDashboard.putData("Target CargoShip Hatch", chooser);
        
    } 

    @Override
    public void robotPeriodic() {
        vision.backgroundUpdate();
        centerX = vision.getCenterX();
        distance = vision.getDistance();
        offsetAngle = vision.getOffsetAngle();
        boolean isTargetVisible = (distance != -1);
        SmartDashboard.putBoolean("Vision Targets Detected", isTargetVisible);
        hatchManV2.getTopLimitSwitch(); //Outputs values of limit switches to Shuffleboard
        hatchManV2.getBotLimitSwitch();
      //  SmartDashboard.putNumber("Distance", distance);
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){
       // jevoisCam.setSerOutEnable(true);
		//jevoisCam.startCameraStream1();
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
      //  jevoisCam.startCameraStream2();
		//jevoisCam.setSerOutEnable(true);
        autonomousCommand = chooser.getSelected();
         // schedule the autonomous command (example)
       // if (autonomousCommand != null) autonomousCommand.start();
        drivetrain.resetEncoders();
        Constants.kMaxDrivePower = 0.4;
       // new DriveToVisionTargets().start();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        if (autonomousCommand != null && startAutoFlag) {
            autonomousCommand.start();
            startAutoFlag=false;
        } 
        drivetrain.outputToSmartDashboard();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        drivetrain.resetEncoders();
        Constants.kMaxDrivePower=1.0;
        //jevoisCam.startCameraStream2();
        //jevoisCam.setSerOutEnable(true);
        timer.start();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        drivetrain.outputToSmartDashboard();
        Robot.climber.getFrontEncoderCounts();
        Robot.climber.getBackEncoderCounts();

        if(timer.get() == 145) {
            isEndgame = true;
        }
    }

    public static double getOffsetAngle() {
        return offsetAngle;
    }
    
    public static double getDistance() {
        return distance;
    }

    public static double getCenterX() {
        return centerX;
    }

    public static void setOffsetAngle(double angle) {
        offsetAngle = angle;
    }
    
    public static void setDistance(double distanceToTarget) {
        distance = distanceToTarget;
    }

    
}
