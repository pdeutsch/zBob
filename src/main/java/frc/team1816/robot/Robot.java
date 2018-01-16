package frc.team1816.robot;

import com.edinarobotics.utils.gamepad.Gamepad;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.team1816.robot.commands.AutonomousCommand;
import frc.team1816.robot.commands.DriveXInchesCommand;
import frc.team1816.robot.commands.GamepadDriveCommand;
import frc.team1816.robot.commands.RotateXDegreesCommand;
import frc.team1816.robot.subsystems.Drivetrain;

public class Robot extends IterativeRobot {

    private Drivetrain drivetrain;
    private double time;
    private SendableChooser<Command> autoChooser;
    private int _loops = 0;

    public void robotInit() {
        Components.getInstance();
        Controls.getInstance();

        drivetrain = Components.getInstance().drivetrain;

        autoChooser = new SendableChooser<>();
        autoChooser.addObject("Drive Forward Switch", new AutonomousCommand(AutonomousCommand.AutonomousMode.SWITCH_FORWARD));
        autoChooser.addObject("Drive & Turn Left Switch", new AutonomousCommand(AutonomousCommand.AutonomousMode.LEFT_SWITCH_FORWARD_TURN));
        autoChooser.addObject("Drive & Turn Right Switch", new AutonomousCommand(AutonomousCommand.AutonomousMode.RIGHT_SWITCH_FORWARD_TURN));
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void autonomousInit() {
        String gamedata;
        drivetrain.resetEncoders();
        gamedata = DriverStation.getInstance().getGameSpecificMessage();
        if(gamedata.charAt(0) == 'L') {
            //left auto
        } else {
            //right auto
        }

        Command autoCommand = new RotateXDegreesCommand(90);
        autoCommand.start();
    }

    @Override
    public void teleopInit() {
        Gamepad gamepad0 = Controls.getInstance().gamepad0;
        Gamepad gamepad1 = Controls.getInstance().gamepad1;

        drivetrain.resetEncoders();
        drivetrain.setDefaultCommand(new GamepadDriveCommand(gamepad0));
    }

    @Override
    public void testInit() {

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }
}