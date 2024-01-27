package com.boy0000.oraxenlibs.features

import com.boy0000.oraxenlibs.di.DI
import kotlin.reflect.KClass

abstract class FeatureWithContext<T : Any>(
    private val createContext: () -> T,
) : Feature() {
    private var _contextClass: KClass<out T>? = null
    private val contextClass get() = _contextClass ?: error("Context not injected yet for $this")
    private val observer by lazy { DI.observe(contextClass) }
    val context: T get() = observer.get()

    fun createAndInjectContext(): T {
        val context = createContext()
        _contextClass = context::class
        removeContext()
        DI.add(contextClass, context)
        return context
    }

    fun removeContext() {
        DI.remove(contextClass)
    }
}
