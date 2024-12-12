package org.asch.bulkit.core.setup

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import org.asch.bulkit.api.BulkItApi
import org.asch.bulkit.api.item.BaseDiskItem

object CoreCreativeTabs {
    private val CREATIVE_TABS: DeferredRegister<CreativeModeTab> =
        DeferredRegister.create(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            BulkItApi.ID
        )

    private val DISKS_BUILDER: CreativeModeTab.Builder =
        CreativeModeTab.builder().title(Component.literal("BulkIt! - Disks")).withSearchBar().displayItems(::disks)
    private val DISKS: DeferredHolder<CreativeModeTab, CreativeModeTab> =
        CREATIVE_TABS.register("disks") { -> DISKS_BUILDER.build() }

    fun register(modBus: IEventBus) {
        CREATIVE_TABS.register(modBus)
    }

    private fun disks(displayParameters: ItemDisplayParameters, output: CreativeModeTab.Output) {
        BuiltInRegistries.ITEM.stream()
            .filter { item -> item is BaseDiskItem }
            .forEach { item -> output.accept(item) }
    }
}
