package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.service.GameMaster;

public class Player extends DynamicObject {
    private static final int stepSize = 1;
    private int coinCount;
    private int state;
    public Player(int xPosition, int yPosition) {
        super(xPosition, yPosition, Configuration.PLAYER_SPRITE);
        this.coinCount = 0;
        this.state = 0;
    }

    public void move(Direction direction) {
        int tmpXPosition = getXPosition();
        int tmpYPosition = getYPosition();

        switch (direction) {
            case UP -> tmpYPosition -= stepSize;
            case DOWN -> tmpYPosition += stepSize;
            case LEFT -> tmpXPosition -= stepSize;
            case RIGHT -> tmpXPosition += stepSize;
        }

        if (super.isAllowedSurface(tmpXPosition, tmpYPosition) && isAllowedExit(tmpXPosition, tmpYPosition)) {
            xPosition = tmpXPosition;
            yPosition = tmpYPosition;
            if (isCoin(xPosition, yPosition)) {
                coinCount++;
            }
        }
    }
    private Boolean isAllowedExit(int x, int y){
        if (GameMaster.getInstance().getMap().getMap()[y][x] == Configuration.EXIT_CHARACTER) {
            if (coinCount < 9) {
                return false;
            }
            this.state = 1;
            return true;
        }
        return true;
    }
    private boolean isCoin(int x, int y) {
        for (GameObject o : GameMaster.getInstance().getCoinObj()) {
            if (o.getXPosition() == x && o.getYPosition() == y) {
                ((Coin) o).setCoinCol(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Player{[" + xPosition + ":" + yPosition + "]}" + "\nscore: " + coinCount + "/9";
    }

    public int getState() {
        return state;
    }
}
