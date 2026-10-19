package frc.robot.subsystems.pivot;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

public class Pivot {
    TalonFX armMotor = new TalonFX(PivotConstants.kPivotMotorID);

    final CurrentLimitsConfigs currentLimits = 
        new CurrentLimitsConfigs()
            .withSupplyCurrentLimit(PivotConstants.kSupplyCurrentLimit)
            .withSupplyCurrentLimitEnable(PivotConstants.kSupplyLimitEnabled)
            .withStatorCurrentLimit(PivotConstants.kStatorCurrentLimit)
            .withStatorCurrentLimitEnable(PivotConstants.kStatorCurrentEnabled);

    final TalonFXConfiguration motorConfiguration = new TalonFXConfiguration().withCurrentLimits(currentLimits);

    final MotorOutputConfigs motorOutputConfig =
        new MotorOutputConfigs()
            .withNeutralMode(PivotConstants.kNeutralModeValue);
}
