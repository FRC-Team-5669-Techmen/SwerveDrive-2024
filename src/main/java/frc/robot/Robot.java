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


public class Robot extends TimedRobot {
  private Constants Constants = new Constants();
  
  double SpeedMulti;
  double Y_axis_speed;
  double X_axis_speed;
  double speed;
  double speedForMotor;
  double turn;
  double turnForMotor;
  double Intake_Speed;
  double Intake_Speed2;
  double Run_to_Value;
  boolean Turnable;
  boolean speedable;
  boolean IsFoward;
  boolean IsArmMoving;
  int Ticks;
  

  private final Timer m_timer = new Timer();
  

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  @Override
  public void robotInit() {
    
    Constants.m_rightDrive.setInverted(true);
    Constants.m_rightBDrive.setInverted(true);
    Constants.m_Arm1.setInverted(true);
    
 
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
    X_axis_speed = Constants.m_controller.getRawAxis(0);
    if (Math.abs(Y_axis_speed)>Math.abs(X_axis_speed)){
      speed = Y_axis_speed;
    }
    else{
    
      speed = Math.abs(X_axis_speed);
      if (Y_axis_speed !=0){
        speed = speed * Math.signum(Y_axis_speed);
      }
    }
  
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
      Methods.Move(Constants.m_rightTurn, Constants.coder_right, Y_axis_speed, -X_axis_speed, speed, Constants.m_rightDrive,false,Ticks);
      Methods.Move(Constants.m_leftBTurn, Constants.coder_leftB, Y_axis_speed, -X_axis_speed, speed, Constants.m_leftBDrive,true,Ticks);
      Methods.Move(Constants.m_leftTurn, Constants.coder_left, Y_axis_speed, -X_axis_speed, speed, Constants.m_leftDrive,false,Ticks);
      Methods.Move(Constants.m_rightBTurn, Constants.coder_rightB, Y_axis_speed, -X_axis_speed, speed, Constants.m_rightBDrive,false,Ticks);
    }
// if this doesnt work as intended use "getRawButtonPressed" instead of "getRawButton" 
    // A Button
    if (Constants.m_controller.getRawButton(1)){
      Methods.ArmMovement(Constants.m_Arm1, 0.75, false);
      Methods.ArmMovement(Constants.m_Arm2, 0.75, false);
    }
    // Y Button
    if (Constants.m_controller.getRawButton(4)){
      Methods.ArmMovement(Constants.m_Arm1, -0.75, false);
      Methods.ArmMovement(Constants.m_Arm2, -0.75, false);
    }
    if (!Constants.m_controller.getRawButton(4) && !Constants.m_controller.getRawButton(1)){
      Methods.ArmMovement(Constants.m_Arm1, 0, true);
      Methods.ArmMovement(Constants.m_Arm2, 0, true);
    }

    if (Constants.m_controller.getRawButton(3)){
      Constants.m_Shooter1.set(1); 
      Constants.m_Shooter2.set(1); 
      Intake_Speed = 1;
      
      Ticks += 1;

      if (Ticks == 30 ){
      Constants.m_Intake.set(ControlMode.PercentOutput, 1);
      Intake_Speed2 = 1;
      }
    }
    else{
      if (Intake_Speed >= 0){
        Intake_Speed -= 0.02;
      }
      if (Intake_Speed < 0){
        Intake_Speed = 0;
      } 
      if (Intake_Speed2 >= 0){
        Intake_Speed2 -= 0.02;
      }
      if (Intake_Speed2 < 0){
        Intake_Speed2 = 0;
      }
      Constants.m_Intake.set(ControlMode.PercentOutput, Intake_Speed2);
      Constants.m_Shooter1.set(Intake_Speed); 
      Constants.m_Shooter2.set(Intake_Speed); 
      Ticks = 0;
    }
    if (Constants.m_controller.getRawButton(2)){
      Constants.m_Intake.set(ControlMode.PercentOutput, 1);
      Intake_Speed2 = 1;
    }
    else{
      if (Intake_Speed2 >= 0){
        Intake_Speed2 -= 0.02;
      }
      if (Intake_Speed2 < 0){
        Intake_Speed2 = 0;
      }
      Constants.m_Intake.set(ControlMode.PercentOutput, Intake_Speed2);
    }
    if (Constants.m_controller.getRawButtonPressed(5)){
      Run_to_Value = 0;
      while (Run_to_Value <= 4000){
      Methods.ArmMovement2(Constants.m_Arm1, -5);
      Methods.ArmMovement2(Constants.m_Arm2, -5);
      Run_to_Value +=1;
      }
      
    }
    /* 
    if (Constants.m_controller.getRawButton(3)){
      Constants.m_Shooter1.set(ControlMode.PercentOutput, 0.1); 
      Constants.m_Shooter2.set(ControlMode.PercentOutput, 0.1); 
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

