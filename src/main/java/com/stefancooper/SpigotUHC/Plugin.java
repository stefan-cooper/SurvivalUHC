package com.stefancooper.SpigotUHC;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin implements Listener {

    public void onEnable() { // This is called when the plugin is loaded into the server.
        Bukkit.getPluginManager().registerEvents(new Core(), this);
        System.out.println("UHC Plugin enabled");
    }

    public void onDisable() { // This is called when the plugin is unloaded from the server.

    }
}
