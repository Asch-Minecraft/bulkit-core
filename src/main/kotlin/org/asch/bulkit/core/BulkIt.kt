package org.asch.bulkit.core

import com.mojang.logging.LogUtils
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent
import net.neoforged.neoforge.registries.NewRegistryEvent
import org.asch.bulkit.api.BulkItApi
import org.asch.bulkit.core.setup.Items
import org.asch.bulkit.core.setup.ResourceTypes
import org.slf4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(BulkIt.ID)
object BulkIt {
    const val ID: String = "bulkit"
    val LOGGER: Logger = LogUtils.getLogger()

    init {
        val modBus = MOD_BUS
        register(modBus)
        modBus.register(NewRegistryEvent::class.java, BulkIt::onNewRegistry)
    }

    private fun register(modBus: IEventBus) {
        Items.register(modBus)
        ResourceTypes.register(modBus)
    }

    private fun onNewRegistry(event: NewRegistryEvent) {
        BulkItApi.registerBulkItRegistries(event)
    }

    private fun onLoadComplete(event: FMLLoadCompleteEvent) {
        LOGGER.info("registered resource types: [${BulkItApi.RESOURCE_TYPE_REGISTRY.joinToString { resourceType -> resourceType.name }}]")
    }
}