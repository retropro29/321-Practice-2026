package frc.robot.subsystems.hood;

import static edu.wpi.first.units.Units.Amps;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.measure.Current;

public class HoodConstants {

    public static final Current kMaxCurrent = Amps.of(40);
    public static final Current kSupplyCurrentLimit = Amps.of(40);
    public static final boolean kSupplyLimitEnabled = true;

    public static final Current kStatorCurrentLimit = Amps.of(40);
    public static final boolean kStatorCurrentEnabled = true;
    //public static final Voltage kMaxVoltage = Volts.of(12);


    public static final int kHoodMotorID = 11;
    public static final double kHoodGearRatio = 142.9;

    public static final InvertedValue kInversion = InvertedValue.Clockwise_Positive;
    public static final NeutralModeValue kNeutralModeValue = NeutralModeValue.Brake;


    // public static final Angle kFeedAngle = Degrees.of(15);                      //Converts value into degrees
    // public static final double kFeedAngleDouble = kFeedAngle.in(Radians);       //Converts degrees into double
    // public static final double kFeedAngleDoubleRad = kFeedAngle.in(Radians);    //High-key too lazy to think of a good name


}
