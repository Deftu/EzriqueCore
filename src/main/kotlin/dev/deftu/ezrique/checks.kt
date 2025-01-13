package dev.deftu.ezrique

import dev.kord.common.Color
import dev.kord.common.entity.Permission
import dev.kord.common.entity.Permissions
import dev.kord.core.behavior.interaction.response.*
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Member
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.embed

// Guild checks

suspend fun MessageInteractionResponseBehavior.complainGuildOnly() {
    edit {
        stateEmbed(EmbedState.ERROR) {
            description = "This command can only be used in a server!"
        }
    }
}

suspend fun DeferredMessageInteractionResponseBehavior.complainGuildOnly() {
    respond {
        embed {
            title = "Guild Only"
            color = Color(ERROR_COLOR)
            description = "This command can only be used in a server!"
        }
    }
}

suspend fun Message.complainGuildOnly(): Message {
    return reply {
        stateEmbed(EmbedState.ERROR) {
            description = "This command can only be used in a server!"
        }
    }
}

suspend fun Member.checkPermissions(
    permissions: Permissions,
    response: MessageInteractionResponseBehavior? = null
): Boolean {
    if (this.permissions?.contains(permissions) == false) {
        response?.edit {
            stateEmbed(EmbedState.ERROR) {
                title = "Missing permissions"
                description = buildString {
                    append("You are missing the following permissions:\n")
                    for (value in permissions.values) {
                        append("- ${value::class.java.simpleName}\n")
                    }
                }
            }
        }

        return false
    }

    return true
}

// Permission checks

suspend fun Member.checkPermissions(
    permissions: Permissions,
    block: suspend () -> MessageInteractionResponseBehavior
): Boolean {
    if (this.permissions?.contains(permissions) == false) {
        block().edit {
            stateEmbed(EmbedState.ERROR) {
                title = "Missing permissions"
                description = buildString {
                    append("You are missing the following permissions:\n")
                    for (value in permissions.values) {
                        append("- ${value::class.java.simpleName}\n")
                    }
                }
            }
        }

        return false
    }

    return true
}

suspend fun Member.checkPermission(
    permission: Permission,
    response: MessageInteractionResponseBehavior? = null
): Boolean {
    if (this.permissions?.contains(permission) == false) {
        response?.edit {
            stateEmbed(EmbedState.ERROR) {
                title = "Missing permission"
                description = "You are missing the ${permission::class.java.simpleName} permission."
            }
        }

        return false
    }

    return true
}

suspend fun Member.checkPermission(
    permission: Permission,
    block: suspend () -> MessageInteractionResponseBehavior
): Boolean {
    if (this.permissions?.contains(permission) == false) {
        block().edit {
            stateEmbed(EmbedState.ERROR) {
                title = "Missing permission"
                description = "You are missing the ${permission::class.java.simpleName} permission."
            }
        }

        return false
    }

    return true
}

suspend fun Member.checkPermissionsDeferred(
    permission: Permissions,
    response: DeferredMessageInteractionResponseBehavior? = null
): Boolean {
    if (permissions?.contains(permission) == false) {
        response?.respond {
            embed {
                title = "Missing Permissions"
                color = Color(ERROR_COLOR)
                description = buildString {
                    append("You are missing the following permissions:\n")
                    for (value in permission.values) {
                        append("- ${value::class.java.simpleName}\n")
                    }
                }
            }
        }

        return false
    }

    return true
}

suspend fun Member.checkPermissionsDeferred(
    permission: Permissions,
    block: suspend () -> DeferredMessageInteractionResponseBehavior
): Boolean {
    if (permissions?.contains(permission) == false) {
        block().respond {
            embed {
                title = "Missing Permissions"
                color = Color(ERROR_COLOR)
                description = buildString {
                    append("You are missing the following permissions:\n")
                    for (value in permission.values) {
                        append("- ${value::class.java.simpleName}\n")
                    }
                }
            }
        }

        return false
    }

    return true
}

suspend fun Member.checkPermissionDeferred(
    permission: Permission,
    response: DeferredMessageInteractionResponseBehavior? = null
): Boolean {
    if (permissions?.contains(permission) == false) {
        response?.respond {
            embed {
                title = "Missing Permission"
                color = Color(ERROR_COLOR)
                description = "You are missing the ${permission::class.java.simpleName} permission."
            }
        }

        return false
    }

    return true
}

suspend fun Member.checkPermissionDeferred(
    permission: Permission,
    block: suspend () -> DeferredMessageInteractionResponseBehavior
): Boolean {
    if (permissions?.contains(permission) == false) {
        block().respond {
            embed {
                title = "Missing Permission"
                color = Color(ERROR_COLOR)
                description = "You are missing the ${permission::class.java.simpleName} permission."
            }
        }

        return false
    }

    return true
}
