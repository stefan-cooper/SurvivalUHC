package com.stefancooper.SurvivalUHC;

import com.stefancooper.SurvivalUHC.commands.PvpCommand;
import com.stefancooper.SurvivalUHC.commands.SetConfigCommand;
import com.stefancooper.SurvivalUHC.commands.UnsetConfigCommand;
import com.stefancooper.SurvivalUHC.commands.ViewConfigCommand;
import com.stefancooper.SurvivalUHC.events.BaseEvents;
import com.stefancooper.SurvivalUHC.events.ReviveEvents;
import com.stefancooper.SurvivalUHC.utils.UHCCommandTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Arrays;

public class Plugin extends JavaPlugin implements Listener {

    private Config config;

    // This is called when the plugin is loaded into the server.
    public void onEnable() {
        config = new Config(this);
        Defaults.setDefaultGameRules(this.config);
        Bukkit.getPluginManager().registerEvents(new BaseEvents(config), this);
        Bukkit.getPluginManager().registerEvents(new ReviveEvents(config), this);
        System.out.println("UHC Plugin enabled");
        getCommand("uhc").setTabCompleter(new UHCCommandTabCompleter());
    }

    public Config getUHCConfig() {
        return config;
    }

    // This is called when the plugin is unloaded from the server.
    public void onDisable() {}

    /** Used to pass to child commands so that we don't pass the command key to them */
    private String[] getCommandArgs (String[] allArgs) {
        return Arrays.copyOfRange(allArgs, 1, allArgs.length);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equals("uhc") && args.length > 0) {
            switch (args[0]) {
                case SetConfigCommand.COMMAND_KEY:
                    new SetConfigCommand(sender, cmd, getCommandArgs(args), config).execute();
                    return true;
                case UnsetConfigCommand.COMMAND_KEY:
                    new UnsetConfigCommand(sender, cmd, getCommandArgs(args), config).execute();
                    return true;
                case ViewConfigCommand.COMMAND_KEY:
                    new ViewConfigCommand(sender, cmd, getCommandArgs(args), config).execute();
                    return true;
                case PvpCommand.COMMAND_KEY:
                    new PvpCommand(sender, cmd, getCommandArgs(args), config).execute();
                    return true;
                default:
                    break;
            }
        }
        return false;
    }
}
