package com.stefancooper.SurvivalUHC.events;

import com.stefancooper.SurvivalUHC.Config;
import com.stefancooper.SurvivalUHC.Defaults;
import com.stefancooper.SurvivalUHC.enums.DeathAction;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HappyGhast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import java.util.List;

import static com.stefancooper.SurvivalUHC.enums.ConfigKey.*;

public class BaseEvents implements Listener {

    private final Config config;

    public BaseEvents (Config config) {
        this.config = config;
    }

    // View docs for various events https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/event/package-summary.html

    // DamageSource API is experimental, so this may break in a spigot update
    @SuppressWarnings("UnstableApiUsage")
    @EventHandler
    public void onDeath (PlayerDeathEvent event) {
        switch (DeathAction.fromString(config.getProperty(ON_DEATH_ACTION, Defaults.ON_DEATH_ACTION))){
            case SPECTATE:
                event.getEntity().setGameMode(GameMode.SPECTATOR);
                break;
            case KICK:
                event.getEntity().kickPlayer("GG, you suck");
                break;
            case null:
            default:
                break;
        }

        if (config.getProperty(PLAYER_HEAD_GOLDEN_APPLE, Defaults.PLAYER_HEAD_GOLDEN_APPLE)) {
            Player player = event.getEntity();
            ItemStack head = new ItemStack(Material.PLAYER_HEAD,1);
            SkullMeta headMeta = (SkullMeta) head.getItemMeta();
            assert headMeta != null;
            headMeta.setDisplayName(String.format("%s's head", player.getDisplayName()));
            headMeta.setLore(List.of("Put this item in a bench", "For a Golden Apple"));
            headMeta.setOwningPlayer(player);
            headMeta.setUnbreakable(true);
            headMeta.setFireResistant(true);
            try {
                headMeta.setRarity(ItemRarity.EPIC);
            } catch (Exception e) {
                // noop
                // for some reason, this function is not implemented in MockBukkit but it is not worth us mocking
            }
            head.setItemMeta(headMeta);
            player.getWorld().dropItemNaturally(player.getLocation(), head);
        }

        // Play death cannon
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1));

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Location deathLocation = player.getLastDeathLocation();
        if (deathLocation != null && player.getGameMode().equals(GameMode.SPECTATOR)) {
            event.setRespawnLocation(deathLocation);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        /* -- Setting Game mode -- */
        GameMode currentGamemode = event.getPlayer().getGameMode();
        if (currentGamemode == GameMode.SPECTATOR) {
            // dont change their game mode if they are dead
            return;
        } else {
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
        }
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        // Set Happy Ghasts to 100 hearts
        if (event.getEntity().getType().equals(EntityType.HAPPY_GHAST)) {
            final HappyGhast ghast = (HappyGhast) event.getEntity();
            final AttributeInstance maxHealth = ghast.getAttribute(Attribute.MAX_HEALTH);
            if (maxHealth != null) {
                maxHealth.setBaseValue(200);
            }
            ghast.setHealth(200);
        }

        if (!event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) {
            // noop
            return;
        }
    }
}
