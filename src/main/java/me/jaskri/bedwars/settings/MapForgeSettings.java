package me.jaskri.bedwars.settings;

import me.jaskri.API.Game.GameMode;
import me.jaskri.API.Generator.GeneratorTier;
import me.jaskri.API.Generator.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapForgeSettings {

    private static final MapForgeSettings INSTANCE = new MapForgeSettings();
    private Map<GameMode, Map<Resource, List<GeneratorTier>>> tiers = new HashMap();

    private MapForgeSettings() {
        GameMode[] var1 = GameMode.values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            GameMode mode = var1[var3];
            Map<Resource, List<GeneratorTier>> tiersMap = new HashMap(2);
            int limit1 = mode != GameMode.SOLO && mode != GameMode.DUO ? 8 : 4;
            List<GeneratorTier> tiers = new ArrayList();
            tiers.add(new GeneratorTier("&eTier &cI", 30, limit1));
            tiers.add(new GeneratorTier("&eTier &cII", 23, limit1));
            tiers.add(new GeneratorTier("&eTier &cIII", 12, limit1));
            int limit2 = mode != GameMode.SOLO && mode != GameMode.DUO ? 4 : 2;
            List<GeneratorTier> tiers2 = new ArrayList();
            tiers2.add(new GeneratorTier("&eTier &cI", 60, limit2));
            tiers2.add(new GeneratorTier("&eTier &cII", 45, limit2));
            tiers2.add(new GeneratorTier("&eTier &cIII", 30, limit2));
            tiersMap.put(Resource.DIAMOND, tiers);
            tiersMap.put(Resource.EMERALD, tiers2);
            this.tiers.put(mode, tiersMap);
        }

    }

    public Map<Resource, List<GeneratorTier>> getTiers(GameMode mode) {
        if (mode == null) {
            return null;
        } else {
            Map<Resource, List<GeneratorTier>> result = (Map)this.tiers.get(mode);
            return result != null ? new HashMap(result) : null;
        }
    }

    public void setTiers(GameMode mode, Map<Resource, List<GeneratorTier>> tiers) {
        if (mode != null && tiers != null) {
            this.tiers.put(mode, tiers);
        }

    }

    public List<GeneratorTier> getGeneratorTiers(GameMode mode, Resource resource) {
        if (mode != null && resource != null) {
            Map<Resource, List<GeneratorTier>> result = (Map)this.tiers.get(mode);
            if (result == null) {
                return null;
            } else {
                List<GeneratorTier> tiers = (List)result.get(resource);
                return tiers != null ? new ArrayList(tiers) : null;
            }
        } else {
            return null;
        }
    }

    public void setGeneratorTiers(GameMode mode, Resource resource, List<GeneratorTier> tiers) {
        if (mode != null && resource != null && tiers != null) {
            Map<Resource, List<GeneratorTier>> result = (Map)this.tiers.get(mode);
            if (result == null) {
                this.tiers.put(mode, result = new HashMap());
            }

            ((Map)result).put(resource, tiers);
        }
    }

    public static MapForgeSettings getInstance() {
        return INSTANCE;
    }
}
