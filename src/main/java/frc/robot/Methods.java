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


public class Methods{
/* 
  public static void Turn(int con1, int con2, TalonSRX drive, CANCoder coder, double turnspeed, TalonSRX Motor){
    double Motorspeed1 = turnspeed * 0.20;
    double drivepos = drive.getSelectedSensorPosition();
    double coderpos = coder.getPosition();
    double coderposABS = coder.getAbsolutePosition();
    double Difference1 = Math.abs(con1 - coderpos);
    double Difference2 = Math.abs(con2 - coderpos);
    double Difference3 = coderpos%360;
    double Difference4 = coderpos - Difference3;
    boolean ReachedDes = false;
    double degreesToTicks = 4096.0 / 360.0; 
    //drive.setNeutralMode(NeutralMode.Brake);
    
    System.out.println("drivepos is : " + drivepos + "Difference1 is : " + Difference1+ "Difference2 is : " + Difference2 + "coder is: " + coderpos);
    drive.config_kP(0,0.5);
    System.out.println(con2*degreesToTicks);
    if (Difference1 > Difference2 ){
      //&& (drivepos != (Difference4 +con2)*5.8666 && drivepos != (Difference4 + con1)*5.8666)
      drive.set(ControlMode.Position, con2*degreesToTicks);
    }
    else{
      
      if (Difference1 <Difference2 ){
        //&&( drivepos != (Difference4 + con1)*5.8666 && drivepos != (Difference4 +con2)*5.8666)
        drive.set(ControlMode.Position, con1*degreesToTicks);
      }
    
    }
    if (coderposABS == con2 || coderposABS == con1){
      ReachedDes = true;
    }

    if( ReachedDes == true){
        Motor.set(ControlMode.PercentOutput, Motorspeed1);
        Motor.set(ControlMode.Position, Motorspeed1);
      }
    else{

      }


  }
  */
  public static void Turn(double targetAngleDegrees, TalonSRX drive, CANCoder coder, double turnspeed, TalonSRX Motor) {
    double unitsPerRotation = 2048.0;
    
    // Calculate target positions in encoder ticks
    int targetPosition1 = (int) (targetAngleDegrees / 360.0 * unitsPerRotation);
    int targetPosition2 = (int) ((targetAngleDegrees - 180.0) / 360.0 * unitsPerRotation); // Assuming a 180-degree difference
    System.out.println("targetPosition1 " +targetPosition1);
    System.out.println("targetPosition2 " +targetPosition2);
    // Read current sensor position
    double coderposABS = coder.getAbsolutePosition();
    System.out.println("coderposABS " +coderposABS);
    
    // Set drive motor position based on the closest target position
    if (Math.abs(coderposABS - targetPosition1) < Math.abs(coderposABS - targetPosition2)) {
        drive.config_kP(0,0.5);
        drive.set(ControlMode.Position, targetPosition1);
        System.out.println("this");
    } else {
        drive.config_kP(0,0.5);
        drive.set(ControlMode.Position, targetPosition2);
        System.out.println("that");
        
    }
    // Check if desired position reached within a threshold
    double threshold = 5.0; // Adjust as needed
    double drivepos = drive.getSelectedSensorPosition();
    System.out.println("drivepos" + drivepos);
    System.out.println("Math.abs(coderposABS - targetPosition1) < threshold " +( Math.abs(coderposABS - (targetPosition1/unitsPerRotation)) ));
    System.out.println("Math.abs(coderposABS - targetPosition2) < threshold " +( Math.abs(coderposABS - (targetPosition2/unitsPerRotation)) ));
    if (Math.abs(coderposABS - (targetPosition1/unitsPerRotation)) < threshold || Math.abs(coderposABS - (targetPosition2/unitsPerRotation)) < threshold) {
        // Activate the other motor once the desired position is reached
        double Motorspeed1 = turnspeed * 0.20;
        Motor.set(ControlMode.PercentOutput, Motorspeed1); // Adjust for your motor
    }
}
  public static void Move(double Motorspeed, TalonSRX Motor, TalonSRX MTurn, CANCoder coder, int con1, int con2){
    double drivepos = MTurn.getSelectedSensorPosition();
    double coderpos = coder.getPosition();
    double coderpos1 = Motor.getSelectedSensorVelocity();
    double Motorspeed1 = Motorspeed * 0.10;
    double Difference1 = Math.abs(con1 - coderpos);
    double Difference2 = Math.abs(con2 - coderpos);
    MTurn.config_kP(0,0.5);
    if (coderpos <= 2 || coderpos >= 358){
      System.out.println(" is Running MOTOR speed ");
      System.out.println( " (con2*5.68888) is " + (con2*5.68888) + " coderpos is " + coderpos+" coderpos <= 2 && coderpos >= 2 ");
      Motor.set(ControlMode.PercentOutput,Motorspeed1);
      //System.out.println("Motorspeed is " + Motorspeed1);
      //System.out.println("Velo " + coderpos1);
      
      //MTurn.setNeutralMode(NeutralMode.Brake);
    }
    else {
      System.out.println(" is Running MOTOR speed ");
      System.out.println( " (con2*5.68888) is " + (con2*5.68888) + " coderpos is " + coderpos+" coderpos <= 2 && coderpos >= 2 ");
      MTurn.set(ControlMode.Position, con2*5.68888);

      
      
    }
    /*
     if (Difference2 >Difference1){
      MTurn.set(ControlMode.Position,drivepos + con1*13.4);
    }
     */
    
    

    }
}
  