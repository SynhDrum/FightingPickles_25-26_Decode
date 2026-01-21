//Winning Code 💯🔥🔥🗣️🗣️

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="WinningPower")
public class WinningPower extends LinearOpMode {
    ControlHub hub; //Control hub (duh)

    double TPR = 537.6;
    double targetRPM = 5239;
    double gearRatio = 6000.0 / 312.0;

    double vx = 0; //Velocity x
    double vy = 0; //Velocity y
    double dir = 0; //Direction of robot
    double steerAngle = 0; //Angle of steering
    double drift = 0; //Amount of drift
    double dx = 0; //Drift Velocity X
    double dy = 0; //Drift Velocity Y

    boolean leftTriggerOld = false;

    @Override
    public void runOpMode() throws InterruptedException {
        hub = new ControlHub();

        hub.init(hardwareMap, new Pose2d(new Vector2d(0,0),0)); //Initially map hardware

        FtcDashboard dash = FtcDashboard.getInstance();
        // Combine the standard telemetry and dashboard telemetry
        telemetry = new MultipleTelemetry(telemetry, dash.getTelemetry());

        waitForStart();
        while(opModeIsActive()){ //Main loop
            motorAction(gamepad1);
            double outtakeRPM = Math.abs((hub.outtake.getVelocity() / TPR * 60.0) * gearRatio);
            telemetry.addLine();
            telemetry.addData("Outtake RPM: ", outtakeRPM);
            telemetry.update();
        }
    }

    public void motorAction(Gamepad gamepad){ //Motor Code
        double xMove = gamepad.left_stick_x * 1.1; //Counteract imperfect strafing
        double yMove = -gamepad.left_stick_y; //y stick is reversed

        steerAngle = gamepad.right_stick_x; //Angle to turn by

        //Drift mode
        if(gamepad.left_trigger > 0.1){
            drift = 0.95;

            //Interpolate xy speed based on drift
            dx = drift * dx + (1 - drift) * xMove;
            dy = drift * dy + (1 - drift) * yMove;

            vx = dx / Math.cos(dir);
            vy = dy / Math.sin(dir);
        }else{
            drift = 0;
            vx = xMove;
            vy = yMove;
        }

        double speedDivisor = Math.max(Math.max(Math.max(Math.abs(vy + vx + steerAngle), Math.abs(vy - vx + steerAngle)), Math.max(Math.abs(vy + vx - steerAngle), Math.abs(vy - vx - steerAngle))), 1); //Limits all motor speeds from being more than the max (1)

        //Calculate individual motor speeds
        double frontLeftVel = (vy + vx + steerAngle) / speedDivisor;
        double backLeftVel = (vy - vx + steerAngle) / speedDivisor;
        double frontRightVel = (vy - vx - steerAngle) / speedDivisor;
        double backRightVel = (vy + vx - steerAngle) / speedDivisor;

        //Emergency movement stop
        if(gamepad.x) {
            frontLeftVel = 0;
            frontRightVel = 0;
            backLeftVel = 0;
            backRightVel = 0;
        }

        //Set motor speeds
        hub.drive.frontLeft.setPower(frontLeftVel);
        hub.drive.frontRight.setPower(frontRightVel);
        hub.drive.backLeft.setPower(backLeftVel);
        hub.drive.backRight.setPower(backRightVel);

        //Control Intake Motor
        if(gamepad.right_trigger > 0.1){
            hub.intake.setPower(1); //Intake Out
        }else if(gamepad.right_bumper){
            hub.intake.setPower(-1); //Intake In
        }else{
            hub.intake.setPower(0); //Intake Off
        }

        //Control Pusher Motor
        if(gamepad.dpad_up){
            hub.pusher.setPower(-1); //Pusher Out
        }else if(gamepad.dpad_down){
            hub.pusher.setPower(1); //Pusher Out
        }else{
            hub.pusher.setPower(0); //Pusher Off
        }

        //Control Launch

        boolean leftTrigger = (gamepad.left_trigger > 0.1); //Left Trigger

        //Start new timer on left trigger
        if(leftTrigger && !leftTriggerOld) {
            hub.timer = new ElapsedTime();
        }

        if(leftTrigger != leftTriggerOld){
            leftTriggerOld = leftTrigger; //Update old variable
        }

        if(hub.timer != null)
        {
            //Outtake Launch
            if(leftTrigger){
                //Outtake Pusher
                if(hub.timer.seconds() >= 3) /*|| (hub.timer.seconds() >= 5 && hub.timer.seconds() < 6.8))*/{
                    hub.pusher.setPower(-1); //Pusher Out
                }else{
                    hub.pusher.setPower(0); //Pusher Off
                }
                if((hub.timer.seconds() >= 3 && hub.timer.seconds() < 3.3) || (hub.timer.seconds() >= 3.9 && hub.timer.seconds() < 4.2) || (hub.timer.seconds() >= 4.8 && hub.timer.seconds() < 5.7)){
                    hub.intake.setPower(-1); //Intake Out
                }else{
                    hub.intake.setPower(0); //Intake Off
                }

                if(gamepad.y){
                    hub.outtake.setVelocity(-1 * (targetRPM / gearRatio / 60) * TPR); //Reverse Outtake
                }else{
                    hub.outtake.setPower(-0.85); //Outtake Out
                }
            }else if(gamepad.left_bumper){
                hub.outtake.setVelocity((targetRPM / gearRatio / 60) * TPR); //Reverse Outtake
            }else{
                hub.outtake.setPower(0); //Outtake Off
            }
        }
    }
}
//Meowa