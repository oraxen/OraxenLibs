package com.boy0000.oraxenlibs.commands

import com.boy0000.oraxenlibs.commands.arguments.Argumentable
import com.boy0000.oraxenlibs.commands.arguments.argumentsMet
import com.boy0000.oraxenlibs.commands.children.ChildManager
import com.boy0000.oraxenlibs.commands.children.ChildRunning
import com.boy0000.oraxenlibs.commands.children.ChildSharing
import com.boy0000.oraxenlibs.commands.children.ChildSharingManager
import com.boy0000.oraxenlibs.commands.execution.Action
import com.boy0000.oraxenlibs.commands.execution.Executable
import com.boy0000.oraxenlibs.commands.execution.stopCommand
import com.boy0000.oraxenlibs.commands.permissions.PermissionManager
import com.boy0000.oraxenlibs.commands.permissions.Permissionable
import com.boy0000.oraxenlibs.commands.sender.Sendable
import com.boy0000.oraxenlibs.messaging.info
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender

/**
 * A class for a command which will be instantiated by
 *
 * @param names A list of names for this command, when either is matched, the command will run.
 * @param sender The sender that ran this command (ex. console or player)
 * @param argumentParser A class that aids with parsing arguments passed to this command
 * @param parentPermission The parent's permissions
 *
 * @property executedCommand Whether any command was executed successfully up to the moment this is accessed.
 */
class Command(
    override val nameChain: List<String>,
    override val names: List<String>,
    override val sender: CommandSender,
    argumentParser: Argumentable,
    parentPermission: String?,
    override val description: String = ""
) : BaseCommand,
    Argumentable by argumentParser,
    ChildRunning by ChildManager(),
    ChildSharing by ChildSharingManager(),
    Executable,
    Permissionable by PermissionManager(parentPermission, names.first()),
    Sendable {
    override var executedCommand = false

    fun runWith(init: Command.() -> Unit): Command {
        init()
        if (subcommands.isNotEmpty() && !executedCommand) sendCommandDescription()
        return this
    }

    override fun canExecute(): Boolean {
        if (argumentsWereSent && firstArgument == "?") {
            sendCommandDescription()
            stopCommand()
        }

        //TODO if all parameters have a default value, command still doesn't consider arguments as met
        return (permissionsMetFor(this)
                && !firstArgumentIsFor(subcommands)
                && argumentsMet())
    }

    override fun <A : Action> A.execute(run: A.() -> Unit) {
        with(this@Command) {
            if (canExecute()) {
                this@execute.run()
                executedCommand = true
            }
        }
    }

    override fun action(run: Action.() -> Unit) {
        Action(this).execute(run)
    }

    override fun sendCommandDescription(showAliases: Boolean, showArgs: Boolean, showDesc: Boolean) {
        var topCommandInfo = "<gold>/${nameChain.joinToString(separator = " ")}<gray>"
        if (showAliases && names.size > 1) topCommandInfo += " <dark_gray>(aliases: ${names.drop(1)}<dark_gray>)<gray>"
        if (showArgs && arguments.isNotEmpty()) topCommandInfo += " $argumentNames"
        if (showDesc && description.isNotEmpty()) topCommandInfo += " - $description"

        var subCommandsInfo = ""
        if (subcommands.isNotEmpty()) {
            topCommandInfo += "\n"
            if (sender is ConsoleCommandSender && subcommands.size > 1) topCommandInfo = "\n" + topCommandInfo

            subCommandsInfo = subcommands.filter { permissionsMetFor(it, false) }.mapIndexed { i, it ->
                var line = "   <gold> ${if (i == subcommands.size - 1) "┗" else "┣"} <i>${it.names.first()}</i><gray> "
                if (it.description.isNotEmpty()) line += "- ${it.description}"
                line
            }.joinToString(separator = "\n")
        }
        sender.info((topCommandInfo + subCommandsInfo))
    }
}
