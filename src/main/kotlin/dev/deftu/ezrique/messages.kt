package dev.deftu.ezrique

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.builder.message.MessageBuilder
import dev.kord.rest.builder.message.embed

inline fun MessageBuilder.successEmbed(
    builder: EmbedBuilder.() -> Unit
) {
    embed {
        color = Color(SUCCESS_COLOR)
        builder()
    }
}

inline fun MessageBuilder.errorEmbed(
    builder: EmbedBuilder.() -> Unit
) {
    embed {
        color = Color(ERROR_COLOR)
        builder()
    }
}
