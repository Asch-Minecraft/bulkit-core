package org.asch.bulkit.core.setup

import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import org.asch.bulkit.api.item.DiskItem
import org.asch.bulkit.core.BulkIt
import org.asch.bulkit.core.common.item.CompressionModItem

object CoreItems {
    private val REGISTRAR: DeferredRegister.Items = DeferredRegister.createItems(BulkIt.ID)

    val DISK_ITEM: DeferredItem<DiskItem> =
        REGISTRAR.registerItem("disk_item", DiskItem.builder(CoreResourceTypes.ITEM))

    val DISK_FLUID: DeferredItem<DiskItem> =
        REGISTRAR.registerItem("disk_fluid", DiskItem.builder(CoreResourceTypes.FLUID))

    val COMPRESSION_MOD: DeferredItem<CompressionModItem> =
        REGISTRAR.registerItem("compression_mod", ::CompressionModItem)

    fun register(modBus: IEventBus) = REGISTRAR.register(modBus)
}