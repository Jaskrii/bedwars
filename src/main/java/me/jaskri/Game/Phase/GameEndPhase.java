package me.jaskri.Game.Phase;

import me.jaskri.API.Game.Game;
import me.jaskri.API.Game.GamePhase;

public class GameEndPhase extends GamePhase {

    public GameEndPhase(int duration) {
        super("Game End", duration);
    }

    public boolean apply(Game game) {
        return game == null ? false : game.stopGame();
    }
}
