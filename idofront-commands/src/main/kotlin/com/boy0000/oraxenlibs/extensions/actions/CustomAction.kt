package com.boy0000.oraxenlibs.commands.extensions.actions

import com.boy0000.oraxenlibs.commands.Command
import com.boy0000.oraxenlibs.commands.execution.Action

fun <A : Action> Command.customAction(run: A.() -> Unit, create: Command.() -> A) {
    if (canExecute()) create(this).execute(run)
}