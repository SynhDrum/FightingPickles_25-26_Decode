import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
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

        while(hub.timer.seconds() < 4){
            hub.frontLeft.setPower(0.1);
            hub.frontRight.setPower(0.1);
            hub.backLeft.setPower(0.1);
            hub.backRight.setPower(0.1);
        }
    }
}
