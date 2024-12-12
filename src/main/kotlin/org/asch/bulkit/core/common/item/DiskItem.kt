package org.asch.bulkit.core.common.item

import net.minecraft.core.Holder
import net.minecraft.resources.ResourceKey
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import org.asch.bulkit.api.item.BaseDiskItem
import org.asch.bulkit.api.resource.ResourceType
import org.asch.bulkit.core.common.menu.DiskMenu

class DiskItem(resourceTypeKey: ResourceKey<ResourceType<*>>, properties: Properties) :
    BaseDiskItem(resourceTypeKey, properties.stacksTo(MAX_STACK_SIZE)) {

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResult {
        val result = player.openMenu(DiskMenu.provider())
        if (result.isEmpty) {
            return super.use(level, player, usedHand)
        }

        return InteractionResult.SUCCESS_SERVER
    }

    companion object {
        private const val MAX_STACK_SIZE: Int = 16

        fun builder(resourceType: Holder<ResourceType<*>>): (Properties) -> DiskItem = { properties ->
            DiskItem(requireNotNull(resourceType.key), properties)
        }
    }
}