package me.jaskri.bedwars.Game.Phase;

import me.jaskri.bedwars.API.PACKAGE.Game.Game;
import me.jaskri.bedwars.API.PACKAGE.Game.GamePhase;
import me.jaskri.bedwars.API.PACKAGE.Generator.Resource;
import me.jaskri.bedwars.API.PACKAGE.Generator.TieredGenerator;

import java.util.Collection;
import java.util.Iterator;

public class DiamondUpgradePhase_2 extends GamePhase {

    public DiamondUpgradePhase_2(int duration) {
        super("Diamond II", duration);
    }

    public boolean apply(Game game) {
        if (game == null) {
            return false;
        } else {
            Collection<TieredGenerator> gens = game.getMapResourceGenerator(Resource.DIAMOND);
            Iterator var3 = gens.iterator();

            while(var3.hasNext()) {
                TieredGenerator gen = (TieredGenerator)var3.next();
                gen.setCurrentTier(2);
            }

            game.broadcastMessage("§bDiamond Generators §ehave been upgraded to Tier §cII");
            return false;
        }
    }
}
