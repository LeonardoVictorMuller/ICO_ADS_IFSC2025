package leo;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class Primeiro extends AdvancedRobot {

	public void run() {
		// Alinha para o norte
		double angleToTurn = normalRelativeAngleDegrees(0 - getHeading());
		setTurnRight(angleToTurn);
		waitFor(new TurnCompleteCondition(this));

		// Inicia o giro contínuo do canhão
		setTurnGunRight(Double.POSITIVE_INFINITY);

		// Começa indo para cima
		setAhead(getBattleFieldHeight());
		waitFor(new MoveCompleteCondition(this));

		// Loop principal de patrulha nas bordas
		while (true) {
			moverLado();
			moverBaixo();
			moverEsquerda();
			moverCima();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		fire(1); // Pode ajustar a potência com base na distância
	}

	public void onHitWall(HitWallEvent e) {
		// Apenas recua um pouco
		back(20);
	}

	public void moverLado() {
		double x = getBattleFieldWidth();
		double xrobo = getX();

		setTurnRight(90);
		waitFor(new TurnCompleteCondition(this));
		setAhead(x - xrobo - 20);
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
		setAhead(y - yrobo - 20);
		waitFor(new MoveCompleteCondition(this));
	}
}
