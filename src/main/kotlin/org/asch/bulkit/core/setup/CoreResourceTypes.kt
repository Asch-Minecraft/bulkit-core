package org.asch.bulkit.core.setup

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item
import net.minecraft.world.level.material.Fluid
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import org.asch.bulkit.api.registry.ResourceTypeRegister
import org.asch.bulkit.api.resource.ResourceType
import org.asch.bulkit.core.BulkIt

object CoreResourceTypes {
    private val REGISTRAR: ResourceTypeRegister = ResourceTypeRegister(BulkIt.ID)

    val ITEM: DeferredHolder<ResourceType<*>, ResourceType<Item>> =
        REGISTRAR.registerResourceType("item", BuiltInRegistries.ITEM) { builder ->
            builder.baseStackMultiplier(8)
        }

    val FLUID: DeferredHolder<ResourceType<*>, ResourceType<Fluid>> =
        REGISTRAR.registerResourceType("fluid", BuiltInRegistries.FLUID) { builder ->
            builder.baseStackMultiplier(32)
        }

    fun register(modBus: IEventBus) = REGISTRAR.register(modBus)
}