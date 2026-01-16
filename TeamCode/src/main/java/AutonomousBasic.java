import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "AutonomousBasic")
public class AutonomousBasic extends LinearOpMode {
    ControlHub hub;

    double TPR = 537.6;

    @Override
    public void runOpMode() throws InterruptedException {
        hub = new ControlHub();
        hub.init(hardwareMap, new Pose2d(new Vector2d(0,0),Math.toRadians(0))); //Initially map hardware
        waitForStart();
        hub.timer = new ElapsedTime();

        while(hub.timer.seconds() < 4.5){
            hub.drive.frontLeft.setPower(0.1);
            hub.drive.frontRight.setPower(0.1);
            hub.drive.backLeft.setPower(0.1);
            hub.drive.backRight.setPower(0.1);
        }

        hub.drive.frontLeft.setPower(0);
        hub.drive.frontRight.setPower(0);
        hub.drive.backLeft.setPower(0);
        hub.drive.backRight.setPower(0);

        hub.timer = new ElapsedTime();
        while(hub.timer.seconds() < 5.7) {
            if (hub.timer.seconds() >= 3 && hub.timer.seconds() < 5.7) {
                hub.pusher.setPower(-1); //Pusher Out
            } else {
                hub.pusher.setPower(0); //Pusher Off
            }

            if(hub.timer.seconds() >= 3 && hub.timer.seconds() < 3.3) || (hub.timer.seconds() >= 3.9 && hub.timer.seconds() < 4.2) || (hub.timer.seconds() >= 4.8 && hub.timer.seconds() < 5.7){
                hub.intake.setPower(-1); //Intake Out
            }else{
                hub.intake.setPower(0); //Intake Off
            }

            hub.outtake.setVelocity(-1 * (5239 / 60) * TPR); //Outtake Out
        }

        hub.outtake.setPower(0); //Outtake Out
    }
}
