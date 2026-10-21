package frc.robot.subsystems.hood;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.units.measure.Angle;

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

    @Logged(name = "hoodPitch")
    public Angle getAngle(){
        Angle angle = hoodMotor.getPosition().getValue();
        return angle;
    }

    @Logged(name = "atTargetAngle")
    public boolean atTargetAngle(Angle targetAngle){
        boolean result =  Math.abs(getAngle().in(Degrees) - targetAngle.in(Degrees)) <= HoodConstants.kTargetAngleTolerance.in(Degrees);
        return result;
    }

}