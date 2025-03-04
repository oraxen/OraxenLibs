package com.boy0000.oraxenlibs.commands.permissions

import com.boy0000.oraxenlibs.commands.BaseCommand
import com.boy0000.oraxenlibs.messaging.error

interface Permissionable {
    val parentPermission: String?
    var permissions: MutableList<String>

    fun permissionsMetFor(command: BaseCommand, sendError: Boolean = true): Boolean {
        if (permissions.none { command.sender.hasPermission(it) || command.sender.hasPermission("$it.*") }) {
            if (sendError) command.sender.error(noPermissionMessage)
            return false
        }
        return true
    }

    //MUTABLE STUFF FOR DSL
    var permission
        get() = permissions[0]
        set(perm) = permissions.run { clear(); add(perm) }
    var noPermissionMessage: String
}
