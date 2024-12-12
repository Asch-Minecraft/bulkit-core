package org.asch.bulkit.core.common.menu

import net.minecraft.network.chat.Component
import net.minecraft.world.MenuProvider
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.ItemStack
import org.asch.bulkit.core.setup.CoreMenus

class DiskMenu(containerId: Int, playerInv: Inventory) : AbstractContainerMenu(CoreMenus.DISK.get(), containerId) {
    override fun quickMoveStack(player: Player, slot: Int): ItemStack {
        TODO("Not yet implemented")
    }

    override fun stillValid(player: Player): Boolean = true

    companion object {
        fun builder(): () -> MenuType<DiskMenu> = { MenuType(::DiskMenu, FeatureFlags.DEFAULT_FLAGS) }
        fun provider(): MenuProvider = SimpleMenuProvider(
            { containerId, playerInv, _ -> DiskMenu(containerId, playerInv) },
            Component.empty()
        )
    }
}