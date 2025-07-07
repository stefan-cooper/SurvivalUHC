import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import com.stefancooper.SurvivalUHC.Plugin;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.junit.jupiter.api.*;

import static com.stefancooper.SurvivalUHC.Defaults.*;

public class PluginTest {

    private static ServerMock server;
    private static Plugin plugin;
    private static World world;

    @BeforeAll
    public static void load()
    {
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
    @DisplayName("Test correct game rules were applied")
    void testGameRules() {
        PlayerMock player = server.addPlayer();
        Assertions.assertNotNull(world);
        Assertions.assertFalse(world.getGameRuleValue(GameRule.NATURAL_REGENERATION));
        Assertions.assertTrue(world.getPVP());
        Assertions.assertEquals(GameMode.SURVIVAL, player.getGameMode());
    }

    @Test
    @DisplayName("Test player scoreboard objective is added")
    void testPlayerScoreboard() {
        PlayerMock player = server.addPlayer();
        Assertions.assertNotNull(player.getScoreboard().getObjective(HEALTH_OBJECTIVE));
    }
}
