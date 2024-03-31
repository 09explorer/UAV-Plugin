package io.github.dominic.uavp2;

import io.github.dominic.uavp2.commands.*;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class UAV extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Plugin Activated!");
        getCommand("activate").setExecutor(new activateCommand());
        getCommand("uav").setExecutor(new helpCommand());
        getCommand("activatesecret").setExecutor(new activateBroken());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin Disabled!");
    }
}
