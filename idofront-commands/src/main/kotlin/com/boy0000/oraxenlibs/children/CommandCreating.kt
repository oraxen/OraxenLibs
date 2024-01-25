package com.boy0000.oraxenlibs.commands.children

import com.boy0000.oraxenlibs.commands.Command

interface CommandCreating {
    operator fun String.div(other: String) = mutableListOf(this, other)
    operator fun MutableCollection<String>.div(other: String) = add(other).let { this }

    operator fun Collection<String>.invoke(desc: String = "", init: Command.() -> Unit = {}) =
        command(names = this.toTypedArray(), desc = desc, init = init)

    operator fun String.invoke(desc: String = "", init: Command.() -> Unit = {}) =
        command(names = arrayOf(this), desc = desc, init = init)

    fun command(vararg names: String, desc: String = "", init: Command.() -> Unit = {}): Command?
}
