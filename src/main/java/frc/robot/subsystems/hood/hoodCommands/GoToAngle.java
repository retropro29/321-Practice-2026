package frc.robot.subsystems.hood.hoodCommands;

import static edu.wpi.first.units.Units.Volts;

import java.util.function.Supplier;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.hood.Hood;

public class GoToAngle extends Command{
    Hood hood;
    Supplier<Angle> targetAngle;

    public GoToAngle(Hood hood, Supplier<Angle> targetAngle){
        this.hood = hood;
        this.targetAngle = targetAngle;
        addRequirements(hood);
    }

    @Override
    public void initialize(){}

    @Override
    public void execute(){
        hood.goToAngle(targetAngle.get());
    }

    @Override
    public boolean isFinished(){
        return hood.atTargetAngle(targetAngle.get());
    }

    @Override
    public void end(boolean interrupted){
        hood.runVolts(Volts.of(0));
    }



}
