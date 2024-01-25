package com.boy0000.oraxenlibs.features

import com.boy0000.oraxenlibs.commands.Command
import com.boy0000.oraxenlibs.commands.entrypoint.CommandDSLEntrypoint
import org.bukkit.plugin.java.JavaPlugin

abstract class FeatureDSL(
    internal val mainCommandProvider: CommandDSLEntrypoint.(init: Command.() -> Unit) -> Unit = {},
) {
    abstract val plugin: JavaPlugin
    abstract val features: List<Feature>
}
