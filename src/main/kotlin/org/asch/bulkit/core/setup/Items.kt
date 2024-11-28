package org.asch.bulkit.core.setup

import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import org.asch.bulkit.core.BulkIt

object Items {
    private val REGISTRAR: DeferredRegister.Items = DeferredRegister.createItems(BulkIt.ID)

    fun register(modBus: IEventBus) = REGISTRAR.register(modBus)
}