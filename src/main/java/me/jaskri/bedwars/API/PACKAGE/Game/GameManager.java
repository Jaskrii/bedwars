package me.jaskri.bedwars.API.PACKAGE.Game;

public interface GameManager {

    Game getGame();

    boolean start();

    boolean stop();

    GamePhase getCurrentPhase();

    long timeLeftForNextPhase();

    long gameLength();

    long currentTime();

    long timeLeft();
}
