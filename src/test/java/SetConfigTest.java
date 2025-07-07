import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;
import com.stefancooper.SurvivalUHC.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scoreboard.Scoreboard;
import org.junit.jupiter.api.*;

import static com.stefancooper.SurvivalUHC.Defaults.WORLD_NAME;
import static com.stefancooper.SurvivalUHC.utils.Constants.PLAYER_HEAD;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetConfigTest {

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
    @DisplayName("Test that the recipe for creating a golden apple from a player head works")
    void testPlayerCanCraftGoldenAppleFromPlayerHead() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);
        server.execute("uhc", player, "set", "player.head.golden.apple=true");
        ItemStack apple = new ItemStack(Material.GOLDEN_APPLE);
        NamespacedKey key = new NamespacedKey(plugin, PLAYER_HEAD);
        ShapedRecipe recipe = (ShapedRecipe) Bukkit.getRecipe(key);
        Assertions.assertNotNull(recipe, "Recipe should be registered.");

        // Simulate crafting inventory (3x3 grid with player head in the center (center = 4))
        InventoryMock craftingInventory = server.createInventory(null, 9, "Crafting");
        craftingInventory.setItem(4, new ItemStack(Material.PLAYER_HEAD));

        assertTrue(isMatchingRecipe(craftingInventory, recipe), "Inventory should match the recipe.");
        Assertions.assertEquals(apple.getType(), recipe.getResult().getType(), "Crafted item should be a golden apple.");
    }

    // Helper method to check if crafting grid matches the recipe
    private boolean isMatchingRecipe(InventoryMock inventory, ShapedRecipe recipe) {
        String[] shape = recipe.getShape();                                         // Get the recipe's shape (array of strings where each row is a line of the crafting grid)
        for (int row = 0; row < shape.length; row++) {                              // Loop through each row in the recipe's shape
            for (int col = 0; col < shape[row].length(); col++) {                   // Loop through each character (ingredient) in the current row
                int slotIndex = row * 3 + col;                                      // Convert 2D grid to flat index
                char ingredientChar = shape[row].charAt(col);                       // Get the ingredient character from the recipe's shape (e.g., 'X' for PLAYER_HEAD)
                ItemStack expected = recipe.getIngredientMap().get(ingredientChar); // Get the expected ItemStack for this ingredient character (e.g., Material.PLAYER_HEAD)
                ItemStack actual = inventory.getItem(slotIndex);                    // Get the actual ItemStack from the simulated crafting grid (inventory)

                if (ingredientChar == ' ' && actual != null) return false;          // Check if the recipe expects an empty slot but the inventory has an item there
                if (expected != null && (actual == null || expected.getType() != actual.getType())) return false; // Check if the recipe expects an ingredient but the actual inventory slot is null or doesn't match
            }
        }
        return true;
    }
}
