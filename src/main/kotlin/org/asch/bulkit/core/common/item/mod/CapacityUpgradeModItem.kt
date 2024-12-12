package org.asch.bulkit.core.common.item.mod

import org.asch.bulkit.api.item.ModItem
import java.util.function.Function

class CapacityUpgradeModItem(val multiplier: Long, properties: Properties) : ModItem(properties) {
    companion object {
        fun create(multiplier: Long): Function<Properties, CapacityUpgradeModItem> {
            return (Function { properties: Properties -> CapacityUpgradeModItem(multiplier, properties) })
        }
    }
}
