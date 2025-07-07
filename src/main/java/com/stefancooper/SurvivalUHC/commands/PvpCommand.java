package com.stefancooper.SurvivalUHC.commands;

import com.stefancooper.SurvivalUHC.Config;
import com.stefancooper.SurvivalUHC.utils.Utils;
import com.stefancooper.SurvivalUHC.types.Worlds;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class PvpCommand extends AbstractCommand {

    public static final String COMMAND_KEY = "pvp";

    public PvpCommand(CommandSender sender, Command cmd, String[] args, Config config) {
        super(sender, cmd, args, config);
    }

    @Override
    public void execute() {
        if (getArgs().length == 1) {
            final boolean enabled = Boolean.parseBoolean(getArgs()[0]);
            final Worlds worlds = getConfig().getWorlds();
            Utils.setWorldEffects(List.of(worlds.getOverworld(), worlds.getNether(), worlds.getEnd()), (world) -> {
                world.setPVP(enabled);
            });
        } else {
            System.out.println("Bad arguments provided to pvp command");
            getSender().sendMessage("Bad arguments provided to pvp command");
        }
    }
}
