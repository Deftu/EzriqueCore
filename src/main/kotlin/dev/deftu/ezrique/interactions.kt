package dev.deftu.ezrique

import dev.kord.core.entity.interaction.*
import dev.kord.core.event.interaction.ButtonInteractionCreateEvent
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.core.event.interaction.ModalSubmitInteractionCreateEvent
import dev.kord.core.event.interaction.SelectMenuInteractionCreateEvent

suspend fun ChatInputCommandInteractionCreateEvent.maybeGetGuild() =
    (interaction as? GuildChatInputCommandInteraction)?.getGuildOrNull()

suspend fun ButtonInteractionCreateEvent.maybeGetGuild() =
    (interaction as? GuildButtonInteraction)?.getGuildOrNull()

suspend fun SelectMenuInteractionCreateEvent.maybeGetGuild() =
    (interaction as? GuildSelectMenuInteraction)?.getGuildOrNull()

suspend fun ModalSubmitInteractionCreateEvent.maybeGetGuild() =
    (interaction as? GuildModalSubmitInteraction)?.getGuildOrNull()
