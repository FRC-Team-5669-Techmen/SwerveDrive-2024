// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenixpro.controls.StaticBrake;
import com.ctre.phoenix.*;
import frc.robot.Constants;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Methods;



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */


public class Robot extends TimedRobot {
  private Constants Constants = new Constants();
  
  double SpeedMulti;
  double Y_axis_speed;
  double X_axis_speed;
  double speed;
  double speedForMotor;
  double turn;
  double turnForMotor;
  boolean Turnable;
  boolean speedable;
  boolean IsFoward;
  

  private final Timer m_timer = new Timer();
  

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

   // calls upon function wrong 

  @Override
  public void robotInit() {
    
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    Constants.m_rightDrive.setInverted(true);
    Constants.m_rightBDrive.setInverted(true);
 
/* 
 
*/





    
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    /* 
    if (m_timer.get() < 1.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      Constants.m_rightDrive.set(ControlMode.PercentOutput,0.5);
      Constants.m_leftDrive.set(ControlMode.PercentOutput,0.5);
      Constants.m_rightBDrive.set(ControlMode.PercentOutput,0.5);
      Constants.m_leftBDrive.set(ControlMode.PercentOutput,0.5);
      //test1.set(ControlMode.PercentOutput,0.6);
      //.set(ControlMode.PercentOutput,0.6);
    } else {
      Constants.m_rightDrive.set(ControlMode.PercentOutput,0); // stop robot
      Constants.m_leftDrive.set(ControlMode.PercentOutput,0);
      Constants.m_rightBDrive.set(ControlMode.PercentOutput,0);
      Constants.m_leftBDrive.set(ControlMode.PercentOutput,0);
      //test1.set(ControlMode.PercentOutput,0);
      //test2.set(ControlMode.PercentOutput,0);
    }
    */
  }



  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
  SpeedMulti = Constants.m_controller.getRawAxis(3)*2;
  Y_axis_speed = -Constants.m_controller.getRawAxis(1);
  X_axis_speed = -Constants.m_controller.getRawAxis(0);
  speed = ((Y_axis_speed+X_axis_speed)/2);
  turn = Constants.m_controller.getRawAxis(4);

  if (SpeedMulti != 0) {
    speed = speed * SpeedMulti;
  }
  if (turn == 0 && (Math.abs(speed) < 0.1)) {
    IsFoward = false;
    Methods.StandStill(Constants.m_rightTurn, Constants.m_rightDrive);
    Methods.StandStill(Constants.m_leftBTurn, Constants.m_leftBDrive);
    Methods.StandStill(Constants.m_leftTurn, Constants.m_leftDrive);
    Methods.StandStill(Constants.m_rightBTurn, Constants.m_rightBDrive);
    
    }
  if (turn != 0) {


  Methods.Turn(45, Constants.m_rightTurn, Constants.coder_right, -turn, Constants.m_rightDrive, true);
  Methods.Turn(45,  Constants.m_leftBTurn, Constants.coder_leftB, turn, Constants.m_leftBDrive, false);
  Methods.Turn(135, Constants.m_leftTurn, Constants.coder_left, -turn, Constants.m_leftDrive,false );
  Methods.Turn(135,  Constants.m_rightBTurn, Constants.coder_rightB, turn, Constants.m_rightBDrive, false);
  
  }
  if (Math.abs(speed)  >= 0.1){
    //System.out.println("Turn:" + Constants.m_controller.getRawAxis(4));
    //System.out.println("Speed:" +-Constants.m_controller.getRawAxis(1));
    //System.out.println("Turn:" + Constants.m_controller.getRawAxis(4));
    //System.out.println("Speed:" + speed);
    Methods.Move(Constants.m_rightTurn, Constants.coder_right, Y_axis_speed, X_axis_speed, speed, Constants.m_rightDrive,false);
    Methods.Move(Constants.m_leftBTurn, Constants.coder_leftB, Y_axis_speed, X_axis_speed, speed, Constants.m_leftBDrive,true);
    Methods.Move(Constants.m_leftTurn, Constants.coder_left, Y_axis_speed, X_axis_speed, speed, Constants.m_leftDrive,false);
    Methods.Move(Constants.m_rightBTurn, Constants.coder_rightB, Y_axis_speed, X_axis_speed, speed, Constants.m_rightBDrive,false);
  }
  //speedForMotor = Math.pow(speed, 3);
  //turnForMotor = Math.pow(turn, 3);
/* 
  if (turn != 0 && Turnable == true) {
    speedable = false;
    //rightTurn fix me
    Methods.Turn(135, 315, Constants.m_rightTurn, Constants.coder_right, turn, Constants.m_rightDrive);
    Methods.Turn(45, 225,  Constants.m_leftBTurn, Constants.coder_leftB, turn, Constants.m_leftBDrive);
    Methods.Turn(45, 225, Constants.m_leftTurn, Constants.coder_left, turn, Constants.m_leftDrive);
    Methods.Turn(135, 315,  Constants.m_rightBTurn, Constants.coder_rightB, turn, Constants.m_rightBDrive);
    //System.out.println("turn is " + turn);

  }
  else {
    speedable = true;
  }
  
  if ((speed - 0.0078125) != 0 && speedable == true){
    Turnable = false; 
    //something wrong with function make thing break
    Methods.Move(speed, Constants.m_leftDrive, Constants.m_leftTurn,Constants.coder_left,180, 0);
    Methods.Move(speed, Constants.m_rightDrive, Constants.m_rightTurn,Constants.coder_right,180, 0);
    Methods.Move(speed, Constants.m_leftBDrive, Constants.m_leftBTurn,Constants.coder_leftB,180, 0);
    Methods.Move(speed, Constants.m_rightBDrive, Constants.m_rightBTurn,Constants.coder_rightB,180, 0);
   
    //System.out.println("speed is " + speed);

  }
  else {
    Turnable = true;
  }
  */

/*
 if (SpeedMulti > 1) {
        speed = speed * SpeedMulti;

    }

    speed = Math.pow(speed, 3);
    turn = Math.pow(turn, 3);

    Constants.m_leftDrive.set(ControlMode.PercentOutput,speed);
    Constants.m_rightDrive.set(ControlMode.PercentOutput,speed);
    Constants.m_leftBDrive.set(ControlMode.PercentOutput,speed);
    Constants.m_rightBDrive.set(ControlMode.PercentOutput,speed);
    if (turn != 0){
    Constants.m_leftTurn.set(ControlMode.PercentOutput,turn);
    Constants.m_rightTurn.set(ControlMode.PercentOutput,turn);
    Constants.m_leftBTurn.set(ControlMode.PercentOutput,turn);
    Constants.m_rightBTurn.set(ControlMode.PercentOutput,turn);
    } 
    else{
    Constants.m_rightTurn.setNeutralMode(NeutralMode.Brake);
    Constants.m_leftTurn.setNeutralMode(NeutralMode.Brake);
    Constants.m_leftBTurn.setNeutralMode(NeutralMode.Brake);
    Constants.m_rightBTurn.setNeutralMode(NeutralMode.Brake);
    }
 */


    
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}

