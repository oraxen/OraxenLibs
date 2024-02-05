package com.boy0000.oraxenlibs.features

import com.boy0000.oraxenlibs.config.OraxenLibsConfig

interface Configurable<T> {
    val configManager: OraxenLibsConfig<T>
    val config: T get() = configManager.getOrLoad()
}
