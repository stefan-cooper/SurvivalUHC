import com.stefancooper.SurvivalUHC.Plugin;
import org.bukkit.World;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import static com.stefancooper.SurvivalUHC.Defaults.WORLD_NAME;

public class UnsetConfigTest {

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
    @DisplayName("Test set then unset config key")
    void testSetAndUnsetConfigKey() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        server.execute("uhc", player, "set", "difficulty=HARD");
        server.execute("uhc", player, "view", "difficulty");
        player.assertSaid("difficulty=HARD");
        server.execute("uhc", player, "unset", "difficulty");
        server.execute("uhc", player, "view", "difficulty");
        player.assertSaid("Unknown config value requested or not set");
    }
}
