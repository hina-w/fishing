package cskies_hina.fishing;

import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.configuration.file.*;

public class Fishing extends JavaPlugin {
    // This code is called after the server starts and after the /reload command
    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "{0}.onEnable()", this.getClass().getName());
        
        FileConfiguration config = this.getConfig();
        config.addDefault("callOnSuccess", "tellraw @s {\"text\":\"Fishing Success!\"}");
        config.addDefault("weight", "1.0");
        //config.addDefault("delimiter", "；；");
        config.addDefault("p_max_distance", 16);
        config.options().copyDefaults(true);
        saveConfig();
        
        getServer().getPluginManager().registerEvents(new PlayerFishingListener(config), this);
    }

    // This code is called before the server stops and after the /reload command
    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "{0}.onDisable()", this.getClass().getName());
    }
}
