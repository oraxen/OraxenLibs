package com.boy0000.oraxenlibs.features

import com.boy0000.oraxenlibs.config.IdofrontConfig

interface Configurable<T> {
    val configManager: IdofrontConfig<T>
    val config: T get() = configManager.getOrLoad()
}
