package dev.deftu.ezrique

import dev.kord.core.behavior.interaction.response.DeferredMessageInteractionResponseBehavior
import dev.kord.core.behavior.interaction.response.MessageInteractionResponseBehavior
import dev.kord.core.behavior.interaction.response.edit
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.rest.builder.message.EmbedBuilder
import io.sentry.Sentry
import io.sentry.protocol.SentryId

interface ErrorCode {
    val code: Int
}

suspend fun handleError(
    throwable: Throwable,
    code: ErrorCode?,
    response: MessageInteractionResponseBehavior? = null
): Message? {
    val sentryId = maybeCaptureException(throwable)
    return response?.edit {
        errorEmbed {
            constructErrorEmbed(code, sentryId)
        }
    }?.message
}

suspend fun handleError(
    throwable: Throwable,
    code: ErrorCode?,
    response: DeferredMessageInteractionResponseBehavior?
): Message? {
    val sentryId = maybeCaptureException(throwable)
    return response?.respond {
        errorEmbed {
            constructErrorEmbed(code, sentryId)
        }
    }?.message
}

suspend fun handleError(
    throwable: Throwable,
    code: ErrorCode?,
    message: Message
): Message {
    val sentryId = maybeCaptureException(throwable)
    return message.reply {
        errorEmbed {
            constructErrorEmbed(code, sentryId)
        }
    }
}

private fun maybeCaptureException(throwable: Throwable): SentryId? {
    return if (isSentrySetup) Sentry.captureException(throwable) else null
}

private fun EmbedBuilder.constructErrorEmbed(
    code: ErrorCode?,
    sentryId: SentryId?
) {
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
