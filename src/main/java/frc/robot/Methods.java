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

import java.text.Format;

import com.ctre.phoenix.*;
import frc.robot.Constants;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Methods{

  public static void Turn(double targetAngleDegrees, TalonSRX drive, CANCoder coder, double turnspeed, TalonSRX Motor, boolean Debug) {
    double unitsPerRotation = 2048.0;
    boolean Is_Negative;
    // Calculate target positions in encoder ticks
    int targetPosition1 = (int) (targetAngleDegrees / 360.0 * unitsPerRotation);
    int targetPosition2 = (int) ((targetAngleDegrees - 180.0) / 360.0 * unitsPerRotation); // Assuming a 180-degree difference
    double drivepos = drive.getSelectedSensorPosition();
    //double Remainder = drivepos%26214.4;
    //double How_many_rotations = drivepos-Remainder;
    
    //System.out.println("targetPosition1 " +targetPosition1);
    //System.out.println("targetPosition2 " +targetPosition2);
    // Read current sensor position
    double coderposABS = coder.getAbsolutePosition();
    // Set drive motor position based on the closest target position
    if (Math.abs(coderposABS - targetPosition1/ unitsPerRotation) < Math.abs(coderposABS - targetPosition2/ unitsPerRotation)) {
        drive.config_kP(0,0.25);
        drive.set(ControlMode.Position,(targetPosition1 * 12.8));
        //System.out.println("cposABS " + coderposABS + " this " +(targetPosition1/unitsPerRotation)* 360.0);
        Is_Negative = false;
    } else {
        drive.config_kP(0,0.25);
        drive.set(ControlMode.Position, (targetPosition2* 12.8));
        //System.out.println("cposABS " + coderposABS + " this " +(targetPosition1/unitsPerRotation)* 360.0);
        Is_Negative = true;
        
    }
    int Negative_multi;
    if (Is_Negative == true){
      Negative_multi = -1;
    }
    else{
      Negative_multi = 1;
    }
    // Check if desired position reached within a threshold
    double threshold = 15.0; // Adjust as needed
    //System.out.println("drivepos" + drivepos);
    //System.out.println("Math.abs(coderposABS - targetPosition1) < threshold " +( (Math.abs(coderposABS - (targetPosition1/unitsPerRotation)))/12.8 ));
    //System.out.println("Math.abs(coderposABS - targetPosition2) < threshold " +( (Math.abs(coderposABS - (targetPosition2/unitsPerRotation)))/12.8 ));
    //System.out.println("Debug" + Debug);
    if ((Math.abs(coderposABS - (targetPosition1/unitsPerRotation))/12.8) < threshold || (Math.abs(coderposABS - (targetPosition2/unitsPerRotation))/12.8) < threshold) {
        // Activate the other motor once the desired position is reached
        /*
        if (Debug == true){
          System.out.println("cposABS " +coderposABS + 
          " tarPos2 " +targetPosition2 + 
          " tarPos1 " +targetPosition1 + 
          " dpos " + drivepos + 
          " (cposABS - tarPos2) < threshold " +( (Math.abs(coderposABS - (targetPosition2/unitsPerRotation)))/12.8 ) + 
          " (cposABS - tarPos1) < threshold " +( (Math.abs(coderposABS - (targetPosition1/unitsPerRotation)))/12.8 ) + 
          " dpos_Degs " + (drivepos/12.8));
         }
         */
        double Motorspeed1 = turnspeed * 0.20;
        Motor.set(ControlMode.PercentOutput, Motorspeed1 * Negative_multi); // Adjust for your motor
    }

}
public static void StandStill(TalonSRX drive, TalonSRX Motor){
  double drivepos = drive.getSelectedSensorPosition();
  double Motorpos = Motor.getSelectedSensorPosition();
  drive.set(ControlMode.Position, drivepos);
  Motor.set(ControlMode.Position, Motorpos);
  drive.set(ControlMode.PercentOutput,0);
  Motor.set(ControlMode.PercentOutput, 0);
}






 // Make it find the best pos
public static void Move(TalonSRX drive, CANCoder coder, double Y_axis_turnspeed, double X_axis_turnspeed, double turnspeed, TalonSRX Motor, boolean Debug, int Time) {
  double unitsPerRotation = 2048.0;
  double targetPosition1;
  double targetPosition2;
  double targetPosition;
  
  double targetAngleDegrees = Math.atan(X_axis_turnspeed/ Y_axis_turnspeed ) * (180/Math.PI);
  /* 
  if (Y_axis_turnspeed <= -0.0){
    
    targetAngleDegrees = targetAngleDegrees -180;
    if (X_axis_turnspeed >= 0.0){
      //-y, -x
      targetAngleDegrees = targetAngleDegrees;
    }
    if (X_axis_turnspeed<= -0.0){
      //-y, -x
      targetAngleDegrees = targetAngleDegrees-90;
    }
    else{
      //-y, x
      targetAngleDegrees = targetAngleDegrees +90;
    }
  }*/
   /* 
  else{
    if (X_axis_turnspeed<= -0.0){
      //y, -x
      targetAngleDegrees = targetAngleDegrees;
    }
    else{
      //y, x
      targetAngleDegrees = targetAngleDegrees -180;
    }
  }*/ 
    double drivepos = drive.getSelectedSensorPosition();
    // Calculate target positions in encoder ticks
  if (targetAngleDegrees >= 180){
    targetPosition2 = targetAngleDegrees - 360;
    targetPosition1 = targetAngleDegrees - 180;
  }
  else{
    targetPosition2 = targetAngleDegrees - 180;
    targetPosition1 = targetAngleDegrees ;
  }
  //targetPosition2 = targetPosition2/ 360.0;
  //targetPosition1 = targetPosition1/ 360.0;
  if (targetAngleDegrees >= 180){
    targetAngleDegrees -= 180;
  }
  

  
  int Negative_multi;
  double coderposABS = coder.getAbsolutePosition();
  if (Math.abs(targetAngleDegrees) < 180) {
    targetPosition = targetPosition1;
    Negative_multi = 1;
} else {
  targetPosition = targetPosition2;
    Negative_multi = -1;
}
  double targetAngleDegrees_After_Calculations = (targetAngleDegrees) ;
    if (Debug == true){
      if (Time%5 == 0){
        System.out.println(" targetAngle_AC " +targetAngleDegrees_After_Calculations + " Y_speed " +Y_axis_turnspeed + " X_speed " +X_axis_turnspeed+ " Multi " +Negative_multi+ " speed " +turnspeed);
      }
    
  }
  
    drive.config_kP(0,0.25);
    drive.set(ControlMode.Position,(targetAngleDegrees_After_Calculations)* unitsPerRotation*12.8/360);
    double Motorspeed1 = turnspeed * 0.10;
   Motor.set(ControlMode.PercentOutput, Motorspeed1 * Negative_multi); // Adjust for your motor

}
public static void ArmMovement(TalonSRX Motor1, double Amount_Moved){
  double unitsPerRotation = 2048.0;
  double drivepos = Motor1.getSelectedSensorPosition();
  Amount_Moved *= (180/Math.PI);
  Motor1.config_kP(0,0.25);
  Motor1.set(ControlMode.Position,(Amount_Moved* unitsPerRotation/360)+ drivepos);
}
  
}
  
