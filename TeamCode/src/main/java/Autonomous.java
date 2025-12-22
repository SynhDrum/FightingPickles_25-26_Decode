import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Timer;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Autonomous")
public class Autonomous extends LinearOpMode {
    ControlHub hub;

    @Override
    public void runOpMode() throws InterruptedException {
        hub = new ControlHub();
        hub.init(hardwareMap, new Pose2d(new Vector2d(0,0),Math.toRadians(0))); //Initially map hardware
        waitForStart();
        hub.timer = new ElapsedTime();

        int ballOrder = 1;

        switch(ballOrder){
            case(1):
                Action action_Movement_A = hub.drive.actionBuilder(new Pose2d(62, 8, 180))
                        .strafeToLinearHeading(new Vector2d(37, 28), Math.toRadians(90))
                        .strafeToLinearHeading(new Vector2d(37, 52), Math.toRadians(90))
                        .strafeToLinearHeading (new Vector2d(37, 28), Math.toRadians(90))
                        .build();
                Actions.runBlocking(action_Movement_A);
                break;
            case(2):
                Action action_Movement_B = hub.drive.actionBuilder(new Pose2d(70, 0, 0))
                        .strafeToLinearHeading(new Vector2d(13, 28), Math.toRadians(90))
                        .strafeToLinearHeading(new Vector2d(13, 52), Math.toRadians(90))
                        .strafeToLinearHeading (new Vector2d(13, 28), Math.toRadians(90))
                        .build();
                Actions.runBlocking(action_Movement_B);
                break;
            case(3):
                Action action_Movement_C = hub.drive.actionBuilder(new Pose2d(70, 0, 0))
                        .strafeToLinearHeading(new Vector2d(-13, 28), Math.toRadians(90))
                        .strafeToLinearHeading(new Vector2d(-13, 52), Math.toRadians(90))
                        .strafeToLinearHeading (new Vector2d(-13, 28), Math.toRadians(90))
                        .build();
                Actions.runBlocking(action_Movement_C);
                break;
        }





        while(hub.timer.seconds() < 4){
            hub.frontLeft.setPower(0.1);
            hub.frontRight.setPower(0.1);
            hub.backLeft.setPower(0.1);
            hub.backRight.setPower(0.1);
        }
    }
}
