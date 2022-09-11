package me.jaskri.bedwars.Game.Phase;

import me.jaskri.bedwars.API.PACKAGE.Game.Game;
import me.jaskri.bedwars.API.PACKAGE.Game.GamePhase;

public class GameEndPhase extends GamePhase {

    public GameEndPhase(int duration) {
        super("Game End", duration);
    }

    public boolean apply(Game game) {
        return game == null ? false : game.stopGame();
    }
}
