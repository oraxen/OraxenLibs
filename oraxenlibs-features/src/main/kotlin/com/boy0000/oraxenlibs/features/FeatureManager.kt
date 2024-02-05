package com.boy0000.oraxenlibs.features

import com.boy0000.oraxenlibs.commands.arguments.optionArg
import com.boy0000.oraxenlibs.execution.OraxenLibsCommandExecutor
import com.boy0000.oraxenlibs.messaging.error
import com.boy0000.oraxenlibs.messaging.logError
import com.boy0000.oraxenlibs.messaging.logSuccess
import com.boy0000.oraxenlibs.messaging.success
import com.boy0000.oraxenlibs.plugin.Plugins
import com.boy0000.oraxenlibs.plugin.actions
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

abstract class FeatureManager<T : FeatureDSL>(
    createContext: () -> T,
) : FeatureWithContext<T>(createContext) {
    val commandExecutor: OraxenLibsCommandExecutor by lazy {
        object : OraxenLibsCommandExecutor(), TabCompleter {
            override val commands = commands(context.plugin) {
                context.mainCommandProvider(this) {
                    mainCommandExtras.forEach { it() }
                    context.features.forEach { feature -> feature.mainCommandExtras.forEach { it() } }
                }
            }

            override fun onTabComplete(
                sender: CommandSender,
                command: org.bukkit.command.Command,
                alias: String,
                args: Array<String>
            ): List<String> {
                val tab = TabCompletion(sender, command, alias, args)
                return (context.features + this@FeatureManager).flatMap { it.tabCompletions }.mapNotNull { it(tab) }.flatten()
            }
        }
    }

    fun load() = actions {
        "Loading features" {
            context.features.forEach { feature ->
                "${feature::class.simpleName}" {
                    feature.load(context)
                }
            }
        }
    }

    fun enable() = actions {
        "Creating feature manager context" {
            createAndInjectContext()
        }

        val featuresWithMetDeps = context.features.filter { feature -> feature.dependsOn.all { Plugins.isEnabled(it) } }
        (context.features - featuresWithMetDeps.toSet()).forEach { feature ->
            val featureName = feature::class.simpleName
            logError("Could not enable $featureName, missing dependencies: ${feature.dependsOn.filterNot(Plugins::isEnabled)}")
        }
        "Registering feature contexts" {
            featuresWithMetDeps
                .filterIsInstance<FeatureWithContext<*>>()
                .forEach {
                    runCatching {
                        it.createAndInjectContext()
                    }.onFailure { e -> logError("Failed to create context for ${it::class.simpleName}: $e") }
                }
        }

        featuresWithMetDeps.forEach { feature ->
            val featureName = feature::class.simpleName
            "Enabled $featureName" {
                feature.enable(context)
            }.onFailure(Throwable::printStackTrace)
        }

        with(context) {
            mainCommand {
                "reloadFeature" {
                    val featureName by optionArg(features.map { it::class.simpleName!! })
                    action {
                        reloadFeature(featureName, sender)
                    }
                }
                "reload" {
                    context.disable()
                    context.enable()
                }
            }
            tabCompletion {
                when (args.size) {
                    1 -> listOf("reloadFeature").filter { it.startsWith(args[0], ignoreCase = true) }

                    2 -> when (args[0]) {
                        "reloadFeature" -> features.map { it::class.simpleName!! }
                            .filter { it.startsWith(args[1], ignoreCase = true) }

                        else -> null
                    }

                    else -> null
                }
            }
        }
        enable(context)
        commandExecutor
    }

    fun disable() = actions {
        disable(context)
        "Disabling features" {
            context.features.forEach { feature ->
                runCatching { feature.disable(context) }
                    .onSuccess { logSuccess("Disabled ${feature::class.simpleName}") }
                    .onFailure { e -> logError("Failed to disable ${feature::class.simpleName}: $e") }
            }
        }
        removeContext()
    }

    fun reloadFeature(simpleClassName: String, sender: CommandSender) {
        val feature = context.features
            .find { it::class.simpleName == simpleClassName }
            ?: error("Feature not found $simpleClassName")

        with(feature) {
            runCatching { context.disable() }
                .onSuccess { sender.success("$simpleClassName: Disabled") }
                .onFailure { sender.error("$simpleClassName: Failed to disable, $it") }
            if (feature is FeatureWithContext<*>)
                runCatching { feature.createAndInjectContext() }
                    .onSuccess { sender.success("$simpleClassName: Recreated context") }
                    .onFailure { sender.error("$simpleClassName: Failed to recreate context, $it") }
            runCatching { context.enable() }
                .onSuccess { sender.success("$simpleClassName: Enabled") }
                .onFailure { sender.error("$simpleClassName: Failed to enable, $it") }
        }
    }

    fun reload() {
        disable()
        enable()
    }
}
