package com.stefancooper.SurvivalUHC;

import com.stefancooper.SurvivalUHC.enums.ConfigKey;
import com.stefancooper.SurvivalUHC.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;
import java.util.List;
import java.util.Properties;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.*;
import static com.stefancooper.SurvivalUHC.enums.DeathAction.SPECTATE;

public class Defaults {

    /* -- Defaults that are written to uhc_config.properties -- */
    public static String HEALTH_OBJECTIVE = "health";
    public static String WORLD_NAME = "world";
    public static String NETHER_WORLD_NAME = "world_nether";
    public static String END_WORLD_NAME = "world_the_end";
    public static String ON_DEATH_ACTION = SPECTATE.name;
    public static Difficulty DIFFICULTY = Difficulty.EASY;
    /* -- End of defaults written to uhc_config.properties -- */

    /* -- Other internal defaults -- */

    // Misc
    public static boolean PLAYER_HEAD_GOLDEN_APPLE = false;

    // Craftables
    public static boolean CRAFTABLE_NOTCH_APPLE = false;
    public static boolean CRAFTABLE_PLAYER_HEAD = false;


    // Revives
    public static boolean REVIVE_ENABLED = false;
    public static boolean REVIVE_VIA_ARMOR_STAND = true;
    public static int REVIVE_HP = 4;
    public static int REVIVE_LOSE_MAX_HEALTH = 4;


    /* -- End of defaults -- */


    public static Properties createDefaultConfig() {
        final Properties defaults = new Properties();
        defaults.setProperty(ConfigKey.WORLD_NAME.configName, WORLD_NAME);
        defaults.setProperty(WORLD_NAME_NETHER.configName, NETHER_WORLD_NAME);
        defaults.setProperty(WORLD_NAME_END.configName, END_WORLD_NAME);
        defaults.setProperty(ConfigKey.ON_DEATH_ACTION.configName, ON_DEATH_ACTION);
        defaults.setProperty(ConfigKey.DIFFICULTY.configName, DIFFICULTY.name());
        return defaults;
    }

    public static void setDefaultGameRules(Config config) {
        Utils.setWorldEffects(List.of(config.getWorlds().getOverworld(), config.getWorlds().getNether(), config.getWorlds().getEnd()), (world) -> {
            world.setGameRule(GameRule.NATURAL_REGENERATION, false);
        });
        final Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        final Objective healthObjective;
        if (board.getObjective(HEALTH_OBJECTIVE) == null) {
            healthObjective = board.registerNewObjective(HEALTH_OBJECTIVE, Criteria.HEALTH, "Health");
        } else {
            healthObjective = board.getObjective(HEALTH_OBJECTIVE);
        }
        healthObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        healthObjective.setRenderType(RenderType.HEARTS);
    }
}
