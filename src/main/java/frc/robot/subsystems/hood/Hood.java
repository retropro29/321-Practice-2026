package frc.robot.subsystems.hood;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@Logged
public class Hood{

        TalonFX hoodMotor = new TalonFX(HoodConstants.kHoodMotorID);

        final CurrentLimitsConfigs currentLimits = 
            new CurrentLimitsConfigs()
                .withSupplyCurrentLimit(HoodConstants.kSupplyCurrentLimit)
                .withSupplyCurrentLimitEnable(HoodConstants.kSupplyLimitEnabled)
                .withStatorCurrentLimit(HoodConstants.kStatorCurrentLimit)
                .withStatorCurrentLimitEnable(HoodConstants.kStatorCurrentEnabled);
        
        final MotorOutputConfigs motorOutputConfig = 
            new MotorOutputConfigs()
                .withNeutralMode(HoodConstants.kNeutralModeValue)
                .withInverted(HoodConstants.kInversion);  
        
        final FeedbackConfigs feedback =
            new FeedbackConfigs().withSensorToMechanismRatio(HoodConstants.kHoodGearRatio);

        final TalonFXConfiguration motorConfigurations = 
            new TalonFXConfiguration()
                .withCurrentLimits(currentLimits)
                .withMotorOutput(motorOutputConfig)
                .withFeedback(feedback);


    public Hood(){
        configureMotor();
    }

    private void configureMotor(){
        hoodMotor.getConfigurator().apply(motorConfigurations);
    }

    private double kP = 0;
    private double kI = 0;
    private double kD = 0;

    private double kS = 0;
    private double kG = 0;
    private double kV = 0;

    Constraints hoodConstraints = 
        new Constraints(
            HoodConstants.kMaxVelocity.in(DegreesPerSecond), 
            HoodConstants.kMaxAcceleration.in(DegreesPerSecondPerSecond));

    ArmFeedforward hoodFeedForward = 
        new ArmFeedforward(
            kS,
            kG,
            kV);

    ProfiledPIDController hoodController = 
        new ProfiledPIDController(
            kP,
            kI, 
            kD, 
            hoodConstraints);

    public void goToAngle(Angle targetAngle){
        Angle currentAngle = getAngle();

        Voltage desiredVoltage = Volts.of(
            hoodController.calculate(
                currentAngle.in(Degrees),
                targetAngle.in(Degrees)));

        hoodMotor.setVoltage(desiredVoltage.in(Volts));
    }

    @Logged(name = "hoodVoltage")
    public Voltage getVoltage(){
        Voltage voltage = hoodMotor.getMotorVoltage().getValue();
        return voltage;
    }

    @Logged(name = "hoodCurrent")
    public Current getCurrent(){
        Current current = hoodMotor.getStatorCurrent().getValue();
        return current;
    }

    @Logged(name = "hoodPitch")
    public Angle getAngle(){
        Angle angle = hoodMotor.getPosition().getValue();
        return angle;
    }

    @Logged(name = "atTargetAngleHood")
    public boolean atTargetAngle(Angle targetAngle){
        boolean result =  Math.abs(getAngle().in(Degrees) - targetAngle.in(Degrees)) <= HoodConstants.kTargetAngleTolerance.in(Degrees);
        return result;
    }


    //@Override
    public void periodic(){
        SmartDashboard.putNumber("Hood Angle", hoodMotor.getPosition().getValue().in(Degrees));
    }

}