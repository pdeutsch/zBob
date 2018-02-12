package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;


public class LeftAutoStartScaleCommand extends CommandGroup {
    char target;

    public LeftAutoStartScaleCommand() {

    }

    public void selectAuto() {
        try {
            target = DriverStation.getInstance().getGameSpecificMessage().charAt(0);
            System.out.println("Scale Auto Selection: " + target);
        } catch (Exception e) {
            System.out.println("NO TARGET");
            target = 'n';
        }

        if (target == 'L') {
            System.out.println("Left Start Scale Auto ---- Target: L");

            addSequential(new DriveXInchesCommand(324, 0.8));
            addSequential(new RotateXDegreesCommand(90));
            addSequential(new DriveXInchesCommand(12, 0.5));

            addSequential(new WaitCommand(1));

//            addSequential(new WaitCommand(0.2));
//            addSequential(new RaiseElevatorCommand());
//            addSequential(new WaitCommand(0.2));
//            addSequential(new LowerElevatorCommand());
//            addSequential(new WaitCommand(0.2));

            addSequential(new DriveXInchesCommand(12, -0.5));
            addSequential(new RotateXDegreesCommand(-90));
            addSequential(new DriveXInchesCommand(24, 0.5));

        } else if (target == 'R') {
            System.out.println("Left Start Scale Auto ---- Target: R");

            addSequential(new DriveXInchesCommand(185,0.8));
            addSequential(new RotateXDegreesCommand(90));
            addSequential(new DriveXInchesCommand(170, 0.8));
            addSequential(new RotateXDegreesCommand(-90));
            addSequential(new DriveXInchesCommand(12,0.5));

            addSequential(new WaitCommand(1));

//            addSequential(new WaitCommand(0.2));
//            addSequential(new RaiseElevatorCommand());
//            addSequential(new WaitCommand(0.2));
//            addSequential(new LowerElevatorCommand());
//            addSequential(new WaitCommand(0.2));

            addSequential(new DriveXInchesCommand(12, -0.5));

        } else {
            System.out.println("Left Start Scale Auto ---- Auto-Run");
            addSequential(new DriveXInchesCommand(140,0.5));
        }
    }
}
