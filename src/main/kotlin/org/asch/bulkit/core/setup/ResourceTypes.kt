package org.asch.bulkit.core.setup

import net.minecraft.core.registries.BuiltInRegistries
import net.neoforged.bus.api.IEventBus
import org.asch.bulkit.api.resource.DeferredResourceRegister
import org.asch.bulkit.core.BulkIt

object ResourceTypes {
    private val REGISTRAR = DeferredResourceRegister(BulkIt.ID, Items.REGISTRAR)

    val ITEM = REGISTRAR.registerResourceType("item") { builder ->
        builder.registry(BuiltInRegistries.ITEM)
    }

    val FLUID = REGISTRAR.registerResourceType("fluid") { builder ->
        builder.registry(BuiltInRegistries.FLUID)
    }

    fun register(modBus: IEventBus) = REGISTRAR.register(modBus)
}