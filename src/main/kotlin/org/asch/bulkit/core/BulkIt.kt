package org.asch.bulkit.core

import com.mojang.logging.LogUtils
import net.minecraft.core.registries.BuiltInRegistries
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.registries.NewRegistryEvent
import org.asch.bulkit.api.capability.IDiskHandler
import org.asch.bulkit.api.item.BaseDiskItem
import org.asch.bulkit.api.registry.BulkItRegistries
import org.asch.bulkit.core.common.capability.DiskFluidHandler
import org.asch.bulkit.core.common.capability.DiskHandler
import org.asch.bulkit.core.common.capability.DiskItemHandler
import org.asch.bulkit.core.setup.*
import org.slf4j.Logger


@Mod(BulkIt.ID)
class BulkIt(modBus: IEventBus, modContainer: ModContainer) {
    init {
        register(modBus)
        modBus.addListener(NewRegistryEvent::class.java, BulkIt::onNewRegistries)
        modBus.addListener(FMLLoadCompleteEvent::class.java, BulkIt::onLoadComplete)
        modBus.addListener(RegisterCapabilitiesEvent::class.java, BulkIt::onRegisterCapabilities)

        modContainer.registerConfig(ModConfig.Type.COMMON, CoreConfig.CONFIG_SPEC)
    }

    private fun register(modBus: IEventBus) {
        CoreResourceTypes.register(modBus)
        CoreDataComponents.register(modBus)
        CoreItems.register(modBus)
        CoreCreativeTabs.register(modBus)
        CoreMenus.register(modBus)
    }

    companion object {
        const val ID: String = "bulkit"
        val LOGGER: Logger = LogUtils.getLogger()

        private fun onNewRegistries(event: NewRegistryEvent) {
            event.register(BulkItRegistries.RESOURCES)
            event.register(BulkItRegistries.RESOURCE_TYPES)
        }

        private fun onRegisterCapabilities(event: RegisterCapabilitiesEvent) {
            event.registerItem(Capabilities.ItemHandler.ITEM, ::DiskItemHandler, CoreItems.DISKS.item)
            event.registerItem(Capabilities.FluidHandler.ITEM, ::DiskFluidHandler, CoreItems.DISKS.fluid)

            for (item in BuiltInRegistries.ITEM) {
                if (item is BaseDiskItem) {
                    event.registerItem(IDiskHandler.CAPABILITY, ::DiskHandler, item)
                }
            }
        }

        private fun onLoadComplete(event: FMLLoadCompleteEvent) {
            LOGGER.info("registered resource types: [${BulkItRegistries.RESOURCE_TYPES.joinToString { resourceType -> resourceType.name }}]")
        }
    }
}