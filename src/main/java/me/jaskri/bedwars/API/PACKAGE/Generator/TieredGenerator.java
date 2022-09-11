package me.jaskri.bedwars.API.PACKAGE.Generator;

import java.util.List;

public interface TieredGenerator extends ItemGenerator{

    List<GeneratorTier> getTiers();

    GeneratorTier getCurrentTier();

    void setCurrentTier(int var1);

    Resource getDrop();
}
