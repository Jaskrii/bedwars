package me.jaskri.Configuration;

import com.google.common.base.Preconditions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class Configuration {

    protected FileConfiguration config;
    protected File file;

    public Configuration(File file) {
        Preconditions.checkNotNull(file, "File cannot be null");
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public void reloadConfig() {
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void createFile() {
        try {
            this.file.createNewFile();
        } catch (IOException var2) {
        }

    }

    public FileConfiguration getConfig() {
        if (this.config == null) {
            this.reloadConfig();
        }

        return this.config;
    }

    public void saveConfig() {
        if (this.config != null) {
            try {
                this.config.save(this.file);
            } catch (IOException var2) {
                var2.printStackTrace();
            }

        }
    }

    public abstract void saveDefaultConfig();
}
