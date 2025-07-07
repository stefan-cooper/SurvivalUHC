package com.stefancooper.SurvivalUHC.commands;

import com.stefancooper.SurvivalUHC.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ViewConfigCommand extends AbstractCommand {

    public static final String COMMAND_KEY = "view";

    public ViewConfigCommand(CommandSender sender, Command cmd, String[] args, Config config) {
        super(sender, cmd, args, config);
    }

    @Override
    public void execute() {
        if (getArgs().length == 1) {
            final String viewConfigKey = getArgs()[0];
            final String prop = getConfig().getProp(viewConfigKey);

            if (prop != null) {
                getSender().sendMessage(viewConfigKey + "=" + prop);
            } else if (viewConfigKey.equals("config")) {
                getSender().sendMessage(getConfig().getProps());
            } else {
                getSender().sendMessage("Unknown config value requested or not set");
            }
        } else {
            System.out.println("Too many arguments provided to view config");
            getSender().sendMessage("Too many arguments provided to view config");
        }
    }
}
