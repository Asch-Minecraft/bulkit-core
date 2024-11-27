package org.asch.bulkit.core.setup

import net.minecraft.core.registries.BuiltInRegistries
import net.neoforged.bus.api.IEventBus
import org.asch.bulkit.api.registry.ResourceTypeRegister
import org.asch.bulkit.core.BulkIt

object ResourceTypes {
    private val REGISTRAR = ResourceTypeRegister(BulkIt.ID)

    val ITEM = REGISTRAR.registerResourceType("item", BuiltInRegistries.ITEM) { builder -> builder }
    val FLUID = REGISTRAR.registerResourceType("fluid", BuiltInRegistries.FLUID) { builder -> builder }

    fun register(modBus: IEventBus) = REGISTRAR.register(modBus)
}