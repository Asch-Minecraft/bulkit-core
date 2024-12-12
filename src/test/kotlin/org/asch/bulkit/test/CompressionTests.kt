package org.asch.bulkit.test

import net.minecraft.server.MinecraftServer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CraftingRecipe
import net.neoforged.testframework.junit.EphemeralTestServerProvider
import org.asch.bulkit.core.utils.Compression
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(EphemeralTestServerProvider::class)
object CompressionTests {
    @Test
    fun compressionChain(server: MinecraftServer) {
        val chain = Compression.getCompressionChain(Items.GOLD_NUGGET.defaultInstance, server, 5)
        Assertions.assertEquals(2, chain.size)

        assertRecipeMatch(
            chain[0],
            3,
            Items.GOLD_NUGGET.defaultInstance,
            Items.GOLD_INGOT.defaultInstance,
            server
        )

        assertRecipeMatch(
            chain[1],
            3,
            Items.GOLD_INGOT.defaultInstance,
            Items.GOLD_BLOCK.defaultInstance,
            server
        )
    }

    @Test
    fun decompressionChain(server: MinecraftServer) {
        val chain = Compression.getDecompressionChain(Items.GOLD_BLOCK.defaultInstance, server, 5)
        Assertions.assertEquals(2, chain.size)

        assertRecipeMatch(
            chain[0],
            1,
            Items.GOLD_BLOCK.defaultInstance,
            Items.GOLD_INGOT.defaultInstance.copyWithCount(9),
            server
        )

        assertRecipeMatch(
            chain[1],
            1,
            Items.GOLD_INGOT.defaultInstance,
            Items.GOLD_NUGGET.defaultInstance.copyWithCount(9),
            server
        )
    }

    private fun assertRecipeMatch(
        recipe: CraftingRecipe,
        craftingSize: Int, input: ItemStack,
        output: ItemStack,
        server: MinecraftServer
    ) {
        val craftingInput = CraftingInput.of(
            craftingSize,
            craftingSize,
            generateSequence { input }.take(craftingSize * craftingSize).toList()
        )
        Assertions.assertTrue(ItemStack.matches(recipe.assemble(craftingInput, server.registryAccess()), output))
    }
}