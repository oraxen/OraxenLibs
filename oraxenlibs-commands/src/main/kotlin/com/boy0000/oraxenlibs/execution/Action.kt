package com.boy0000.oraxenlibs.commands.execution

import com.boy0000.oraxenlibs.commands.BaseCommand
import com.boy0000.oraxenlibs.commands.CommandDSLElement

/**
 * An action to be run when the a command is executed. Within its scope is extra information about the command that is
 * useful when the command is actually run.
 */
open class Action(val command: BaseCommand) : CommandDSLElement {
    val sender = command.sender
    val arguments = command.strings
}
