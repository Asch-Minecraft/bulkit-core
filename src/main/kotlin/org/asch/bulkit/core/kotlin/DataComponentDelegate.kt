package org.asch.bulkit.core.kotlin

import net.minecraft.core.component.DataComponentType
import net.neoforged.neoforge.common.MutableDataComponentHolder
import java.util.function.Supplier
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class DataComponentDelegate<R, V : Any>(
    private val dataComponentHolder: MutableDataComponentHolder, private val dataComponentType: DataComponentType<V>,
    private val defaultValue: V
) : ReadWriteProperty<R, V> {
    constructor(
        dataComponentHolder: MutableDataComponentHolder,
        dataComponentType: Supplier<DataComponentType<V>>,
        defaultValue: V
    ) : this(dataComponentHolder, dataComponentType.get(), defaultValue)

    override fun getValue(thisRef: R, property: KProperty<*>): V =
        requireNotNull(dataComponentHolder.getOrDefault(dataComponentType, defaultValue))

    override fun setValue(thisRef: R, property: KProperty<*>, value: V) {
        dataComponentHolder.set(dataComponentType, value)
    }
}