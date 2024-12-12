package org.asch.bulkit.core.setup

import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import org.asch.bulkit.core.BulkIt
import org.asch.bulkit.core.common.item.DiskItem
import org.asch.bulkit.core.common.item.mod.CapacityDowngradeModItem
import org.asch.bulkit.core.common.item.mod.CompressionModItem

object CoreItems {
    private val REGISTRAR: DeferredRegister.Items = DeferredRegister.createItems(BulkIt.ID)
    val DISKS = Disks()
    val MODS = Mods()

    class Disks {
        val item: DeferredItem<DiskItem> =
            REGISTRAR.registerItem("disk_item", DiskItem.builder(CoreResourceTypes.ITEM))

        val fluid: DeferredItem<DiskItem> =
            REGISTRAR.registerItem("disk_fluid", DiskItem.builder(CoreResourceTypes.FLUID))
    }

    class Mods {
        val capacityDowngrade: DeferredItem<CapacityDowngradeModItem> =
            REGISTRAR.registerItem("capacity_downgrade", ::CapacityDowngradeModItem)

        val compression: DeferredItem<CompressionModItem> =
            REGISTRAR.registerItem("compression_mod", ::CompressionModItem)
    }

    fun register(modBus: IEventBus) = REGISTRAR.register(modBus)
}