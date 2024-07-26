package dev.deftu.ezrique

import dev.kord.common.Color
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.builder.message.MessageBuilder
import dev.kord.rest.builder.message.embed

enum class EmbedState(val color: Int) {

    SUCCESS(SUCCESS_COLOR),
    ERROR(ERROR_COLOR)

}

inline fun MessageBuilder.stateEmbed(
    state: EmbedState,
    builder: EmbedBuilder.() -> Unit
) {
    embed {
        color = Color(state.color)
        builder()
    }
}
