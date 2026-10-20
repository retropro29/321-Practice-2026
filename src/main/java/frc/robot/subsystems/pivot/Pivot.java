package frc.robot.subsystems.pivot;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

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
}
