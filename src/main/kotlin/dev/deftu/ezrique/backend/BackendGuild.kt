package dev.deftu.ezrique.backend

import com.google.gson.JsonObject
import dev.deftu.ezrique.add
import dev.deftu.ezrique.buildJsonObject
import dev.deftu.ezrique.rawValue
import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.Guild

data class BackendGuild(
    val id: Snowflake,
    val name: String,
    val icon: String?,
) {

    fun toJson(): JsonObject {
        return buildJsonObject {
            add("id", id.rawValue)
            add("name", name)
            add("icon", icon)
        }
    }

}

fun Guild.toEzriqueGuild(): BackendGuild {
    return BackendGuild(
        id = id,
        name = name,
        icon = iconHash
    )
}
