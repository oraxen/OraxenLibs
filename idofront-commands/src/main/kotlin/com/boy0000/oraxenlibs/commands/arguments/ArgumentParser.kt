package com.boy0000.oraxenlibs.commands.arguments

import com.boy0000.oraxenlibs.commands.BaseCommand

class ArgumentParser(
    strings: List<String>,
    arguments: Collection<CommandArgument<*>> = setOf()
) : Argumentable {
    override val strings = strings.toList()
    override val arguments get() = _arguments.toSet()
    private val _arguments = arguments.toMutableSet()

    override fun addArgument(argument: CommandArgument<*>) {
        _arguments += argument
    }

    override fun argumentsMetFor(command: BaseCommand): Boolean =
        command.run { _arguments.all { it.verifyAndCheckMissing(command) } }

    override operator fun get(commandArgument: CommandArgument<*>): String =
        strings[_arguments.indexOf(commandArgument)]
}
