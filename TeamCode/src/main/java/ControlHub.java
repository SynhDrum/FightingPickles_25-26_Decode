import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.MecanumDrive;

public class ControlHub {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public ElapsedTime timer;
    MecanumDrive drive;
    //public CRServo BasicServo;
    //public Servo RegularServo;
    public DcMotor leftWheel;
    public DcMotor rightWheel;

    public void init(HardwareMap map, Pose2d initialPose) {
        frontLeft = map.get(DcMotor.class, "frontLeft");
        frontRight = map.get(DcMotor.class, "frontRight");
        backLeft = map.get(DcMotor.class, "beckLeft");
        backRight = map.get(DcMotor.class, "backRight");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //This is one of the motors to move the arm up and down
        leftWheel = map.get(DcMotor.class, "leftWheel");
        //This is one of the motors to move the arm up and down
        rightWheel = map.get(DcMotor.class, "rightWheel");

        drive=new MecanumDrive(map,initialPose);
    }
}
