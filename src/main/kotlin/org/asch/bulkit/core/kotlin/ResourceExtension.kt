package org.asch.bulkit.core.kotlin

import net.minecraft.core.component.DataComponentPatch
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.material.Fluid
import net.neoforged.neoforge.fluids.FluidStack
import org.asch.bulkit.api.resource.Resource
import java.util.*

fun Resource<Item>.toStack(amount: Long): ItemStack {
    val stack = ItemStack(resource, amount.toInt())
    stack.applyComponents(components)
    return stack
}

fun Resource<Fluid>.toStack(amount: Long): FluidStack =
    FluidStack(resource, amount.toInt())

fun Resource<Item>.matches(stack: ItemStack): Boolean =
    if (stack.isEmpty) {
        false
    } else {
        stack.`is`(resource) && Objects.equals(stack.components, components)
    }

fun Resource<Fluid>.matches(stack: FluidStack): Boolean =
    if (stack.isEmpty) {
        false
    } else {
        stack.`is`(resource) && Objects.equals(stack.components, components)
    }

fun ItemStack.toResource(): Resource<Item> = Resource(item, componentsPatch)

fun FluidStack.toResource(): Resource<Fluid> = Resource(fluid, DataComponentPatch.EMPTY)