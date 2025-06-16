package leo;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class Primeiro extends AdvancedRobot {
	double battlefieldY = 0;
	double battlefieldX = 0;

	public void run() {
		// Pegar as dimensões do campo de batalha
		battlefieldY = getBattleFieldHeight();
		battlefieldX = getBattleFieldWidth();
		
		// Alinha para o norte
		double angleToTurn = normalRelativeAngleDegrees(0 - getHeading());
		setTurnRight(angleToTurn);
		waitFor(new TurnCompleteCondition(this));

		// Começa indo para cima
		setAhead(getBattleFieldHeight());
		waitFor(new MoveCompleteCondition(this));

		// Loop principal de patrulha nas bordas
		while (true) {
			double currentHeading = getHeading();
			
			if (currentHeading == 0) {
				setTurnGunRight(90);
			} else if (currentHeading == 90) {
				setTurnGunRight(90);
			} else if (currentHeading == 180) {
				setTurnGunLeft(90);
			} else if (currentHeading == 270) {
				setTurnGunRight(90);
			} else if (currentHeading == 360) {
				setTurnGunLeft(90);
			}

			moverLado();
			moverBaixo();
			moverEsquerda();
			moverCima();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		fire(1);
	}

	public void onHitWall(HitWallEvent e) {
		// Apenas recua um pouco
		back(20);
	}

	public void moverLado() {
		double xrobo = getX();

		setTurnRight(90);
		waitFor(new TurnCompleteCondition(this));
		setAhead(battlefieldX - xrobo - 20);
		waitFor(new MoveCompleteCondition(this));
	}

	public void moverBaixo() {
		double yrobo = getY();

		setTurnRight(90);
		waitFor(new TurnCompleteCondition(this));
		setAhead(yrobo - 20);
		waitFor(new MoveCompleteCondition(this));
	}

	public void moverEsquerda() {
		double xrobo = getX();

		setTurnRight(90);
		waitFor(new TurnCompleteCondition(this));
		setAhead(xrobo - 20);
		waitFor(new MoveCompleteCondition(this));
	}

	public void moverCima() {
		double y = getBattleFieldHeight();
		double yrobo = getY();

		setTurnRight(90);
		waitFor(new TurnCompleteCondition(this));
		setAhead(battlefieldY - yrobo - 20);
		waitFor(new MoveCompleteCondition(this));
	}
}
