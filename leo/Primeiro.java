package leo;
import robocode.*;
//import java.awt.Color;
// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html
public class Primeiro extends AdvancedRobot
{
	public void run() {
		while(true) {	
			setAhead(100);
			setTurnRight(90);
			setAhead(100);
			setTurnRight(90);
			setAhead(100);
			setTurnRight(90);
			setAhead(100);
			setTurnRight(90);
			setTurnGunLeft(360);
			execute();
		}
	}
	public void onScannedRobot(ScannedRobotEvent e) {	
		fire(1);
	}
	public void onHitByBullet(HitByBulletEvent e) {
		ahead(80);
	}
	public void onHitWall(HitWallEvent e) {
		back(90);
	}	
}
