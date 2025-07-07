package com.stefancooper.SurvivalUHC.commands;

import com.stefancooper.SurvivalUHC.Config;
import com.stefancooper.SurvivalUHC.enums.ConfigKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class UnsetConfigCommand extends AbstractCommand {

    public static final String COMMAND_KEY = "unset";

    public UnsetConfigCommand(CommandSender sender, Command cmd, String[] args, Config config) {
        super(sender, cmd, args, config);
    }

    @Override
    public void execute() {
        Arrays.asList(getArgs()).forEach(arg -> {
            getConfig().unsetProp(arg);
        });

    }
}
