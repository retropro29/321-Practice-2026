package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

public class Elevator {
    TalonFX elevatorLeftMotor = new TalonFX(ElevatorConstants.kElevatorLeftMotorID);
    TalonFX elevatorRightMotor = new TalonFX(ElevatorConstants.kElevatorLeftMotorID);


    final CurrentLimitsConfigs currentLimits = 
        new CurrentLimitsConfigs()
            .withSupplyCurrentLimit(ElevatorConstants.kSupplyCurrentLimit)
            .withSupplyCurrentLimitEnable(ElevatorConstants.kSupplyLimitEnabled)
            .withStatorCurrentLimit(ElevatorConstants.kStatorCurrentLimit)
            .withStatorCurrentLimitEnable(ElevatorConstants.kStatorCurrentEnabled);

    final MotorOutputConfigs motorOutputConfig =
        new MotorOutputConfigs()
            .withNeutralMode(ElevatorConstants.kNeutralModeValue)
            .withInverted(ElevatorConstants.kInversion);

    final FeedbackConfigs feedback =
        new FeedbackConfigs()
            .withSensorToMechanismRatio(ElevatorConstants.kElevatorGearRatio);

    final TalonFXConfiguration motorConfigurations = 
        new TalonFXConfiguration()
            .withCurrentLimits(currentLimits)
            .withMotorOutput(motorOutputConfig)
            .withFeedback(feedback);


    public Elevator(){
        configureMotor();
    }

    private void configureMotor(){
        elevatorLeftMotor.getConfigurator().apply(motorConfigurations);
        elevatorRightMotor.getConfigurator().apply(motorConfigurations);
    }
}
