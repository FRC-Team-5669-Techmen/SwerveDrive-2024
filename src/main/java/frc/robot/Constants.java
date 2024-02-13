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
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.*;

public final class Constants {
    

    //Establishment on motors that make the wheels move
    int ID_m_leftDrive = 2;
    int ID_m_rightDrive = 6;
    int ID_m_rightBDrive = 3;
    int ID_m_leftBDrive = 8;
    int ID_m_leftTurn = 1;
    int ID_m_rightTurn = 7;
    int ID_m_rightBTurn = 5;
    int ID_m_leftBTurn = 4;
    int ID_coder_right = 10;
    int ID_coder_leftB = 13;
    int ID_coder_rightB = 12;
    int ID_coder_left = 14;



    public final PigeonIMU gyro = new PigeonIMU(9);
     public final TalonSRX m_leftDrive = new TalonSRX(ID_m_leftDrive);
     public final TalonSRX m_rightDrive = new TalonSRX(ID_m_rightDrive);
     public final TalonSRX m_rightBDrive = new TalonSRX(ID_m_rightBDrive);
     public final TalonSRX m_leftBDrive = new TalonSRX(ID_m_leftBDrive);

     CANCoder coder_right= new CANCoder(ID_coder_right);
     CANCoder coder_rightB = new CANCoder(ID_coder_rightB);
     CANCoder coder_leftB = new CANCoder(ID_coder_leftB);
     CANCoder coder_left = new CANCoder(ID_coder_left);
  
     public final TalonSRX m_leftTurn = new TalonSRX(ID_m_leftTurn);
     public final TalonSRX m_rightTurn = new TalonSRX(ID_m_rightTurn);
     public final TalonSRX m_rightBTurn = new TalonSRX(ID_m_rightBTurn);
     public final TalonSRX m_leftBTurn = new TalonSRX(ID_m_leftBTurn);
  

    
     public final XboxController m_controller = new XboxController(0);
     
    
}
