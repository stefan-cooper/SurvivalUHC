# SurvivalUHC

Turn on Survival UHC on your server.

Want to contribute? See [Contributing](#contributing)

## Installing

1. Download the plugin - we don't currently have any accessible download links. Contact @stefan-cooper or any other contributor to retrieve a .jar file

2. Add the plugin to your `plugins` folder in your Spigot server

## Configure your UHC settings

You can configure UHC settings in the server (see [Configuring](#configuring)) or you can create a `uhc_config.properties` file inside your `plugins` folder. One of these will be created anyway after starting the server with this plugin installed.

The following configurations are available for managing your UHC:

### Required Properties (they have defaults)

```properties
# Name of the minecraft overworld
world.name=world
# Name of the minecraft nether world
nether.world.name=world_nether
# Name of the minecraft end world
end.world.name=world_end
# Difficulty of the game when UHC is live
difficulty=EASY
# Action to undertake when a player dies ("spectate" | "kick")
on.death.action=spectate
# Enable revive. False by default
revive.enabled=false|true
# HP that the revived player will start on (default: 4 (2 hearts))
revive.hp=4
# HP that the revived player will lose permanently on each revive (default 4 (2 hearts))
revive.lose.max.health=4
# (optional) drop player heads who are killed that can be crafted into golden apples
player.head.golden.apple=false|true
# (optional) re-add notch apples
craftable.notch.apples=true
# (optional) add a craftable player head (golden apple surrounded by diamonds)
craftable.player.heads=true
```

## Commands

The following commands are available in game:

### Configuring

#### Set config value:

`/uhc set revive.hp=4`

#### Set multiple config values:

`/uhc set revive.hp=4 revive.enabled=true`

#### View full config:

`/uhc view config`

#### View a specific config value:

`/uhc view revive.hp`

#### Unset config value:

`/uhc unset revive.hp`

#### Unset multiple config values:

`/uhc unset revive.hp revive.enabled`

#### Manually enable/disable PVP:

`/uhc pvp <true|false>`

e.g: `/uhc pvp true`

## Contributing

Contact @stefan-cooper for information about contributing. This is an open source project so if you feel like you want to add something, just raise a PR!

### Prereqs

- Java 21
- Maven
- Git

### Getting started

1. Clone the repo

2. Run the following command to build the spigot server dev env

   ```
   REFRESH_BUILD=true ./setup_server.sh
   ```

   Note: This may take a long time (10-15min)

### Running

1. Run Spigot server

   ```
   ./run_server.sh
   ```
   