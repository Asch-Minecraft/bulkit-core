package org.asch.bulkit.core.utils

import net.minecraft.server.MinecraftServer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CraftingRecipe
import net.minecraft.world.item.crafting.RecipeType

object Compression {
    fun getCompressionChain(item: ItemStack, server: MinecraftServer, maxChainSize: Int = 1): List<CraftingRecipe> {
        if (maxChainSize == 0) {
            return emptyList()
        }

        val nextRecipe = getNextRecipe(item, 2, listOf(1), server)
            ?: getNextRecipe(item, 3, listOf(1), server)
            ?: return emptyList()
        return listOf(nextRecipe.first) + getCompressionChain(
            nextRecipe.second.copyWithCount(1), server, maxChainSize - 1
        )
    }


    fun getDecompressionChain(item: ItemStack, server: MinecraftServer, maxChainSize: Int = 1): List<CraftingRecipe> {
        if (maxChainSize == 0) {
            return emptyList()
        }

        val nextRecipe = getNextRecipe(item, 1, listOf(4, 9), server) ?: return emptyList()
        return listOf(nextRecipe.first) + getDecompressionChain(
            nextRecipe.second.copyWithCount(1),
            server,
            maxChainSize - 1
        )
    }

    private fun getNextRecipe(
        item: ItemStack,
        inputSize: Int,
        outputSizes: Collection<Int>,
        server: MinecraftServer
    ): Pair<CraftingRecipe, ItemStack>? {
        val craftingInput =
            CraftingInput.of(inputSize, inputSize, generateSequence { item }.take(inputSize * inputSize).toList())
        val recipes =
            server.recipeManager.recipeMap().getRecipesFor(RecipeType.CRAFTING, craftingInput, server.overworld())
        val candidateRecipes = recipes
            .map { Pair(it.value, it.value.assemble(craftingInput, server.registryAccess())) }
            .filter { it.second.count in outputSizes }
            .filter { canReverseCraft(it.second, item.copyWithCount(inputSize * inputSize), server) }
            .toList()

        return if (candidateRecipes.size == 1) candidateRecipes[0] else null
    }

    private fun canReverseCraft(input: ItemStack, output: ItemStack, server: MinecraftServer): Boolean {
        val craftingInput = when (input.count) {
            1 -> CraftingInput.of(1, 1, listOf(input))
            4 -> CraftingInput.of(2, 2, generateSequence { input.copyWithCount(1) }.take(4).toList())
            9 -> CraftingInput.of(3, 3, generateSequence { input.copyWithCount(1) }.take(9).toList())
            else -> return false
        }

        val craftingRecipes =
            server.recipeManager.recipeMap().getRecipesFor(RecipeType.CRAFTING, craftingInput, server.overworld())
        return craftingRecipes
            .map { it.value.assemble(craftingInput, server.registryAccess()) }
            .filter { ItemStack.matches(it, output) }
            .count() == 1L
    }
}
