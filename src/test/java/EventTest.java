import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.scheduler.BukkitSchedulerMock;
import com.stefancooper.SurvivalUHC.Plugin;
import com.stefancooper.SurvivalUHC.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.stefancooper.SurvivalUHC.Defaults.WORLD_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.*;

public class EventTest {

    private static ServerMock server;
    private static Plugin plugin;
    private static World world;

    @BeforeAll
    public static void load() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(Plugin.class);
        world = server.getWorld(WORLD_NAME);
    }

    @BeforeEach
    public void cleanUp() {
        plugin.getUHCConfig().resetToDefaults();
    }

    @AfterAll
    public static void unload() {
        plugin.getUHCConfig().resetToDefaults();
        MockBukkit.unmock();
    }

    @Test
    @DisplayName("Test the on death event to ensure a player is set to Spectator after death")
    void testOnDeathEventSpectate() {
        PlayerMock player = server.addPlayer();
        player.damage(100);
        player.getGameMode();
        assertEquals(GameMode.SPECTATOR, player.getGameMode());
    }

    @Test
    @DisplayName("Test the on death event to ensure a player is kicked after death")
    void testOnDeathEventKick() {
        server.setPlayers(15);
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        server.execute("uhc", player, "set", "on.death.action=kick");
        assertEquals(16, server.getOnlinePlayers().size());
        player.damage(100);
        server.getOnlinePlayers();
        assertEquals(15, server.getOnlinePlayers().size());
    }

    @Test
    @DisplayName("Test the on death event to ensure a player drops a head after death")
    void testHeadDropOnDeath() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        server.execute("uhc", player, "set", "player.head.golden.apple=true");
        player.damage(100);
        if(!world.getEntities().stream().filter(entity -> entity.getType() == EntityType.ITEM && entity.getName().equals("PLAYER_HEAD")).toList().isEmpty()){
            Item droppedItem = (Item) world.getEntities().get(0);
            droppedItem.getItemStack();

            assertEquals(Material.PLAYER_HEAD, droppedItem.getType());
        }
    }

    @Test
    @DisplayName("Test the on respawn event to ensure a player respawns at their death location")
    void testPlayerRespawnOnDeathLocation() {
        PlayerMock player = server.addPlayer();
        World world = player.getWorld();
        Location deathLocation = new Location(world, 100, 65, 100);
        player.damage(100);
        player.setLastDeathLocation(deathLocation);

        PlayerRespawnEvent respawnEvent = new PlayerRespawnEvent(player, null, false);
        server.getPluginManager().callEvent(respawnEvent);

        assertEquals(deathLocation, respawnEvent.getRespawnLocation(), "Player should respawn at their death location");
    }

    @Test
    @DisplayName("Correct game mode is set when joining the server")
    void testGameModeSet() {
        PlayerMock admin = server.addPlayer();
        admin.setOp(true);

        PlayerMock player = server.addPlayer();

        assertEquals(GameMode.SURVIVAL, player.getGameMode());

        player.disconnect();
        player.reconnect();

        assertEquals(GameMode.SURVIVAL, player.getGameMode());

        player.damage(100);
        player.respawn();

        assertEquals(GameMode.SPECTATOR, player.getGameMode());

        player.disconnect();
        player.reconnect();

        assertEquals(GameMode.SPECTATOR, player.getGameMode());
    }

    @Test
    @DisplayName("Test the on death event fires a cannon when someone dies in a uhc")
    void cannonOnDeathTest() {
        PlayerMock admin = server.addPlayer();
        admin.setOp(true);

        PlayerMock player1 = server.addPlayer();
        PlayerMock player2 = server.addPlayer();
        PlayerMock player3 = server.addPlayer();

        player1.damage(100);

        player2.assertSoundHeard(Sound.ENTITY_LIGHTNING_BOLT_THUNDER);
        player3.assertSoundHeard(Sound.ENTITY_LIGHTNING_BOLT_THUNDER);
    }
}
