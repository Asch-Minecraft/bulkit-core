package org.asch.bulkit.core.common.capability

import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.IItemHandler
import org.asch.bulkit.api.capability.IDiskHandler
import org.asch.bulkit.api.resource.ResourceHandler
import org.asch.bulkit.core.kotlin.toResource
import org.asch.bulkit.core.kotlin.toStack

class DiskItemHandler(disk: ItemStack, ctx: Void?) : IItemHandler {
    private val resourceHandler: ResourceHandler<Item> =
        ResourceHandler<Item>(disk) { resource -> resource?.resource?.defaultMaxStackSize?.toLong() ?: 0L };
    private val diskHandler: IDiskHandler = requireNotNull(disk.getCapability(IDiskHandler.CAPABILITY))

    override fun getSlots(): Int = 1

    override fun getStackInSlot(slot: Int): ItemStack {
        require(slot == 0)
        return resourceHandler.resource?.toStack(diskHandler.amount) ?: ItemStack.EMPTY
    }

    override fun insertItem(slot: Int, toInsert: ItemStack, simulate: Boolean): ItemStack {
        require(slot == 0)

        if (toInsert.isEmpty) {
            return ItemStack.EMPTY
        }

        val remainder = resourceHandler.insert(toInsert.toResource(), toInsert.count.toLong(), simulate);
        if (remainder == 0L) {
            return ItemStack.EMPTY
        }

        return toInsert.copyWithCount(remainder.toInt())
    }

    override fun extractItem(slot: Int, toExtract: Int, simulate: Boolean): ItemStack {
        require(slot == 0)

        if (toExtract == 0) {
            return ItemStack.EMPTY
        }

        if (resourceHandler.isEmpty) {
            return ItemStack.EMPTY
        }

        val currentResource = resourceHandler.resource
        val extracted = resourceHandler.extract(toExtract.toLong(), simulate)
        return currentResource?.toStack(extracted) ?: ItemStack.EMPTY
    }

    override fun getSlotLimit(slot: Int): Int {
        require(slot == 0)
        return diskHandler.getCapacity(resourceHandler.maxStackSize).toInt()
    }

    override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
        require(slot == 0)
        return resourceHandler.isValidResource(stack.toResource())
    }
}