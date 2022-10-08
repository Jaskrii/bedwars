package me.jaskri.API.Game;

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
