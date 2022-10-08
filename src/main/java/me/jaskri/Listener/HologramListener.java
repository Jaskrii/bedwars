package me.jaskri.Listener;

import me.jaskri.Hologram.BedwarsHologram;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class HologramListener implements Listener {

    public HologramListener() {
    }

    @EventHandler
    public void onArmorStandInteract(PlayerArmorStandManipulateEvent event) {
        if (BedwarsHologram.isHologram(event.getRightClicked())) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onArmorStandDamage(EntityDamageEvent event) {
        if (BedwarsHologram.isHologram(event.getEntity())) {
            event.setCancelled(true);
        }

    }
}
