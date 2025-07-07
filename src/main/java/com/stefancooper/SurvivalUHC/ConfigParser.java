package com.stefancooper.SurvivalUHC;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.CRAFTABLE_NOTCH_APPLE;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.CRAFTABLE_PLAYER_HEAD;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.DIFFICULTY;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.ON_DEATH_ACTION;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.PLAYER_HEAD_GOLDEN_APPLE;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.REVIVE_ENABLED;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.REVIVE_HP;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.REVIVE_LOSE_MAX_HEALTH;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.REVIVE_VIA_ARMOR_STAND;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.WORLD_NAME;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.WORLD_NAME_END;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.WORLD_NAME_NETHER;
import static com.stefancooper.SurvivalUHC.enums.ConfigKey.fromString;
import com.stefancooper.SurvivalUHC.utils.Configurable;

public class ConfigParser {

    private final Config config;

    public ConfigParser(Config config) {
        this.config = config;
    }

    public Configurable<?> propertyToConfigurable(String key, String value) {
        return switch (fromString(key)) {
            case ON_DEATH_ACTION -> new Configurable<>(ON_DEATH_ACTION, value);
            case PLAYER_HEAD_GOLDEN_APPLE -> new Configurable<>(PLAYER_HEAD_GOLDEN_APPLE, Boolean.parseBoolean((value)));
            case WORLD_NAME -> new Configurable<>(WORLD_NAME, value);
            case WORLD_NAME_NETHER -> new Configurable<>(WORLD_NAME_NETHER, value);
            case WORLD_NAME_END -> new Configurable<>(WORLD_NAME_END, value);
            case DIFFICULTY -> new Configurable<>(DIFFICULTY, Difficulty.valueOf(value));
            case CRAFTABLE_NOTCH_APPLE -> new Configurable<>(CRAFTABLE_NOTCH_APPLE, Boolean.parseBoolean(value));
            case CRAFTABLE_PLAYER_HEAD -> new Configurable<>(CRAFTABLE_PLAYER_HEAD, Boolean.parseBoolean(value));
            // Revive config
            case REVIVE_ENABLED -> new Configurable<>(REVIVE_ENABLED, Boolean.parseBoolean(value));
            case REVIVE_HP -> new Configurable<>(REVIVE_HP, Integer.parseInt(value));
            case REVIVE_LOSE_MAX_HEALTH -> new Configurable<>(REVIVE_LOSE_MAX_HEALTH, Integer.parseInt(value));
            case REVIVE_VIA_ARMOR_STAND -> new Configurable<>(REVIVE_VIA_ARMOR_STAND, Boolean.parseBoolean(value));
            case null -> null;
        };
    }

    public void executeConfigurable(Configurable<?> configurable) {
        if (configurable == null) {
            System.out.println("Invalid config value attempted to be executed, ignoring...");
            return;
        }
        switch (configurable.key()) {
            case PLAYER_HEAD_GOLDEN_APPLE:
                NamespacedKey playerHeadKey = config.getManagedResources().getPlayerHeadKey();
                if (config.getProperty(PLAYER_HEAD_GOLDEN_APPLE, Defaults.PLAYER_HEAD_GOLDEN_APPLE)) {
                    if (Bukkit.getRecipe(playerHeadKey) == null) {
                        ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, 1);
                        ItemMeta appleMeta = apple.getItemMeta();
                        apple.setItemMeta(appleMeta);
                        ShapedRecipe recipe = new ShapedRecipe(playerHeadKey, apple);
                        recipe.shape("   ", " X ", "   ");
                        recipe.setIngredient('X', Material.PLAYER_HEAD);
                        Bukkit.addRecipe(recipe);
                    }
                } else {
                    if (Bukkit.getRecipe(playerHeadKey) != null) {
                        Bukkit.removeRecipe(playerHeadKey);
                    }
                }
                break;
            case CRAFTABLE_NOTCH_APPLE:
                NamespacedKey notchAppleKey = config.getManagedResources().getNotchAppleKey();
                if (config.getProperty(CRAFTABLE_NOTCH_APPLE, Defaults.CRAFTABLE_NOTCH_APPLE)) {
                    if (Bukkit.getRecipe(notchAppleKey) == null) {
                        ItemStack apple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 1);
                        ShapedRecipe recipe = new ShapedRecipe(notchAppleKey, apple);
                        recipe.shape("GGG", "GAG", "GGG");
                        recipe.setIngredient('G', Material.GOLD_BLOCK);
                        recipe.setIngredient('A', Material.APPLE);
                        Bukkit.addRecipe(recipe);
                    }
                } else {
                    if (Bukkit.getRecipe(notchAppleKey) != null) {
                        Bukkit.removeRecipe(notchAppleKey);
                    }
                }
                break;
            case CRAFTABLE_PLAYER_HEAD:
                final NamespacedKey craftablePlayerHeadKey = config.getManagedResources().getCraftablePlayerHeadKey();
                if (config.getProperty(CRAFTABLE_PLAYER_HEAD, Defaults.CRAFTABLE_PLAYER_HEAD)) {
                    if (Bukkit.getRecipe(craftablePlayerHeadKey) == null) {
                        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
                        ShapedRecipe recipe = new ShapedRecipe(craftablePlayerHeadKey, playerHead);
                        recipe.shape("DDD", "DGD", "DDD");
                        recipe.setIngredient('D', Material.DIAMOND);
                        recipe.setIngredient('G', Material.GOLDEN_APPLE);
                        Bukkit.addRecipe(recipe);
                    }
                } else {
                    if (Bukkit.getRecipe(craftablePlayerHeadKey) != null) {
                        Bukkit.removeRecipe(craftablePlayerHeadKey);
                    }
                }
                break;
            case WORLD_NAME:
            case WORLD_NAME_NETHER:
            case WORLD_NAME_END:
                config.getWorlds().updateWorlds();
            default:
                break;
        }
    }

    public void executeConfigurables(List<? extends Configurable<?>> configurables) {
        configurables.forEach(this::executeConfigurable);
    }

}
