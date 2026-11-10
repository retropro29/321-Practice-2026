package frc.robot.subsystems.hood.hoodCommands;

import static edu.wpi.first.units.Units.Volts;

import java.util.function.Supplier;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.hood.Hood;
import frc.robot.subsystems.hood.HoodConstants;

public class RunVoltage extends Command{
    
    Hood hood;
    Supplier <Voltage> volts;


    public RunVoltage(Hood hood, Supplier <Voltage> volts) {
        this.hood = hood;
        this.volts = volts;
        addRequirements(hood);
    }

    @Override
    public void initialize(){}

    @Override
    public void execute(){
        hood.runVolts(volts.get());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        // hood.zeroEncoder();
        hood.runVolts(Volts.of(0));
    }
}