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
        hub.init(hardwareMap); //Initial position

        waitForStart();
        while(opModeIsActive()){ //Main loop
            motorAction(gamepad1);
        }
    }

    public void motorAction(Gamepad gamepad){ //Motor Code
        double xMove = gamepad.left_stick_x * 1.1; //Counteract imperfect strafing
        double yMove = -gamepad.left_stick_y; //y stick is reversed

        steerAngle = gamepad.right_stick_x; //Angle to turn by

        //Drift mode
        if(gamepad.left_trigger > 0.1){
            drift = 0.95;
        }else{
            drift = 0;
        }

        //Interpolate speed based on drift
        vx = drift * vx + (1 - drift) * xMove;
        vy = drift * vy + (1 - drift) * yMove;

        double speedDivisor = Math.max(Math.abs(vy + vx + steerAngle), Math.abs(vy - vx + steerAngle), Math.abs(vy + vx - steerAngle), Math.abs(vy - vx - steerAngle), 1)); //Limits all motor speeds from being more than the max (1)

        //Calculate individual motor speeds
        double frontLeftVel = (vy + vx + steerAngle) / speedDivisor;
        double backLeftVel = (vy - vx + steerAngle) / speedDivisor;
        double frontRightVel = (vy + vx - steerAngle) / speedDivisor;
        double backRightVel = (vy - vx - steerAngle) / speedDivisor;

        if(gamepad.x){ //Emergency movement stop
            frontLeftVel = 0;
            frontRightVel = 0;
            backLeftVel = 0;
            backRightVel = 0;
        }

        if(!gamepad.x){ //Set motor speeds
            hub.frontLeft.setPower(frontLeftVel);
            hub.frontRight.setPower(frontRightVel);
            hub.backLeft.setPower(backLeftVel);
            hub.backRight.setPower(backRightVel);
        }
    }
}
