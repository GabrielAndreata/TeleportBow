package dev.g3b.teleportbow;

import dev.g3b.teleportbow.commands.GiveCommand;
import dev.g3b.teleportbow.listeners.BowListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class TeleportBow extends JavaPlugin {

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults();
        saveDefaultConfig();


        getCommand("givebow").setExecutor(new GiveCommand(this));
        getServer().getPluginManager().registerEvents(new BowListener(this), this);
    }
}
