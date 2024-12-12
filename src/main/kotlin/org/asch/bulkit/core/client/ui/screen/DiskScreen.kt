package org.asch.bulkit.core.client.screen

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import org.asch.bulkit.core.common.menu.DiskMenu

class DiskScreen(menu: DiskMenu, playerInv: Inventory, title: Component) :
    AbstractContainerScreen<DiskMenu>(menu, playerInv, title) {

    override fun renderBg(guiGraphics: GuiGraphics, partialTicks: Float, mouseX: Int, mouseY: Int) {
        TODO("Not yet implemented")
    }
}