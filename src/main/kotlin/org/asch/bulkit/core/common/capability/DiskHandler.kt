package org.asch.bulkit.core.common.capability

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemContainerContents
import org.asch.bulkit.api.capability.IDiskHandler
import org.asch.bulkit.api.item.BaseDiskItem
import org.asch.bulkit.api.resource.ResourceType
import org.asch.bulkit.core.common.item.mod.CapacityDowngradeModItem
import org.asch.bulkit.core.common.item.mod.CapacityUpgradeModItem
import org.asch.bulkit.core.kotlin.DataComponentDelegate
import org.asch.bulkit.core.setup.CoreDataComponents

class DiskHandler(disk: ItemStack, ctx: Void?) : IDiskHandler {
    private val _resourceType: ResourceType<*> = (disk.item as BaseDiskItem).resourceType

    private var _amount: Long by DataComponentDelegate(
        disk,
        CoreDataComponents.Disk.AMOUNT,
        CoreDataComponents.Disk.Default.AMOUNT
    )

    private var _voidExcess: Boolean by DataComponentDelegate(
        disk,
        CoreDataComponents.Disk.VOID_EXCESS,
        CoreDataComponents.Disk.Default.VOID_EXCESS
    )

    private var mods: ItemContainerContents by DataComponentDelegate(
        disk,
        CoreDataComponents.Disk.MODS,
        CoreDataComponents.Disk.Default.EMPTY_MODS
    )

    override fun getResourceType(): ResourceType<*> = _resourceType

    override fun getAmount(): Long = _amount
    override fun setAmount(amount: Long) {
        _amount = amount
    }

    override fun isVoidExcess(): Boolean = _voidExcess
    override fun setVoidExcess(voidExcess: Boolean) {
        _voidExcess = voidExcess
    }

    override fun getCapacity(maxStackSize: Long): Long {
        val multipliers = mods.nonEmptyItems()
            .map(ItemStack::getItem)
            .filterIsInstance<CapacityUpgradeModItem>()
            .map(CapacityUpgradeModItem::multiplier)

        val num =
            _resourceType.baseStackMultiplier * if (multipliers.isEmpty()) 1 else multipliers.reduce { acc, l -> acc * l }
        val denom = if (mods.nonEmptyItems().filterIsInstance<CapacityDowngradeModItem>().isEmpty()) 1 else maxStackSize
        return maxStackSize * num / denom
    }
}