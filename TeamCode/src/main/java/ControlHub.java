import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.MecanumDrive;

public class ControlHub {
    public DcMotor leftFront;
    public DcMotor rightFront;
    public DcMotor leftBack;
    public DcMotor rightBack;
    public ElapsedTime timer;
    MecanumDrive drive;
    //public CRServo BasicServo;
    //public Servo RegularServo;
    //public DcMotor leftWheel;
    //public DcMotor rightWheel;
}
