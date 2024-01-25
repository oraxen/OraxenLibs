package com.boy0000.oraxenlibs.commands

import com.boy0000.oraxenlibs.commands.arguments.Argumentable
import com.boy0000.oraxenlibs.commands.children.ChildRunning
import com.boy0000.oraxenlibs.commands.children.ChildSharing
import com.boy0000.oraxenlibs.commands.children.ChildSharingManager
import com.boy0000.oraxenlibs.commands.execution.Executable
import com.boy0000.oraxenlibs.commands.naming.Nameable
import com.boy0000.oraxenlibs.commands.permissions.Permissionable
import com.boy0000.oraxenlibs.commands.sender.Sendable

/**
 * Command groups limit arguments defined inside of them to only the commands in the group.
 *
 * @param parent The parent of this command group, to which most interface implementations will be delegated.
 */
class CommandGroup(
    private val parent: BaseCommand
) : BaseCommand,
    Argumentable by parent.childGroupParser(),
    ChildRunning by parent,
    ChildSharing by ChildSharingManager(),
    Executable by parent,
    Nameable by parent,
    Permissionable by parent,
    Sendable by parent
