package dev.deftu.ezrique

import dev.kord.core.entity.interaction.*

val InteractionCommand.names: Triple<String, String?, String?>
    get() {
        val rootName = rootName

        val subCommandName = when (this) {
            is RootCommand -> null
            is GroupCommand -> name
            is SubCommand -> name
        }

        val groupName = when (this) {
            is RootCommand, is SubCommand -> null
            is GroupCommand -> groupName
        }

        return Triple(rootName, subCommandName, groupName)
    }
