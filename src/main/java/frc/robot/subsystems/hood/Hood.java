package frc.robot.subsystems.hood;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

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

}