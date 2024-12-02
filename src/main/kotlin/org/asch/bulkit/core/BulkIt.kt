package org.asch.bulkit.core

import com.mojang.logging.LogUtils
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.registries.NewRegistryEvent
import org.asch.bulkit.api.registry.core.BulkItCreativeTabs
import org.asch.bulkit.api.registry.core.BulkItDataComponents
import org.asch.bulkit.api.registry.core.BulkItRegistries
import org.asch.bulkit.core.common.capability.DiskItemHandler
import org.asch.bulkit.core.setup.CoreItems
import org.asch.bulkit.core.setup.CoreResourceTypes
import org.slf4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(BulkIt.ID)
object BulkIt {
    const val ID: String = "bulkit"
    private val LOGGER: Logger = LogUtils.getLogger()

    init {
        val modBus = MOD_BUS

        register(modBus)
        modBus.addListener(NewRegistryEvent::class.java, BulkItRegistries::register)
        modBus.addListener(FMLLoadCompleteEvent::class.java, BulkIt::onLoadComplete)
        modBus.addListener(RegisterCapabilitiesEvent::class.java, BulkIt::onRegisterCapabilities)
    }

    private fun register(modBus: IEventBus) {
        CoreItems.register(modBus)
        CoreResourceTypes.register(modBus)

        BulkItDataComponents.register(modBus)
        BulkItCreativeTabs.register(modBus)
    }

    private fun onRegisterCapabilities(event: RegisterCapabilitiesEvent) {
        event.registerItem(Capabilities.ItemHandler.ITEM, ::DiskItemHandler, CoreItems.DISK_ITEM)
    }

    private fun onLoadComplete(event: FMLLoadCompleteEvent) {
        LOGGER.info("registered resource types: [${BulkItRegistries.RESOURCE_TYPES.joinToString { resourceType -> resourceType.name }}]")
    }
}