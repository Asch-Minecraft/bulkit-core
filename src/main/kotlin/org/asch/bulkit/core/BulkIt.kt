package org.asch.bulkit.core

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import org.asch.bulkit.core.setup.Items
import org.asch.bulkit.core.setup.ResourceTypes
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(BulkIt.ID)
object BulkIt {
    const val ID: String = "bulkit"

    init {
        val modBus = MOD_BUS
        register(modBus)
    }

    private fun register(modBus: IEventBus) {
        Items.register(modBus)
        ResourceTypes.register(modBus)
    }
}