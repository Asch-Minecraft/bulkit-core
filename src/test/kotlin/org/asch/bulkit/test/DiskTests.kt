package org.asch.bulkit.test

import net.minecraft.core.component.DataComponentPatch
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.testframework.junit.EphemeralTestServerProvider
import org.asch.bulkit.api.resource.Resource
import org.asch.bulkit.core.kotlin.toStack
import org.asch.bulkit.core.setup.CoreItems
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(EphemeralTestServerProvider::class)
object DiskTests {
    @Test
    fun item() {
        val disk = CoreItems.DISKS.item.toStack()
        val diskItemHandler = disk.getCapability(Capabilities.ItemHandler.ITEM)
        Assertions.assertNotNull(diskItemHandler)

        val resource = Resource(Items.REDSTONE, DataComponentPatch.EMPTY)
        val amountToInsert = 32L
        val toInsert = resource.toStack(amountToInsert)

        val remainder = diskItemHandler!!.insertItem(0, toInsert, false)
        Assertions.assertEquals(ItemStack.EMPTY, remainder)

        val amountToExtract = 16
        val extracted = diskItemHandler.extractItem(0, amountToExtract, false)
        Assertions.assertTrue(ItemStack.matches(resource.toStack(amountToExtract.toLong()), extracted))
    }
}