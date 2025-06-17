package leo;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class Primeiro extends AdvancedRobot {
    double battlefieldY = 0;
    double battlefieldX = 0;
    
    // Flag para indicar se o robô está mirando em um inimigo
    boolean targetingEnemy = false;

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
            moverLado();
            moverBaixo();
            moverEsquerda();
            moverCima();
            
            // Se não estiver mirando em um inimigo, resetar o canhão para o norte
            if (!targetingEnemy) {
                resetGunToNorth();
            }
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        // Gira o canhão para a direção do inimigo
        double angleToEnemy = getHeading() + e.getBearing(); // Ângulo entre o robô e o inimigo
        double gunTurn = normalRelativeAngleDegrees(angleToEnemy - getGunHeading()); // Normaliza o ângulo do canhão

        setTurnGunRight(gunTurn);  // Gira o canhão na direção correta
        fire(1);  // Dispara no inimigo
        
        // Marca que está mirando em um inimigo
        targetingEnemy = true;
    }

    public void onHitWall(HitWallEvent e) {
        // Apenas recua um pouco
        back(20);
    }

    public void onRobotDeath(RobotDeathEvent e) {
        // Quando um inimigo morre, parar de mirar nele
        targetingEnemy = false;
    }

    // Reseta o canhão para o norte
    private void resetGunToNorth() {
        double gunHeading = getGunHeading();
        double angleToTurn = normalRelativeAngleDegrees(0 - gunHeading); // Direção norte
        setTurnGunRight(angleToTurn);  // Gira o canhão para o norte
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
