package com.stefancooper.SpigotUHC;

import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin implements Listener {

    public void onEnable() { // This is called when the plugin is loaded into the server.
        Bukkit.getPluginManager().registerEvents(new Core(), this);
        System.out.println("UHC Plugin enabled");
    }

    public void onDisable() { // This is called when the plugin is unloaded from the server.

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equals("uhc")) {
            if (args[0].equals("--config") && args[1].startsWith("world.border=")) {
                String newSize = args[1].split("=")[1];
                try {
                    WorldBorder worldBorder = Bukkit.getWorld("world").getWorldBorder();
                    worldBorder.setSize(Double.parseDouble(newSize));
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }
}
