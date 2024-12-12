package org.asch.bulkit.core.setup

import net.neoforged.neoforge.common.ModConfigSpec
import org.asch.bulkit.core.setup.config.GuiConfig
import org.asch.bulkit.core.setup.config.ModConfig

class CoreConfig(builder: ModConfigSpec.Builder) {
    val mod: ModConfig
    val gui: GuiConfig

    init {
        builder.push("mod")
        mod = ModConfig(builder)
        builder.pop()

        builder.push("gui")
        gui = GuiConfig(builder)
        builder.pop()
    }

    companion object {
        val INSTANCE: CoreConfig
        val CONFIG_SPEC: ModConfigSpec

        init {
            val builder = ModConfigSpec.Builder().configure(::CoreConfig)
            INSTANCE = builder.left
            CONFIG_SPEC = builder.right
        }
    }
}