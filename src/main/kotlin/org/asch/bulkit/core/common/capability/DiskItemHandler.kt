package org.asch.bulkit.core.common.capability

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemContainerContents
import net.neoforged.neoforge.items.IItemHandler
import org.asch.bulkit.api.registry.core.BulkItDataComponents
import org.asch.bulkit.api.resource.Resource
import org.asch.bulkit.api.resource.ResourceType
import org.asch.bulkit.core.setup.CoreItems
import org.asch.bulkit.core.setup.CoreResourceTypes

class DiskItemHandler(private val disk: ItemStack, ctx: Void) : IItemHandler {
    private val resourceType: ResourceType<Item> = CoreResourceTypes.ITEM.get()

    private val resource: Resource<Item> = disk.get(resourceType.resourceHolder)!!
    private val mods: ItemContainerContents = disk.get(BulkItDataComponents.Disk.MODS)!!
    private val canCompress: Boolean = mods.stream().anyMatch { it.`is`(CoreItems.COMPRESSION_MOD) }

    override fun getSlots(): Int = 1

    override fun getStackInSlot(p0: Int): ItemStack {
        TODO("Not yet implemented")
    }

    override fun insertItem(p0: Int, p1: ItemStack, p2: Boolean): ItemStack {
        TODO("Not yet implemented")
    }

    override fun extractItem(p0: Int, p1: Int, p2: Boolean): ItemStack {
        TODO("Not yet implemented")
    }

    override fun getSlotLimit(p0: Int): Int {
        TODO("Not yet implemented")
    }

    override fun isItemValid(p0: Int, p1: ItemStack): Boolean {
        TODO("Not yet implemented")
    }

    private fun compressionPossibilities(): List<Resource<Item>> {

    }
}