package org.asch.bulkit.core.setup

import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent
import org.asch.bulkit.core.BulkIt
import org.asch.bulkit.core.client.ui.screen.DiskScreen

class CoreScreens {
    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
    companion object {
        @SubscribeEvent
        fun onRegisterScreens(event: RegisterMenuScreensEvent) {
            event.register(CoreMenus.DISK.get(), ::DiskScreen)
        }
    }
}