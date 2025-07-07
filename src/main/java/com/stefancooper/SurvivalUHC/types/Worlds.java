package com.stefancooper.SurvivalUHC.types;

import com.stefancooper.SurvivalUHC.Config;
import com.stefancooper.SurvivalUHC.Defaults;
import com.stefancooper.SurvivalUHC.utils.Utils;
import org.bukkit.World;

import static com.stefancooper.SurvivalUHC.enums.ConfigKey.WORLD_NAME;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.WORLD_NAME_END;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.WORLD_NAME_NETHER;

public class Worlds {

    private final Config config;
    private World overworld;
    private World nether;
    private World end;

    public Worlds(Config config) {
        this.config = config;
        overworld = Utils.getWorld(config.getProperty(WORLD_NAME, Defaults.WORLD_NAME));
        nether = Utils.getWorld(config.getProperty(WORLD_NAME_NETHER, Defaults.NETHER_WORLD_NAME));
        end = Utils.getWorld(config.getProperty(WORLD_NAME_END, Defaults.END_WORLD_NAME));
    }

    public World getOverworld() {
        return overworld;
    }

    public World getNether() {
        return nether;
    }

    public World getEnd() {
        return end;
    }

    public void updateWorlds() {
        overworld = Utils.getWorld(config.getProperty(WORLD_NAME, Defaults.WORLD_NAME));
        nether = Utils.getWorld(config.getProperty(WORLD_NAME_NETHER, Defaults.NETHER_WORLD_NAME));
        end = Utils.getWorld(config.getProperty(WORLD_NAME_END, Defaults.END_WORLD_NAME));
    }
}
