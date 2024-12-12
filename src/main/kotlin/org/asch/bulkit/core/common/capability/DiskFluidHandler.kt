package org.asch.bulkit.core.common.capability

import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.material.Fluid
import net.neoforged.neoforge.fluids.FluidStack
import net.neoforged.neoforge.fluids.FluidType
import net.neoforged.neoforge.fluids.capability.IFluidHandler
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem
import org.asch.bulkit.api.capability.IDiskHandler
import org.asch.bulkit.api.resource.ResourceHandler
import org.asch.bulkit.core.kotlin.matches
import org.asch.bulkit.core.kotlin.toResource
import org.asch.bulkit.core.kotlin.toStack

class DiskFluidHandler(private val disk: ItemStack, ctx: Void?) : IFluidHandlerItem {
    private val resourceHandler: ResourceHandler<Fluid> =
        ResourceHandler<Fluid>(disk) { _ -> FluidType.BUCKET_VOLUME.toLong() };
    private val diskHandler: IDiskHandler = requireNotNull(disk.getCapability(IDiskHandler.CAPABILITY))

    override fun getTanks(): Int = 1

    override fun getFluidInTank(tank: Int): FluidStack {
        require(tank == 0)
        return resourceHandler.resource?.toStack(diskHandler.amount) ?: FluidStack.EMPTY
    }

    override fun getTankCapacity(tank: Int): Int {
        require(tank == 0)
        return diskHandler.getCapacity(resourceHandler.maxStackSize).toInt()
    }

    override fun isFluidValid(tank: Int, stack: FluidStack): Boolean {
        require(tank == 0)
        return resourceHandler.isValidResource(stack.toResource())
    }

    override fun fill(toFill: FluidStack, action: IFluidHandler.FluidAction): Int {
        if (toFill.isEmpty) {
            return 0
        }

        val remainder = resourceHandler.insert(toFill.toResource(), toFill.amount.toLong(), action.simulate());
        if (remainder == 0L) {
            return 0
        }

        return remainder.toInt()
    }

    override fun drain(toDrain: FluidStack, action: IFluidHandler.FluidAction): FluidStack {
        if (resourceHandler.resource?.matches(toDrain) != true) {
            return FluidStack.EMPTY
        }

        return drain(toDrain.amount, action)
    }

    override fun drain(amount: Int, action: IFluidHandler.FluidAction): FluidStack {
        if (resourceHandler.isEmpty) {
            return FluidStack.EMPTY
        }

        val currentResource = resourceHandler.resource
        val extracted = resourceHandler.extract(amount.toLong(), action.simulate())
        return currentResource?.toStack(extracted) ?: FluidStack.EMPTY
    }

    override fun getContainer(): ItemStack = disk
}