package com.boy0000.oraxenlibs.commands

import com.boy0000.oraxenlibs.commands.arguments.Argumentable
import com.boy0000.oraxenlibs.commands.arguments.CommandArgument
import com.boy0000.oraxenlibs.commands.children.ChildRunning
import com.boy0000.oraxenlibs.commands.children.ChildSharing
import com.boy0000.oraxenlibs.commands.children.CommandCreating
import com.boy0000.oraxenlibs.commands.children.runChildCommand
import com.boy0000.oraxenlibs.commands.execution.Executable
import com.boy0000.oraxenlibs.commands.naming.Nameable
import com.boy0000.oraxenlibs.commands.permissions.Permissionable
import com.boy0000.oraxenlibs.commands.sender.Sendable
import kotlin.reflect.KProperty

interface BaseCommand : CommandDSLElement,
    Argumentable,
    ChildRunning,
    ChildSharing,
    CommandCreating,
    Executable,
    Nameable,
    Permissionable,
    Sendable {
    operator fun <T> (CommandArgument<T>.() -> Unit).provideDelegate(
        thisRef: Nothing?,
        prop: KProperty<*>
    ): CommandArgument<T> {
        val argument = CommandArgument<T>(this@BaseCommand, prop.name)
        invoke(argument)
        addArgument(argument)
        return argument
    }

    /**
     * Creates a subcommand that will run if the next argument passed matches one of its [names]
     *
     * @param desc The description for the command. Displayed when asked to enter sub-commands.
     */
    override fun command(vararg names: String, desc: String, init: Command.() -> Unit): Command? {
        val thisPerm = this.names[0]
        val subcommand = Command(
            nameChain = nameChain + names.first(),
            names = names.toList(),
            sender = sender,
            argumentParser = childParser(),
            parentPermission = if (parentPermission == null) thisPerm else "$parentPermission.$thisPerm",
            description = desc
        )
        return runChildCommand(subcommand, init)
    }

    /** Group commands which share methods or variables together, so commands outside this scope can't see them */
    fun commandGroup(init: CommandGroup.() -> Unit) = CommandGroup(
        this
    ).init()
}
