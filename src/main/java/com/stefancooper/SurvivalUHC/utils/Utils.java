package com.stefancooper.SurvivalUHC.utils;

import com.stefancooper.SurvivalUHC.Config;
import com.stefancooper.SurvivalUHC.Defaults;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import java.util.List;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.REVIVE_ENABLED;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.REVIVE_VIA_ARMOR_STAND;

public class Utils {

    public static World getWorld (String name) {
        final String worldName = name != null ? name : Defaults.WORLD_NAME;
        if (Bukkit.getWorld(worldName) == null) return Bukkit.createWorld(WorldCreator.name(worldName).environment(World.Environment.NORMAL));
        else return Bukkit.getWorld(worldName);
    }

    public interface WorldBorderCallback {
        void execute(World world);
    }

    public static void setWorldEffects(List<World> worlds, WorldBorderCallback callback) {
        worlds.forEach(callback::execute);
    }

    // Some things are managed in Minecraft ticks. Use this to convert from seconds to ticks
    public static long secondsToTicks (int seconds) {
        return (long) seconds * 20;
    }

    public static boolean testMode() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }

    public static String getResourceLocation(final String fileName) {
        if (testMode()) {
            return String.format("./src/test/java/resources/%s", fileName);
        } else {
            return String.format("./plugins/%s", fileName);
        }
    }

    public static boolean isReviveViaArmorStandEnabled(final Config config) {
        boolean reviveEnabled = config.getProperty(REVIVE_ENABLED, Defaults.REVIVE_ENABLED);
        boolean reviveViaPlatforms = config.getProperty(REVIVE_VIA_ARMOR_STAND, Defaults.REVIVE_VIA_ARMOR_STAND);
        return reviveEnabled && reviveViaPlatforms;
    }

}
