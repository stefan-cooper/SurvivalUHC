package com.stefancooper.SurvivalUHC;

import com.stefancooper.SurvivalUHC.types.InstantRevive;
import com.stefancooper.SurvivalUHC.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;


import static com.stefancooper.SurvivalUHC.utils.Constants.CRAFTABLE_PLAYER_HEAD;
import static com.stefancooper.SurvivalUHC.utils.Constants.NOTCH_APPLE;
import static com.stefancooper.SurvivalUHC.utils.Constants.PLAYER_HEAD;

public class ManagedResources {

    final Config config;
    final BukkitScheduler scheduler;
    final NamespacedKey playerHead;
    final NamespacedKey craftablePlayerHead;
    final NamespacedKey notchApple;

    public ManagedResources(final Config config) {
        this.config = config;
        this.scheduler = Bukkit.getScheduler();
        this.playerHead = new NamespacedKey(config.getPlugin(), PLAYER_HEAD);
        this.craftablePlayerHead = new NamespacedKey(config.getPlugin(), CRAFTABLE_PLAYER_HEAD);
        this.notchApple = new NamespacedKey(config.getPlugin(), NOTCH_APPLE);
    }

    public void instantRevive(Player reviver, String revivee, ArmorStand armorStand) {
        new InstantRevive(config, reviver, revivee, true, armorStand);
    }

    public BukkitTask runTaskLater(Runnable runnable, int time) {
        return scheduler.runTaskLater(config.getPlugin(), runnable, Utils.secondsToTicks(time));
    }

    public BukkitTask runRepeatingTask(Runnable runnable, int interval) {
        return scheduler.runTaskTimer(config.getPlugin(), runnable, 0, Utils.secondsToTicks(interval));
    }

    public void cancelRepeatingTask(int id) {
        scheduler.cancelTask(id);
    }

    public NamespacedKey getPlayerHeadKey() {
        return playerHead;
    }

    public NamespacedKey getNotchAppleKey() {
        return notchApple;
    }

    public NamespacedKey getCraftablePlayerHeadKey() {
        return craftablePlayerHead;
    }

    public void cancelTimer() {
        scheduler.cancelTasks(config.getPlugin());
    }

}
