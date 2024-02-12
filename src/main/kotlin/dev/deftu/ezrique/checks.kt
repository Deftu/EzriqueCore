package dev.deftu.ezrique

import dev.kord.common.entity.Permission
import dev.kord.common.entity.Permissions
import dev.kord.core.behavior.interaction.response.MessageInteractionResponseBehavior
import dev.kord.core.behavior.interaction.response.edit
import dev.kord.core.entity.Member

suspend fun Member.checkPermissions(
    permissions: Permissions,
    response: MessageInteractionResponseBehavior? = null
): Boolean {
    if (this.permissions?.contains(permissions) == false) {
        response?.edit {
            errorEmbed {
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
            errorEmbed {
                title = "Missing permission"
                description = "You are missing the ${permission::class.java.simpleName} permission."
            }
        }

        return false
    }

    return true
}
