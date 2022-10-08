package me.jaskri.Prestige;

import me.jaskri.Configuration.Configuration;
import me.jaskri.API.Prestige.Prestige;
import me.jaskri.bedwars.Bedwars;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.Iterator;

public class PrestigeConfig extends Configuration {

    private static PrestigeConfig instance;
    private Prestige default_prestige;

    private PrestigeConfig() {
        super(new File(Bedwars.getInstance().getDataFolder(), "Prestige.yml"));
        this.saveDefaultConfig();
    }

    public Prestige getDefaultPrestige() {
        if (this.default_prestige != null) {
            return this.default_prestige;
        } else {
            this.default_prestige = this.getPrestige(this.getConfig().getString("Default"));
            if (this.default_prestige == null) {
                this.default_prestige = Prestige.DEFAULT;
            }

            return this.default_prestige;
        }
    }

    public Prestige getPrestige(String name) {
        if (name == null) {
            return null;
        } else {
            String displayName = this.getConfig().getString("Prestige." + name + ".display-name");
            if (displayName == null) {
                return null;
            } else {
                String format = this.config.getString("Prestige." + name + ".format.chat");
                if (format == null) {
                    return null;
                } else {
                    String sbFormat = this.config.getString("Prestige." + name + ".format.scoreboard");
                    if (sbFormat == null) {
                        return null;
                    } else {
                        int start = this.config.getInt("Prestige." + name + ".start");
                        int end = this.config.getInt("Prestige." + name + ".end");
                        return start >= 0 && end >= 0 && end >= start ? new Prestige(name, ChatUtils.format(displayName), format, sbFormat, start, end) : null;
                    }
                }
            }
        }
    }

    public void loadPrestiges() {
        this.reloadConfig();
        ConfigurationSection section = this.config.getConfigurationSection("Prestige");
        if (section != null) {
            Iterator var2 = section.getKeys(false).iterator();

            while(var2.hasNext()) {
                String key = (String)var2.next();
                Prestige prestige = this.getPrestige(key);
                if (prestige != null) {
                    Prestige.registerPrestige(prestige);
                }
            }

        }
    }

    public void saveDefaultConfig() {
        if (!this.file.exists()) {
            Bedwars.getInstance().saveResource("Prestige.yml", false);
        }

    }

    public static PrestigeConfig getInstance() {
        if (instance == null) {
            instance = new PrestigeConfig();
        }

        return instance;
    }
}
