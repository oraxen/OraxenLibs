package com.boy0000.oraxenlibs.commands.children

import com.boy0000.oraxenlibs.commands.BaseCommand
import com.boy0000.oraxenlibs.commands.Command

class ChildManager : ChildRunning {
    override val subcommands: List<BaseCommand>
        get() = _subcommands.toList()
    private val _subcommands = mutableListOf<BaseCommand>()

    override fun addCommand(command: BaseCommand) {
        _subcommands += command
    }

    override fun runChildCommandOn(command: BaseCommand, subcommand: Command, init: Command.() -> Unit): Command? {
        _subcommands += subcommand

        with(command) {
            //if there are extra arguments and sub-commands exist, we first try to match them to any sub-commands
            if (argumentsWereSent && firstArgument in subcommand.names) {
                applySharedTo(subcommand)
                subcommand.runWith(init)
                this.executedCommand = true
            } else {
                return null
            }
        }
        return subcommand
    }
}
