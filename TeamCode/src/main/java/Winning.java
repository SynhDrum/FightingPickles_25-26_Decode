//Winning Code 💯🔥🔥🗣️🗣️

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp(name="Winning")
public class Winning extends LinearOpMode {
    ControlHub hub; //Control hub (duh)

    double vx = 0; //Velocity x
    double vy = 0; //Velocity y
    double dir = 0; //Direction of robot
    double steerAngle = 0; //Angle of steering
    double drift = 0; //Amount of drift
    double dx = 0; //Drift Velocity X
    double dy = 0; //Drift Velocity Y

    static RevHubOrientationOnRobot.LogoFacingDirection[] logoFacingDirections = RevHubOrientationOnRobot.LogoFacingDirection.values();
    static RevHubOrientationOnRobot.UsbFacingDirection[] usbFacingDirections = RevHubOrientationOnRobot.UsbFacingDirection.values();

    IMU imu;
    int logoFacingDirectionPosition;
    int usbFacingDirectionPosition;
    boolean orientationIsValid = true;

    @Override
    public void runOpMode() throws InterruptedException {
        hub = new ControlHub();
        hub.init(hardwareMap); //Initially map hardware

        imu = hardwareMap.get(IMU.class, "imu");
        logoFacingDirectionPosition = 0; // Up
        usbFacingDirectionPosition = 2; // Forward

        RevHubOrientationOnRobot.LogoFacingDirection logo = logoFacingDirections[logoFacingDirectionPosition];
        RevHubOrientationOnRobot.UsbFacingDirection usb = usbFacingDirections[usbFacingDirectionPosition];
        try {
            RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logo, usb);
            imu.initialize(new IMU.Parameters(orientationOnRobot));
            orientationIsValid = true;
        } catch (IllegalArgumentException e) {
            orientationIsValid = false;
        }

        waitForStart();
        while(opModeIsActive()){ //Main loop
            motorAction(gamepad1);
        }
    }

    public void motorAction(Gamepad gamepad){ //Motor Code
        double xMove = gamepad.left_stick_x * 1.1; //Counteract imperfect strafing
        double yMove = -gamepad.left_stick_y; //y stick is reversed

        // Create an object to receive the IMU angles
        YawPitchRollAngles robotOrientation;
        robotOrientation = imu.getRobotYawPitchRollAngles();

        dir = robotOrientation.getYaw(AngleUnit.RADIANS);

        steerAngle = gamepad.right_stick_x; //Angle to turn by

        //Drift mode
        if(gamepad.left_trigger > 0.1){
            drift = 0.95;

            //Interpolate xy speed based on drift
            dx = drift * dx + (1 - drift) * xMove;
            dy = drift * dy + (1 - drift) * yMove;

            vx = dx / Math.cos(dir);
            vy = dy / Math.cos(dir);
        }else{
            drift = 0;
            vx = xMove;
            vy = yMove;
        }

        double speedDivisor = Math.max(Math.max(Math.max(Math.abs(vy + vx + steerAngle), Math.abs(vy - vx + steerAngle)), Math.max(Math.abs(vy + vx - steerAngle), Math.abs(vy - vx - steerAngle))), 1); //Limits all motor speeds from being more than the max (1)

        //Calculate individual motor speeds
        double frontLeftVel = (vy + vx + steerAngle) / speedDivisor;
        double backLeftVel = (vy - vx + steerAngle) / speedDivisor;
        double frontRightVel = (vy + vx - steerAngle) / speedDivisor;
        double backRightVel = (vy - vx - steerAngle) / speedDivisor;

        //Emergency movement stop
        if(gamepad.x) {
            frontLeftVel = 0;
            frontRightVel = 0;
            backLeftVel = 0;
            backRightVel = 0;
        }

        //Set motor speeds
        hub.frontLeft.setPower(frontLeftVel);
        hub.frontRight.setPower(frontRightVel);
        hub.backLeft.setPower(backLeftVel);
        hub.backRight.setPower(backRightVel);

        //Control Intake Motor
        if(gamepad.a){
            hub.intake.setPower(1);
        }else if(gamepad.b){
            hub.intake.setPower(-1);
        }else{
            hub.intake.setPower(0);
        }

        //Control Outtake Motor
        if(gamepad.y){
            hub.outtake.setPower(-1);
        }else{
            hub.outtake.setPower(0);
        }
    }
}
//Meowa