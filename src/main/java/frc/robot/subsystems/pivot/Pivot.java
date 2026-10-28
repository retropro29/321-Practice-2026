package frc.robot.subsystems.pivot;

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
import frc.robot.subsystems.hood.HoodConstants;

public class Pivot {
    TalonFX pivotMotor = new TalonFX(PivotConstants.kPivotMotorID);

    final CurrentLimitsConfigs currentLimits = 
        new CurrentLimitsConfigs()
            .withSupplyCurrentLimit(PivotConstants.kSupplyCurrentLimit)
            .withSupplyCurrentLimitEnable(PivotConstants.kSupplyLimitEnabled)
            .withStatorCurrentLimit(PivotConstants.kStatorCurrentLimit)
            .withStatorCurrentLimitEnable(PivotConstants.kStatorCurrentEnabled);

    final MotorOutputConfigs motorOutputConfig =
        new MotorOutputConfigs()
            .withNeutralMode(PivotConstants.kNeutralModeValue)
            .withInverted(PivotConstants.kInversion);

    final FeedbackConfigs feedback =
        new FeedbackConfigs()
            .withSensorToMechanismRatio(PivotConstants.kPivotGearRatio);

    final TalonFXConfiguration motorConfigurations = 
        new TalonFXConfiguration()
            .withCurrentLimits(currentLimits)
            .withMotorOutput(motorOutputConfig)
            .withFeedback(feedback);

    public Pivot(){
        configureMotor();
    }

    private void configureMotor(){
        pivotMotor.getConfigurator().apply(motorConfigurations);
    }

    private double kP = 0;
    private double kI = 0;
    private double kD = 0;

    private double kS = 0;
    private double kG = 0;
    private double kV = 0;

    private Constraints pivotConstraints = 
        new Constraints(
            PivotConstants.kMaxVelocity.in(DegreesPerSecond),
            PivotConstants.kMaxAcceleration.in(DegreesPerSecondPerSecond));

    private ProfiledPIDController pivotPIDController = 
        new ProfiledPIDController(
            kP, 
            kI, 
            kD, 
            pivotConstraints);
    
    private ArmFeedforward pivotFeedForward = 
            new ArmFeedforward(
                kS,
                kG,
                kV);

    public void goToAngle(Angle targetAngle){
        Angle currentAngle = getAngle();

        Voltage desiredPIDVoltage = Volts.of(
            pivotPIDController.calculate(
                currentAngle.in(Degrees), 
                targetAngle.in(Degrees)));
        
        Voltage desiredFFVoltage = Volts.of(
            pivotFeedForward.calculate(
                targetAngle.in(Degrees),
                0));
        
        Voltage desiredVoltage = Volts.of(
            desiredPIDVoltage.in(Volts) + desiredFFVoltage.in(Volts));

        pivotMotor.setVoltage(desiredVoltage.in(Volts));
    }

    @Logged(name = "pivotVoltage")
    public Voltage getVoltage(){
        Voltage voltage = pivotMotor.getMotorVoltage().getValue();
        return voltage;
    }

    @Logged(name = "pivotCurrent")
    public Current getCurrent(){
        Current current = pivotMotor.getStatorCurrent().getValue();
        return current;
    }

    @Logged(name = "pivotPitch")
    public Angle getAngle(){
        Angle angle = pivotMotor.getPosition().getValue();
        return angle;
    }

    @Logged(name = "atTargetAnglePivot")
    public boolean atTargetAngle(Angle targetAngle){
        boolean result =  Math.abs(getAngle().in(Degrees) - targetAngle.in(Degrees)) <= HoodConstants.kTargetAngleTolerance.in(Degrees);
        return result;
    }
}
