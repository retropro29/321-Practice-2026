package frc.robot.subsystems.elevator;

import static edu.wpi.first.units.Units.Amps;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.measure.Current;

public class ElevatorConstants {
    
    public static final Current kMaxCurrent =  Amps.of(40);
    public static final Current kSupplyCurrentLimit = Amps.of(40);
    public static final boolean kSupplyLimitEnabled = true;

    public static final Current kStatorCurrentLimit = Amps.of(40);
    public static final boolean kStatorCurrentEnabled = true;

    public static final int kElevatorLeftMotorID = 12;
    public static final int kElevatorRightMotorID = 12;

    public static final double kElevatorGearRatio = 142.9;

    public static final InvertedValue kInversion = InvertedValue.Clockwise_Positive;
    public static final NeutralModeValue kNeutralModeValue = NeutralModeValue.Brake;
    
}
