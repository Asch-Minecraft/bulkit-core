package org.asch.bulkit.core.setup.config

import net.neoforged.neoforge.common.ModConfigSpec
import java.awt.Color

class GuiConfig(builder: ModConfigSpec.Builder) {
    private val consoleBorderColorHex: ModConfigSpec.ConfigValue<String> =
        builder.define("console-border-color", String.format("#%06x", 0xFFFFFF and Color.white.rgb))
    private val consoleBackgroundColorHex: ModConfigSpec.ConfigValue<String> =
        builder.define("console-background-color", String.format("#%06x", 0xFFFFFF and Color.black.rgb))
    private val consoleTextColorHex: ModConfigSpec.ConfigValue<String> =
        builder.define("console-text-color", String.format("#%06x", 0xFFFFFF and Color.green.rgb))

    val consoleBorderColor: Color
        get() = Color.decode(consoleBorderColorHex.get())
    val consoleBackgroundColor: Color
        get() = Color.decode(consoleBackgroundColorHex.get())
    val consoleTextColor: Color
        get() = Color.decode(consoleTextColorHex.get())
}