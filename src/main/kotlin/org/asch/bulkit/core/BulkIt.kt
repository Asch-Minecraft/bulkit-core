package org.asch.bulkit.core

import com.mojang.logging.LogUtils
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item
import net.minecraft.world.level.material.Fluid
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.NewRegistryEvent
import org.asch.bulkit.api.BulkItApi
import org.asch.bulkit.api.registry.BulkItRegistries
import org.asch.bulkit.api.registry.CreativeModTabs
import org.asch.bulkit.api.registry.DiskDataComponents
import org.asch.bulkit.api.resource.ResourceType
import org.asch.bulkit.core.setup.Items
import org.slf4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(BulkIt.ID)
object BulkIt {
    const val ID: String = "bulkit"
    val LOGGER: Logger = LogUtils.getLogger()
    val MOD: BulkItApi.Mod = BulkItApi.Mod(ID)

    val ITEM: DeferredHolder<ResourceType<*>, ResourceType<Item>> =
        MOD.registerResourceType("item", BuiltInRegistries.ITEM) { builder -> builder }

    val FLUID: DeferredHolder<ResourceType<*>, ResourceType<Fluid>> =
        MOD.registerResourceType("fluid", BuiltInRegistries.FLUID) { builder -> builder }


    init {
        val modBus = MOD_BUS

        register(modBus)
        modBus.addListener(NewRegistryEvent::class.java, BulkItRegistries::register)
        modBus.addListener(FMLLoadCompleteEvent::class.java, BulkIt::onLoadComplete)
    }

    private fun register(modBus: IEventBus) {
        Items.register(modBus)

        MOD.register(modBus)
        BulkItRegistries.register(modBus)
        DiskDataComponents.register(modBus)
        CreativeModTabs.register(modBus)
    }

    private fun onLoadComplete(event: FMLLoadCompleteEvent) {
        LOGGER.info("registered resource types: [${BulkItRegistries.RESOURCE_TYPES.joinToString { resourceType -> resourceType.name }}]")
    }
}