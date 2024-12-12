package org.asch.bulkit.core.setup

import com.mojang.serialization.Codec
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.Registries
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.world.item.component.ItemContainerContents
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import org.asch.bulkit.api.BulkItApi
import java.util.function.UnaryOperator

object CoreDataComponents {
    fun register(modBus: IEventBus) {
        Disk.DATA_COMPONENTS.register(modBus)
    }

    object Disk {
        private val NAME = BulkItApi.ID + ".disk"
        internal val DATA_COMPONENTS: DeferredRegister.DataComponents =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, NAME)

        val AMOUNT: DeferredHolder<DataComponentType<*>, DataComponentType<Long>> =
            DATA_COMPONENTS.registerComponentType(
                "amount",
                (UnaryOperator { builder: DataComponentType.Builder<Long> ->
                    builder.persistent(Codec.LONG)
                        .networkSynchronized(ByteBufCodecs.LONG)
                        .cacheEncoding()
                })
            )

        val VOID_EXCESS: DeferredHolder<DataComponentType<*>, DataComponentType<Boolean>> =
            DATA_COMPONENTS.registerComponentType(
                "void_excess",
                (UnaryOperator { builder: DataComponentType.Builder<Boolean> ->
                    builder.persistent(Codec.BOOL)
                        .networkSynchronized(ByteBufCodecs.BOOL)
                        .cacheEncoding()
                })
            )

        val MODS: DeferredHolder<DataComponentType<*>, DataComponentType<ItemContainerContents>> =
            DATA_COMPONENTS.registerComponentType(
                "mods",
                (UnaryOperator { builder: DataComponentType.Builder<ItemContainerContents> ->
                    builder.persistent(ItemContainerContents.CODEC)
                        .networkSynchronized(ItemContainerContents.STREAM_CODEC)
                        .cacheEncoding()
                })
            )

        object Default {
            const val AMOUNT: Long = 0L
            const val VOID_EXCESS: Boolean = false
            val EMPTY_MODS: ItemContainerContents = ItemContainerContents.EMPTY
        }
    }
}
