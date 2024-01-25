package com.boy0000.oraxenlibs.commands.children

import com.boy0000.oraxenlibs.commands.BaseCommand
import com.boy0000.oraxenlibs.commands.Command

interface ChildRunning {
    val subcommands: List<BaseCommand>

    /** Runs a child command */
    fun runChildCommandOn(command: BaseCommand, subcommand: Command, init: Command.() -> Unit): Command?

    fun addCommand(command: BaseCommand)
}

/** @see ChildRunning.runChildCommandOn */
fun BaseCommand.runChildCommand(subcommand: Command, init: Command.() -> Unit) =
    runChildCommandOn(this, subcommand, init)
