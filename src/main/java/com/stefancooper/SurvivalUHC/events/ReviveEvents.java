package com.stefancooper.SurvivalUHC.events;

import com.stefancooper.SurvivalUHC.Config;
import com.stefancooper.SurvivalUHC.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.EquipmentSlot;
import java.util.List;


public class ReviveEvents implements Listener {

    private final Config config;

    public ReviveEvents (Config config) {
        this.config = config;
    }

    // View docs for various events https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/event/package-summary.html

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.ITEM) {
            Item item = (Item) event.getEntity();
            if (item.getItemStack().getType() == Material.PLAYER_HEAD) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onArmorStandEvent(PlayerArmorStandManipulateEvent event) {
        if (Utils.isReviveViaArmorStandEnabled(config)) {
            if (event.getSlot() == EquipmentSlot.HEAD && event.getPlayerItem().getType() == Material.PLAYER_HEAD) {
                List<Player> deadPlayers = (List<Player>) Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SPECTATOR).toList();
                if (!deadPlayers.isEmpty()) {
                    config.getManagedResources().instantRevive(event.getPlayer(), deadPlayers.getFirst().getName(), event.getRightClicked());
                    event.getRightClicked().setVisible(false);
                    event.getRightClicked().setHealth(0);
                    return;
                }
            }
        }

    }

}
