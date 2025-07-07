package com.stefancooper.SurvivalUHC.enums;

public enum ConfigKey {

    // ----- Required config -----
    WORLD_NAME("world.name"), // Name of the minecraft world
    WORLD_NAME_NETHER("nether.world.name"), // Name of the nether world
    WORLD_NAME_END("end.world.name"), // Name of the end world
    DIFFICULTY("difficulty"), // Difficulty during the UHC
    ON_DEATH_ACTION("on.death.action"), // Action to undertake when a player dies ("spectate" | "kick")
    // ----- End required config -----

    // Misc Enums
    PLAYER_HEAD_GOLDEN_APPLE("player.head.golden.apple"), // (optional) drop player heads who are killed that can be crafted into golden apples
    CRAFTABLE_NOTCH_APPLE("craftable.notch.apple"), // re-add craftable notch apple
    CRAFTABLE_PLAYER_HEAD("craftable.player.head"), // add craftable player head

    // Revive config
    REVIVE_ENABLED("revive.enabled"), // Enable revive
    REVIVE_HP("revive.hp"), // Revivee starting hp
    REVIVE_LOSE_MAX_HEALTH("revive.lose.max.health"), // Revivee max hp loss
    REVIVE_VIA_ARMOR_STAND("revive.via.armor.stand"), // Enable revive

    ;

    public final String configName;

    ConfigKey(String name) {
        this.configName = name;
    }

    public static ConfigKey fromString(String configName) {
        for (ConfigKey key : ConfigKey.values()) {
            if (key.configName.equalsIgnoreCase(configName)) {
                return key;
            }
        }
        return null;
    }
}
