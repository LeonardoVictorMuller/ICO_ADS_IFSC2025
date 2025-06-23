package leo;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class RoboParedes extends AdvancedRobot {
    double battlefieldY = 0;
    double battlefieldX = 0;

    long lastScanTime = 0;

    int scanDirection = 1;

    public void run() {
        // Pegar as dimensões do campo de batalha
        battlefieldY = getBattleFieldHeight();
        battlefieldX = getBattleFieldWidth();

        // Configurações independentes para movimento, giro do corpo e canhão
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);

        // Alinha para o norte
        double angleToTurn = normalRelativeAngleDegrees(0 - getHeading());
        setTurnRight(angleToTurn);
        waitFor(new TurnCompleteCondition(this));

        // Começa indo para cima
        setAhead(getBattleFieldHeight());
        waitFor(new MoveCompleteCondition(this));

        // Loop principal de patrulha nas bordas
        while (true) {
            // Sempre manter o radar girando procurando inimigos
            setTurnRadarRight(360 * scanDirection);

            moverLado();
            moverBaixo();
            moverEsquerda();
            moverCima();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        // Atualiza o tempo do último scan
        lastScanTime = getTime();

        // Para o radar quando encontrar um inimigo
        setTurnRadarRight(0);

        // Gira o canhão para a direção do inimigo
        double angleToEnemy = getHeading() + e.getBearing();
        double gunTurn = normalRelativeAngleDegrees(angleToEnemy - getGunHeading());

        setTurnGunRight(gunTurn);
        fire(1);

        // Mantém o radar focado no inimigo com pequenos ajustes
        double radarTurn = normalRelativeAngleDegrees(angleToEnemy - getRadarHeading());
        setTurnRadarRight(radarTurn * 2);
    }

    public void onHitWall(HitWallEvent e) {
        // Apenas recua um pouco
        back(20);
    }

    public void onRobotDeath(RobotDeathEvent e) {
        // Quando um inimigo morre, resetar o tempo de scan e voltar a procurar
        lastScanTime = 0;
        // Inverte a direção de busca ocasionalmente
        if (Math.random() < 0.3) {
            scanDirection *= -1;
        }
        setTurnRadarRight(360 * scanDirection);
    }

    public void moverLado() {
        double xrobo = getX();

        setTurnRight(90);
        waitFor(new TurnCompleteCondition(this));
        setAhead(battlefieldX - xrobo - 20);

        // Se perdeu o contato com inimigos, continuar girando o radar
        if (getTime() - lastScanTime > 3) {
            setTurnRadarRight(360 * scanDirection);
        }

        waitFor(new MoveCompleteCondition(this));
    }

    public void moverBaixo() {
        double yrobo = getY();

        setTurnRight(90);
        waitFor(new TurnCompleteCondition(this));
        setAhead(yrobo - 20);

        // Se perdeu o contato com inimigos, continuar girando o radar
        if (getTime() - lastScanTime > 3) {
            setTurnRadarRight(360 * scanDirection);
        }

        waitFor(new MoveCompleteCondition(this));
    }

    public void moverEsquerda() {
        double xrobo = getX();

        setTurnRight(90);
        waitFor(new TurnCompleteCondition(this));
        setAhead(xrobo - 20);

        // Se perdeu o contato com inimigos, continuar girando o radar
        if (getTime() - lastScanTime > 3) {
            setTurnRadarRight(360 * scanDirection);
        }

        waitFor(new MoveCompleteCondition(this));
    }

    public void moverCima() {
        double y = getBattleFieldHeight();
        double yrobo = getY();

        setTurnRight(90);
        waitFor(new TurnCompleteCondition(this));
        setAhead(battlefieldY - yrobo - 20);

        // Se perdeu o contato com inimigos, continuar girando o radar
        if (getTime() - lastScanTime > 3) {
            setTurnRadarRight(360 * scanDirection);
        }

        waitFor(new MoveCompleteCondition(this));
    }
}