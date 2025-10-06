//Initializing code for our control hub

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.MecanumDrive;

public class ControlHub {
    //Driving Wheels
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public ElapsedTime timer; //uh, here's a timer i guess

    MecanumDrive drive;

    //Servo motors
    //public CRServo BasicServo;
    //public Servo RegularServo;

    public void init(HardwareMap map){
        //Names of each wheel
        frontLeft = map.get(DcMotor.class, "frontLeft");
        frontRight = map.get(DcMotor.class, "frontRight");
        backLeft = map.get(DcMotor.class, "backLeft");
        backRight = map.get(DcMotor.class, "backRight");

        //Reverse any wheels as needed
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        //drive = new MecanumDrive(map, initialPose);
    }
}
