// to be honest IDK how much of these imports you need but it seems to work 
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

import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.ctre.phoenix.*;


public final class Constants {
    

// Establishment The ids to the use latter
// Any ID that has "Drive" in the name means that it controls how it propels 
// Any ID that has "Turn" in the name means that it controls how the swerve moduel is orientated 
// Any ID that has "coder" is an coder ID
// Any ID that has "B" means that they are at the back of the robot
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
    int ID_Arm_1 = 15;
    int ID_Arm_2 = 16;
    int ID_Intake = 17;
    private static final int ID_Shooter1 = 18;
    private static final int ID_Shooter2 = 19;
    private static final MotorType kMotorType = MotorType.kBrushless;
    AnalogEncoder encoder = new AnalogEncoder(0);


// etsablishes the devices with there ids to be called later in the code
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

    public final TalonSRX m_Arm1 = new TalonSRX(ID_Arm_1);
    public final TalonSRX m_Arm2 = new TalonSRX(ID_Arm_2);

    public final TalonSRX m_Intake = new TalonSRX(ID_Intake);

    public final CANSparkMax m_Shooter1 = new CANSparkMax(ID_Shooter1, kMotorType);
    public final CANSparkMax m_Shooter2 = new CANSparkMax(ID_Shooter2, kMotorType);
  
    public final XboxController m_controller = new XboxController(0);
     
}
