package com.boy0000.oraxenlibs.commands.children

import com.boy0000.oraxenlibs.commands.Command

interface ChildSharing {
    val sharedInit: List<Command.() -> Unit>

    /**
     * Will run on creation of all sub-commands in this command group. Useful for sharing conditions.
     *
     * If multiple shared blocks are created, all blocks declared above a command will be executed. Anything below
     * will not, so you can add additional conditions or run additional actions on specific commands.
     */
    fun shared(block: Command.() -> Unit)

    fun applySharedTo(command: Command) {
        sharedInit.forEach { command.it() }
    }
}