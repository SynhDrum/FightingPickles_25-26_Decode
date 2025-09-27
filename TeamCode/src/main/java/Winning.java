import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
@TeleOp(name="Winning") //Something
public class Winning extends LinearOpMode {
    ControlHub hub;
    @Override
    public void runOpMode() throws InterruptedException {
        hub = new ControlHub();
        hub.init(hardwareMap, new Pose2d(10, 10, 1));

        waitForStart();
        while (opModeIsActive()) {

            motorAction(gamepad1);


        }
    }

    public void motorAction(Gamepad gamepad) {
        double y = -gamepad.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad.right_stick_x;

        if(gamepad.left_trigger>.1){
            hub.leftWheel.setPower(gamepad.left_trigger);
        }
        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;
        if (gamepad.x){
            frontLeftPower=0;
            frontRightPower=0;
            backLeftPower=0;
            backRightPower=0;
        }

        if (!gamepad.x){
            hub.leftFront.setPower(frontLeftPower);
            hub.leftBack.setPower(backLeftPower);
            hub.rightFront.setPower(frontRightPower);
            hub.rightBack.setPower(backRightPower);
        }
    }
}
