package dev.deftu.ezrique

import dev.kord.common.entity.ButtonStyle
import dev.kord.common.entity.Snowflake

val Snowflake.rawValue: Long
    get() = this.value.toLong()

fun Boolean.toButtonStyle(): ButtonStyle =
    if (this) ButtonStyle.Success else ButtonStyle.Danger
