//Winning Code 💯🔥🔥🗣️🗣️

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
@TeleOp(name="Winning")
public class Winning extends LinearOpMode {
    ControlHub hub; //Control hub (duh)

    double vx = 0; //Velocity x
    double vy = 0; //Velocity y
    double steerAngle = 0; //Angle of steering
    double drift = 0; //Amount of drift

    @Override
    public void runOpMode() throws InterruptedException {
        hub = new ControlHub();
        hub.init(hardwareMap, new Pose2d(10, 10, 1)); //Initial position

        waitForStart();
        while(opModeIsActive()){ //Main loop
            motorAction(gamepad1);
        }
    }

    public void motorAction(Gamepad gamepad){ //Motor Code
        double xMove = gamepad.left_stick_x * 1.1; //Counteract imperfect strafing
        double yMove = -gamepad.left_stick_y; //y stick is reversed

        if(xMove != 0 || yMove != 0){
            steerAngle = gamepad.right_stick_x; //Angle analogous to the front wheels on a car
        }else{
            steerAngle = 0;
        }

        //Drift mode
        if(gamepad.right_trigger > 0.1){
            drift = 0.95;
        }else{
            drift /= -2;
        }

        //Interpolate speed based on drift
        vx = drift * vx + (1 - drift) * xMove;
        vy = drift * vy + (1 - drift) * yMove;

        double speedDivisor = Math.max(Math.abs(vx) + Math.abs(vy), 1); //Limits a motor speed from being more than the max (1)

        //Calculate individual motor speeds
        double frontLeftPower = (vy + vx + steerAngle) / speedDivisor;
        double backLeftPower = (vy - vx + steerAngle) / speedDivisor;
        double frontRightPower = (vy - vx - steerAngle) / speedDivisor;
        double backRightPower = (vy + vx - steerAngle) / speedDivisor;

        if(gamepad.x){ //Emergency stop
            frontLeftPower=0;
            frontRightPower=0;
            backLeftPower=0;
            backRightPower=0;
        }

        if(!gamepad.x){ //Set motor powers
            hub.frontLeft.setPower(frontLeftPower);
            hub.frontRight.setPower(frontRightPower);
            hub.backLeft.setPower(backLeftPower);
            hub.backRight.setPower(backRightPower);
        }
    }
}
