package com.boy0000.oraxenlibs.commands.children

import com.boy0000.oraxenlibs.commands.Command

class ChildSharingManager : ChildSharing {
    override val sharedInit get() = _sharedInit.toList()
    private val _sharedInit = mutableListOf<Command.() -> Unit>()

    override fun shared(block: Command.() -> Unit) {
        _sharedInit += block
    }
}