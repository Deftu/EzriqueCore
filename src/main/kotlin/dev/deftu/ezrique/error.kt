package dev.deftu.ezrique

import dev.kord.core.behavior.interaction.response.MessageInteractionResponseBehavior
import dev.kord.core.behavior.interaction.response.edit
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import io.sentry.Sentry

suspend fun handleError(
    t: Throwable,
    code: ErrorCode?,
    response: MessageInteractionResponseBehavior? = null
): Message? {
    val sentryId = if (Sentry.isEnabled()) Sentry.captureException(t) else null
    return response?.edit {
        errorEmbed {
            title = "Uh oh!"
            description = buildString {
                if (code != null || sentryId != null) append("**[")
                if (code != null) append("${code.code}")
                if (code != null && sentryId != null) append(" - ")
                if (sentryId != null) append("$sentryId")
                if (code != null || sentryId != null) append("]** ")

                append("An internal error has occurred! Please report this")
                if (code != null || sentryId != null) append(" alongside the error code(s).")
            }
        }
    }?.message
}

suspend fun handleError(
    t: Throwable,
    code: ErrorCode?,
    message: Message
): Message {
    val sentryId = if (Sentry.isEnabled()) Sentry.captureException(t) else null
    return message.reply {
        errorEmbed {
            title = "Uh oh!"
            description = buildString {
                if (code != null || sentryId != null) append("**[")
                if (code != null) append("${code.code}")
                if (code != null && sentryId != null) append(" - ")
                if (sentryId != null) append("$sentryId")
                if (code != null || sentryId != null) append("]** ")

                append("An internal error has occurred! Please report this")
                if (code != null || sentryId != null) append(" alongside the error code(s).")
            }
        }
    }
}

interface ErrorCode {
    val code: Int
}
