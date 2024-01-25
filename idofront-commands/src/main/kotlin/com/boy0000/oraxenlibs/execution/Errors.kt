package com.boy0000.oraxenlibs.commands.execution

import com.boy0000.oraxenlibs.commands.BaseCommand
import com.boy0000.oraxenlibs.messaging.error

/**
 * Stops a command from executing further by throwing a [CommandExecutionFailedException].
 *
 * @param message An error message to be sent to the sent to the sender of the command. Nothing is sent if null.
 * @param onFail Additional actions to take upon failure. Will execute after the message is sent.
 */
inline fun <T : BaseCommand> T.stopCommand(message: String? = null, onFail: (T.() -> Unit) = {}): Nothing {
    if (message != null) sender.error(message)
    onFail()
    throw CommandExecutionFailedException()
}

/**
 * Thrown when a command has failed to execute for any reason.
 *
 * It is used to stop the command from executing further in the DSL.
 */
class CommandExecutionFailedException : Exception()
