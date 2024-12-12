package org.asch.bulkit.core.setup

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.inventory.MenuType
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import org.asch.bulkit.core.BulkIt
import org.asch.bulkit.core.common.menu.DiskMenu

object CoreMenus {
    private val REGISTRAR: DeferredRegister<MenuType<*>> = DeferredRegister.create(BuiltInRegistries.MENU, BulkIt.ID)

    val DISK: DeferredHolder<MenuType<*>, MenuType<DiskMenu>> = REGISTRAR.register("disk", DiskMenu.builder())

    fun register(modBus: IEventBus) = REGISTRAR.register(modBus)
}