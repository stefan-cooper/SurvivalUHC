import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import com.stefancooper.SurvivalUHC.Plugin;
import org.bukkit.World;
import org.junit.jupiter.api.*;

import static com.stefancooper.SurvivalUHC.Defaults.*;

public class ViewConfigTest {

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
    @DisplayName("Test view full config")
    void testPlayerGetConfig() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        server.execute("uhc", player, "view", "config");
        player.assertSaid(String.format("""
                        difficulty=%s
                        end.world.name=%s
                        nether.world.name=%s
                        on.death.action=%s
                        world.name=%s
                        """, DIFFICULTY, END_WORLD_NAME, NETHER_WORLD_NAME, ON_DEATH_ACTION, WORLD_NAME
                        )
        );
    }

    @Test
    @DisplayName("Test view initial world border command")
    void testPlayerGetInitialWorldBorderSize() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        server.execute("uhc", player, "view", "difficulty");
        player.assertSaid("difficulty=" + DIFFICULTY);
    }
}
